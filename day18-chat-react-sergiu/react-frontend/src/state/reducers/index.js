import {combineReducers} from "redux";
import userNameReducer from "./userNameReducer"

const reducers = combineReducers({
    userName: userNameReducer
})

export default reducers
