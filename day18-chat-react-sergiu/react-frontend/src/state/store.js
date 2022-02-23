import {applyMiddleware, createStore} from "redux";
import thunk from "redux-thunk"
import reducers from "./reducers"
import {composeWithDevTools} from 'redux-devtools-extension';

// export const store = createStore(
//     reducers,
//     {},
//     applyMiddleware(thunk)
// )

// for redux in browser
export const store = createStore(reducers, {}, composeWithDevTools(
    applyMiddleware(thunk),
    // other store enhancers if any
));
