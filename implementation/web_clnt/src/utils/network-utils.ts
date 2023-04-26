export const bodyContentBasic = (eventName: string, id: string, publishTarget: string) => {
    return `{\"eventName\":\"${eventName}\",\"identifier\":\"${id}\",\"publishTarget\":\"${publishTarget}\"}`
}

export const bodyContentPayload = (eventName: string, publishTarget: string, payload: string) => {
    return `{\"eventName\":\"${eventName}\",\"publishTarget\":\"${publishTarget}\",\"payload\":${payload}}`
}

export const postRequestWithHeader = (url: string, body: string, fetchMode: "ALL" | "SINGLE" | null, resCallback: Function, finCallback: Function = () => { }): void => {
    const headerExtension = fetchMode != null ? {
        "Fetch-Mode": fetchMode
    } : null
    fetch(url, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            ...headerExtension
        },
        body: body
    })
        .then((res: Response) => {
            resCallback(res)
        })
        .catch(err => {
            console.error(err);
        })
        .finally(() => finCallback())
}

export const postRequest = (url: string, body: string, resCallback: Function, finCallback: Function = () => { }): void => {
    postRequestWithHeader(url, body, null, resCallback, finCallback)
}

export const deleteRequest = (url: string, resCallback: Function, finCallback: Function = () => { }): void => {
    fetch(url, {
        method: "DELETE",
    })
        .then((res: Response) => {
            resCallback(res)
        })
        .catch(err => {
            console.error(err);
        })
        .finally(() => finCallback())
}