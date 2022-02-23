const initialState = ''

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case 'setUserName':
            return action.payload;
        case 'clearUserName':
            return '';
        default:
            return state;
    }
}

export default reducer;
