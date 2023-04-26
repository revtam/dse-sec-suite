import { API, EventMetaData, EVENTS_METADATA } from './api'
import { MsInfo, TaskPublished, TaskResult } from './network-types'
import { bodyContentBasic, bodyContentPayload, deleteRequest, postRequest, postRequestWithHeader } from '../utils/network-utils'
import {MsCalcTask, MsCalcTaskResult, PriceReport, TaskCompleted, TaskToPublish} from '../utils/datatypes'
import {
    MsCalcTaskResultConverter,
    MsInfoConverter,
    TaskPublishedConverter,
    TaskResultConverter
} from '@/utils/converters'

export const pollPriceInfo = (clntId: string, resultCallback: (_: PriceReport) => void): void => {
    const eventMetaData: EventMetaData = EVENTS_METADATA.priceReport
    const body = bodyContentBasic(eventMetaData.eventName, clntId, eventMetaData.publishTarget)
    postRequestWithHeader(API.subscriptions, body, "ALL", (res: Response) => {
        if (res.status === 200) {
            res.json().then((resContent: string) => {
                const msInfo: MsInfo = JSON.parse(resContent[resContent.length - 1])  // take latest
                const priceReport: PriceReport = MsInfoConverter.toDataObject(msInfo)
                resultCallback(priceReport)
            })
        }
    })
}

export const pollTaskResult = (clntId: string, resultCallback: (_: TaskCompleted) => void) => {
    const eventMetaData: EventMetaData = EVENTS_METADATA.taskResult(clntId)
    const body = bodyContentBasic(eventMetaData.eventName, clntId, eventMetaData.publishTarget)
    postRequestWithHeader(API.subscriptions, body, "ALL", (res: Response) => {
        if (res.status === 200) {
            res.json().then((taskResList: any) => {
                for (const taskRes of taskResList) {
                    const receivedTaskResult: TaskResult = JSON.parse(taskRes)
                    if (receivedTaskResult.clientId != clntId) continue  // check if message is intended for this client
                    const completedTask: TaskCompleted = TaskResultConverter.toDataObject(receivedTaskResult)
                    resultCallback(completedTask)                
                }
            })
        }
    })
}

export const pollMsCalcTaskResult = (clntId: string, publishedTasks: Array<MsCalcTaskResult>, setPublishTasks: Function) => {
    const eventMetaData: EventMetaData = EVENTS_METADATA.taskResult(clntId)
    const body = bodyContentBasic(eventMetaData.eventName, clntId, eventMetaData.publishTarget)
    postRequestWithHeader(API.subscriptions, body, "SINGLE", (res: Response) => {
        if (res.status === 200) {
            res.json().then((res: any) => {
                const taskRes = JSON.parse(res[0])
                const storedTask: MsCalcTaskResult | undefined = publishedTasks.find(task => task.taskId == taskRes.taskId)
                if (storedTask != undefined) {
                    storedTask.result = taskRes.result
                    storedTask.benchmark.state = 'completed'
                    storedTask.benchmark.finishedAt = Date.now()
                    setPublishTasks([...publishedTasks])
                    console.log("MsCalc Task #" + storedTask.taskId + " processed")
                } else {
                    console.log("MsCalc Task #" + taskRes.taskId + " could not be found")
                }
            })
        }
    })
}

const subscribe = (toEventName: string, toPublishTarget: string, clntId: string): void => {
    const body = bodyContentBasic(toEventName, clntId, toPublishTarget)
    postRequest(API.subscribe, body, (res: Response) => {
        if (res.status == 200) {
            console.log(`Subscribed to ${toEventName} successfully`)
        } else {
            console.log(`Subscribing to ${toEventName} failed`, res)
        }
    })
}

const unsubscribe = (fromEventName: string, clntId: string): void => {
    deleteRequest(API.unsubscribe(fromEventName, clntId), (res: Response) => {
        if (res.status == 200) {
            console.log(`Unsubscribed from ${fromEventName} successfully`)
        } else {
            console.log(`Unsubscribing from ${fromEventName} failed, res`)
        }
    })
}

export const subscribeToPriceReport = (clntId: string): void => {
    const eventMetaData: EventMetaData = EVENTS_METADATA.priceReport
    subscribe(eventMetaData.eventName, eventMetaData.publishTarget, clntId)
}

export const subscribeToTaskResult = (clntId: string): void => {
    const eventMetaData: EventMetaData = EVENTS_METADATA.taskResult(clntId)
    subscribe(eventMetaData.eventName, eventMetaData.publishTarget, clntId)
}

export const unsubscribeFromPriceReport = (clntId: string): void => {
    const eventMetaData: EventMetaData = EVENTS_METADATA.priceReport
    unsubscribe(eventMetaData.eventName, clntId)
}

export const unsubscribeFromTaskResult = (clntId: string): void => {
    const eventMetaData: EventMetaData = EVENTS_METADATA.taskResult(clntId)
    unsubscribe(eventMetaData.eventName, clntId)
}

export const publishTask = (clntId: string, msCalcId: string | undefined, taskToPublish: TaskToPublish | undefined, 
    resultCallback: (_: TaskToPublish) => void, finalizeCallback: () => void) => {
    if (!msCalcId || !taskToPublish) {
        console.log("Missing values from task, cannot be published")
        return
    }
    console.log("Publishing task")
    const task: TaskPublished = TaskPublishedConverter.toNetworkObject(taskToPublish, clntId)
    const eventMetaData: EventMetaData = EVENTS_METADATA.taskPublished(msCalcId)
    const payload = JSON.stringify(JSON.stringify(task))  // double stringify required! (first creates JSON object, second escapes "-s)
    const body = bodyContentPayload(eventMetaData.eventName, eventMetaData.publishTarget, payload)
    postRequest(API.publish, body, (res: Response) => {
        if (res.status === 200) {
            console.log(`Task #${taskToPublish.taskId} published successfully`)
            resultCallback(taskToPublish)
        }
    }, () => {
        finalizeCallback()
    })
}

export const publishMsCalcTask = (clntId: string, msCalcId: string | undefined, taskToPublish: MsCalcTask,
                            publishedTasks: Array<MsCalcTaskResult>, setTaskIdCounter: Function, setPublishText: Function, setPublishTasks: Function) => {

    if (!msCalcId) return
    console.log("Publishing MSCalc task")
    const eventMetaData: EventMetaData = EVENTS_METADATA.taskPublished(msCalcId)
    const payload = JSON.stringify(JSON.stringify(taskToPublish))  // double stringify required! (first creates JSON object, second escapes "-s)
    const body = bodyContentPayload(eventMetaData.eventName, eventMetaData.publishTarget, payload)
    postRequest(API.publish, body, (res: Response) => {
        if (res.status === 200) {
            console.log(`MsCalcTask #${taskToPublish.taskId} published successfully`)
            const result = MsCalcTaskResultConverter.toDataObject(taskToPublish)
            publishedTasks.push(result)
            setPublishTasks([...publishedTasks])
        }
    }, () => {
        setTaskIdCounter(taskToPublish.taskId + 1)
        setPublishText("")
    })
}
