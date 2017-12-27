import {applyMiddleware, createStore} from 'redux';
import thunk from 'redux-thunk'
import promise from 'redux-promise-middleware'

import combineReducers from './reducers/index'

const middleware = applyMiddleware(promise(), thunk);
// const middleware = applyMiddleware(promise(), thunk, createLogger());

let store = createStore(combineReducers, middleware);

export default store