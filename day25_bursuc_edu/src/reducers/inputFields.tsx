const inputFieldsReducer = (state = {username: '',password: '',name: '', content: ''},action: { type: string; payload: string; }) =>{
    switch(action.type){
        case 'SETUSERNAME':
           return {...state, username: action.payload};
        case 'SETPASSWORD':
           return {...state, password: action.payload};
        case 'SETNAME':
            return {...state, name: action.payload};
        case 'SETCONTENT':
            return {...state, content: action.payload};     
        default:
            return state; 

    }
}

export default inputFieldsReducer;