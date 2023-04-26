export interface PriceReportEntry {
    msCalcInstanceId: string
    price: number
}

export interface PriceReport {
    entries: Array<PriceReportEntry>
}

export enum TaskType {
    Plaintext = "PLAIN_TEXT_ATTACK",
    HashCrack = "CRACK_HASH",
    Unknown = "UNKNOWN"
}

export enum TaskState {
    Processing = "processing",
    Completed = "completed"
}

export interface TaskToPublish {
    forMsCalcId: string
    taskType: TaskType
    plainText: string
    cipherText: string
    hash: string
    taskId: number
}

export interface TaskCompleted {
    msCalcId: string
    result: string
    taskType: TaskType
    taskId: number
}

export interface Task {
    taskId: number
    taskType: TaskType
    plainText: string  // for Plaintext
    cipherText: string  // for Plaintext
    hash: string  // for CrackHash
    assignedToMsCalcId: string
    state: TaskState | undefined
    result: string | undefined
}

export interface MsCalcTask {
    instanceId: string
    taskType: string
    clientId: string
    plainText: string
    cipherText: string
    hash: string
    taskId: string
    shouldBeIntercepted: boolean
}

interface MsCalcTaskBenchmark {
    startedAt: number
    finishedAt: number
    state: 'pending' | 'completed'
}
export interface MsCalcTaskResult {
    msCalcId: string
    result: string
    benchmark: MsCalcTaskBenchmark
    clientId: string
    taskType: string
    taskId: string
}

export const castToTaskType = (str: string): TaskType => {
    switch (str) {
        case TaskType.HashCrack: return TaskType.HashCrack
        case TaskType.Plaintext: return TaskType.Plaintext
    }
    return TaskType.Unknown
}