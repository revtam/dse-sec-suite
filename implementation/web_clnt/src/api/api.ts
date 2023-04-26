import { API_HOST_PREFIX } from "@/utils/constants";

export const API = {
  subscribe: API_HOST_PREFIX + "/subscribe",
  publish: API_HOST_PREFIX + "/publish",
  unsubscribe: (eventName: string, identifier: string) => API_HOST_PREFIX + `/unsubscribe/${eventName}/${identifier}`,
  subscriptions: API_HOST_PREFIX + "/subscriptions"
};

const generateEventMetaData = (eventName: string, publishTarget: string): EventMetaData => {
  return {
    eventName: eventName,
    publishTarget: publishTarget
  }
}

export const EVENTS_METADATA = {
  priceReport: generateEventMetaData("MS_INFO", "clnt"),
  taskResult: (clntId: string) => generateEventMetaData("CLNT_TASK_RESULT", clntId),
  taskPublished: (msCalcId: string) => generateEventMetaData("CLNT_TASK", msCalcId)
}

export interface EventMetaData {
  eventName: string,
  publishTarget: string
}
