import 'assets/main.css'
import c from 'classnames'
// @ts-ignore
import BarChart from 'react-bar-chart';
import React, {useEffect, useMemo, useState} from 'react'
import {pollMsCalcTaskResult, publishMsCalcTask, subscribeToTaskResult} from './api/api-calls'
import {CLNT_ID, POLLING_INTERVAL} from './utils/constants'
import {MsCalcTask, MsCalcTaskResult} from "@/utils/datatypes";

export interface BenchmarkProps {
  className?: string
  onClick?: (e: React.MouseEvent<HTMLDivElement>) => void
}

export const Benchmark: React.FunctionComponent<BenchmarkProps> = ({ className, onClick }: BenchmarkProps) => {
  const [publishedTasks, setPublishTasks] = useState<Array<MsCalcTaskResult>>([])
  const [tasksToChart, setTasksToChart] = useState<Array<string>>([])

  const [chartData, setChartData] = useState<{text:string, value:number}[]>([])

  useEffect(()=> {
    subscribeToTaskResult(CLNT_ID)
  }, []);

  // useEffect is called when component is mounted
  useEffect(() => {
    pollMsCalcTaskResult(CLNT_ID, publishedTasks, setPublishTasks)
    const intervalId = window.setInterval(() => {
      pollMsCalcTaskResult(CLNT_ID, publishedTasks, setPublishTasks)
    }, POLLING_INTERVAL)
    return () => window.clearInterval(intervalId) // returned function stops interval when component unmounts
  }, [publishedTasks])

  function generateTasks(requestCount: number, taskType: 'plaintext'|'crackhash') {
    const ids = Array(requestCount).fill(0).map((_, i) => Math.random().toString(16).substring(2, 6))
    setTasksToChart(ids)
    const tasks: Array<MsCalcTask> = ids.map(id => ({
      taskId: id,
      hash: "232d", // secret
      taskType: taskType ==='crackhash' ? "CRACK_HASH" : "PLAIN_TEXT_ATTACK",
      clientId: CLNT_ID,
      shouldBeIntercepted: true,
      instanceId: "primary",
      plainText: "hello",
      cipherText: "qnuux"
    }));

    const noop = () => {}
    for (const task of tasks) {
      publishMsCalcTask(CLNT_ID, "primary", task, publishedTasks, noop, noop, setPublishTasks)
    }
  }

  useEffect(() => {
    const toChart = [] as MsCalcTaskResult[]
    for (const task of publishedTasks) {
      const shouldBeCharted = tasksToChart.includes(task.taskId)
        if (!shouldBeCharted) return

        toChart.push(task)
    }

    setChartData(toChart.map(task => ({
        text: task.taskId,
        value: task.benchmark.finishedAt - task.benchmark.startedAt
        })))
  },[publishedTasks]);

  const renderTaskList = useMemo(() => {
    const tasks = publishedTasks.map((task, index) => {
          const tookLabel = task.benchmark.state === 'completed' ? `Took ${task.benchmark.finishedAt - task.benchmark.startedAt}ms` : 'ongoing'
          return (
              <div className="row">
                <div>id: {task.taskId} [{task.taskType}], result: {task.result}, took: {tookLabel}</div>
              </div>
          )
        }
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

  return (
    <div className={c('benchmark', className)} onClick={onClick}>
      <div>
        <p><b>Tasks overview:</b></p>
        {renderTaskList}
      </div>
      <button onClick={() => {
        setPublishTasks([])
        setChartData([])
      }
      }>Clear tasks</button>
        <button onClick={() => generateTasks(10, 'crackhash')}>Generate 10 tasks crackhash</button>
        <button onClick={() => generateTasks(10, 'plaintext')}>Generate 10 tasks plaintext</button>
        {chartData.length > 0 &&
            <BarChart ylabel='millis' xlabel='taskId'
                      width={900}
                    height={450}
                    margin={{top: 20, right: 20, bottom: 30, left: 250}}
                    data={chartData}
          />}
    </div>
  )
}
