import { MsInfo, PriceInfoEntry, TaskPublished, TaskResult } from "@/api/network-types";
import {PriceReport, PriceReportEntry, TaskState, Task, MsCalcTask, MsCalcTaskResult, castToTaskType, TaskToPublish, TaskCompleted} from "./datatypes";

export const MsInfoConverter = {
    toDataObject: (msInfo: MsInfo): PriceReport => {
        const priceList: Array<PriceReportEntry> = msInfo.priceInfoEntries.map((entry: PriceInfoEntry): PriceReportEntry => {
            return {
                msCalcInstanceId: entry.msCalcInstanceId,
                price: entry.price
            }
        })
        return {
            entries: priceList
        }
    }
}

export const TaskPublishedConverter = {
    toNetworkObject: (task: TaskToPublish, clientId: string): TaskPublished => {
        return {
            instanceId: task.forMsCalcId,
            taskType: task.taskType,
            clientId: clientId,
            plainText: task.plainText,
            cipherText: task.cipherText,
            hash: task.hash,
            taskId: String(task.taskId),
            shouldBeIntercepted: true
        }
    }
}

export const TaskResultConverter = {
    toDataObject: (taskRes: TaskResult): TaskCompleted => {
        return {
            taskId: Number(taskRes.taskId),
            taskType: castToTaskType(taskRes.taskType),
            msCalcId: taskRes.msCalcId,
            result: taskRes.result
        }
    }
}

export const MsCalcTaskResultConverter = {
    toDataObject: (taskRes: MsCalcTask): MsCalcTaskResult => {
        return {
            taskId: taskRes.taskId,
            result: "Pending",
            benchmark: {
                startedAt: Date.now(),
                finishedAt: 0,
                state: "pending"
            },
            msCalcId: taskRes.instanceId,
            taskType: taskRes.taskType,
            clientId: taskRes.clientId
        }
    }
}
