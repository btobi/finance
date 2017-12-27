import {combineReducers} from 'redux'
import commonReducer from './commonReducer';
import overviewReducer from "./overviewReducer";


export default combineReducers({
    common: commonReducer,
    overview: overviewReducer
})