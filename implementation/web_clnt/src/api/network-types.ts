export interface PriceInfoEntry {
    msCalcInstanceId: string
    price: number
    taskType: string
}

export interface MsInfo {
    priceInfoEntries: Array<PriceInfoEntry>
}

export interface TaskPublished {
    instanceId: string
    taskType: string
    clientId: string
    plainText: string
    cipherText: string
    hash: string
    taskId: string
    shouldBeIntercepted: boolean
}

export interface TaskResult {
    msCalcId: string
    result: string
    clientId: string
    taskType: string
    taskId: string
    shouldBeIntercepted: boolean
}