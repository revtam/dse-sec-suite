import React, { ChangeEvent, useCallback, useEffect, useMemo, useState } from 'react'
import c from 'classnames'
import 'assets/main.css'
import { TaskType, Task, TaskState, PriceReport, PriceReportEntry, TaskToPublish, TaskCompleted } from './utils/datatypes'
import { POLLING_INTERVAL, CLNT_ID } from './utils/constants'
import { castToTaskType } from './utils/datatypes'
import { pollTaskResult, pollPriceInfo, subscribeToPriceReport, subscribeToTaskResult, publishTask, unsubscribeFromTaskResult, unsubscribeFromPriceReport } from './api/api-calls'

export interface MainProps {
  className?: string
  onClick?: (e: React.MouseEvent<HTMLDivElement>) => void
}

export const Main: React.FunctionComponent<MainProps> = ({ className, onClick }: MainProps) => {

  const [selectedMsCalc, setSelectedMsCalc] = useState<PriceReportEntry | undefined>(undefined)
  const [publishHashText, setPublishHashText] = useState<string>("")
  const [publishPlainText, setPublishPlainText] = useState<string>("")
  const [publishCipherText, setPublishCipherText] = useState<string>("")
  const [selectedTaskType, setSelectedTaskType] = useState<TaskType>(TaskType.HashCrack)
  const [taskIdCounter, setTaskIdCounter] = useState<number>(0)
  const [publishedTasks, setPublishTasks] = useState<Array<Task>>([])
  const [priceInfo, setPriceInfo] = useState<PriceReport | undefined>(undefined)

  let pollingSchedulerId: number | undefined = undefined

  const pollResultCallback = useCallback((priceReport: PriceReport) => {
    setPriceInfo(priceReport)
  }, [])

  const taskResultCallback = useCallback((completedTask: TaskCompleted) => {
    const storedTask: Task | undefined = publishedTasks.find(task => task.taskId == completedTask.taskId)
    if (storedTask != undefined) {
        storedTask.result = completedTask.result == null ? "invalid" : storedTask.result = completedTask.result
        storedTask.state = TaskState.Completed
        setPublishTasks([...publishedTasks])
        console.log("Task #" + storedTask.taskId + " processed")
    } else {
        console.log("Task #" + completedTask.taskId + " could not be found")
    }
  }, [publishedTasks])

  const publishTaskCallback = useCallback((publishedTask: TaskToPublish) => {
    const newTask: Task = {
      taskId: publishedTask.taskId,
      taskType: publishedTask.taskType,
      plainText: publishedTask.plainText,
      cipherText: publishedTask.cipherText,
      hash: publishedTask.hash,
      assignedToMsCalcId: publishedTask.forMsCalcId,
      state: TaskState.Processing,
      result: undefined
    }
    publishedTasks.push(newTask)
    setPublishTasks([...publishedTasks])
  }, [publishedTasks])

  const afterTaskPublished = useCallback(() => {
    setTaskIdCounter(taskIdCounter + 1)
    setPublishHashText("")
    setPublishCipherText("")
    setPublishPlainText("")
  }, [taskIdCounter])

  const selectCheapestInstance = useCallback(() => {
    if (!priceInfo || !priceInfo.entries.length) return
    const cheapest = priceInfo.entries.reduce((prev, curr) => prev.price < curr.price ? prev : curr)
    setSelectedMsCalc(cheapest)
  }, [selectedTaskType, selectedMsCalc, priceInfo])


  // use effects
  useEffect(() => {
    subscribeToPriceReport(CLNT_ID)
    subscribeToTaskResult(CLNT_ID)
  }, [])

  useEffect(() => {
      const callback = () => {
          pollPriceInfo(CLNT_ID, pollResultCallback)
          pollTaskResult(CLNT_ID, taskResultCallback)
      }
      callback()
      const intervalId = window.setInterval(() => {
        callback()
    }, POLLING_INTERVAL)
    pollingSchedulerId = intervalId
    return () => window.clearInterval(intervalId)
  }, [publishedTasks])

  useEffect(() => {
    selectCheapestInstance()
  }, [selectedTaskType, priceInfo])

  
  // rendering methods
  const renderInput = useMemo(() => {
    if (selectedTaskType === TaskType.HashCrack) {
      return (
        <div>
          HASH to crack: <input className={'input'} value={publishHashText} type="text" onChange={e => setPublishHashText(e.currentTarget.value)} />
        </div>
      )
    }
    return (
      <div>
        PLAINTEXT: <input className={'input'} value={publishPlainText} type="text" onChange={e => setPublishPlainText(e.currentTarget.value)} />
        CIPHERTEXT: <input className={'input'} value={publishCipherText} type="text" onChange={e => setPublishCipherText(e.currentTarget.value)} />
      </div>
    )
  }, [selectedTaskType, publishHashText, publishPlainText, publishCipherText])

  const renderMsCalcList = useMemo(() => {
    const instances = priceInfo?.entries.map((entry: PriceReportEntry) =>
      <div className="row">
        <div>id: {entry.msCalcInstanceId}, price: {entry.price}</div>
      </div>
    )
    if (instances && instances.length > 0)
      return (
        <div>
          {instances}
        </div>
      );
    return (
      <div>No MSCalc instances yet.</div>
    );
  }, [priceInfo]);

  const renderTaskList = useMemo(() => {
    const tasks = publishedTasks.map((task: Task) =>
      <div className="row">
        <div>id: {task.taskId}, {task.taskType === TaskType.HashCrack ? `hash: ${task.hash}` : `plaintext: ${task.plainText}, cipher: ${task.cipherText}`}, 
        to MSCalc: {task.assignedToMsCalcId}, state: {task.state}{task.result ? `, result: ${task.result}` : ""}</div>
      </div>
    )
    if (tasks.length > 0)
      return (
        <div>
          {tasks}
        </div>
      );
    return (
      <div>No tasks yet.</div>
    );
  }, [publishedTasks]);


  // helpers
  const handleTaskTypeChange = (e: ChangeEvent<HTMLInputElement>): void => {
    const taskType = castToTaskType(e.currentTarget.value)
    if (!taskType) return
    setSelectedTaskType(taskType)
  }

  const createNewTask = useCallback((): TaskToPublish | undefined => {
    if (selectedTaskType === TaskType.HashCrack && publishHashText == "") return
    if (selectedTaskType === TaskType.Plaintext && (publishPlainText == "" || publishCipherText == "")) return
    if (selectedMsCalc == null) return
    const hash = selectedTaskType === TaskType.HashCrack ? publishHashText : ""
    const plain = selectedTaskType === TaskType.Plaintext ? publishPlainText : ""
    const cipher = selectedTaskType === TaskType.Plaintext ? publishCipherText : ""
    return {
      forMsCalcId: selectedMsCalc?.msCalcInstanceId,
      taskType: selectedTaskType,
      plainText: plain,
      cipherText: cipher,
      hash: hash,
      taskId: taskIdCounter
    }
  }, [taskIdCounter, selectedMsCalc, selectedTaskType, publishHashText, publishPlainText, publishCipherText])


  return (
    <div className={c('main', className)} onClick={onClick}>
      {renderInput}
      <br></br>

      <div>
        <b>Attack type</b>
        <input type="radio"
          value={TaskType.HashCrack}
          checked={selectedTaskType == TaskType.HashCrack}
          onChange={handleTaskTypeChange}
        /> Hash Cracking
        <input type="radio"
          value={TaskType.Plaintext}
          checked={selectedTaskType == TaskType.Plaintext}
          onChange={handleTaskTypeChange}
        /> Plaintext Attack
      </div>

      <br></br>
      <p><b>Cheapest MSCalc for task type '{selectedTaskType}':</b></p>
      <p>{selectedMsCalc ? ` instance id: ${selectedMsCalc?.msCalcInstanceId}, price: ${selectedMsCalc?.price}` : " None"}</p>

      <br></br>
      <div>
        <p><b>Available MSCalc instances:</b></p>
        {renderMsCalcList}
      </div>

      <br></br>
      <div>
        <p><b>Tasks overview:</b></p>
        {renderTaskList}
      </div>

      <div>
        <br></br>
        <br></br>
        <button className="btn"
          onClick={() =>
            publishTask(CLNT_ID, selectedMsCalc?.msCalcInstanceId, createNewTask(), 
              publishTaskCallback, afterTaskPublished)}>Publish task</button>

        {/* <button className="btn" onClick={() => pollPriceInfo(clntId, setPriceInfo)}>Poll</button>
        <button className="btn" onClick={() => pollTaskResult(clntId, publishedTasks, setPublishTasks)}>Poll result</button> */}

        <br></br>
        <br></br>
        <button className="btn"
          onClick={() => {
            unsubscribeFromTaskResult(CLNT_ID)
            window.clearInterval(pollingSchedulerId)
          }}
        >Unsubscribe from results</button>
        <button className="btn"
          onClick={() => {
            unsubscribeFromPriceReport(CLNT_ID)
            window.clearInterval(pollingSchedulerId)
          }}
        >Unsubscribe from price info</button>
      </div>
    </div>
  )
}
