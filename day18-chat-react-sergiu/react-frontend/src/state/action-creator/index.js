export const setUserName = (userName) => {
    return (dispatch) => {
        dispatch({
            type: "setUserName",
            payload: userName
        });
    }
}


export const clearUserName = () => {
    return (dispatch) => {
        dispatch({
            type: "clearUserName",
            // payload: amount
        });
    }
}
