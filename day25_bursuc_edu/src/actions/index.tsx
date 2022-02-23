export const setUserName = (userName: string) => {
    return {
        type: "SETUSERNAME",
        payload: userName
    }
}

export const setName = (name: string) => {
    return {
        type: "SETNAME",
        payload: name
    }
}

export const setContent = (context: string) => {
    return {
        type: "SETCONTENT",
        payload: context
    }
}

export const setPassword = (context: string) => {
    return {
        type: "SETPASSWORD",
        payload: context
    }
}

export {}