const defaultState = {
    pending: false,
    rejected: false,
};

export default function commonReducer(state = defaultState, action) {

    if (action.type.endsWith('PENDING'))
        return {...state, pending: true, rejected: false};

    if (action.type.endsWith('REJECTED')) {
        
        return {...state, pending: false, rejected: true};

    }

    if (action.type.endsWith('FULFILLED'))
        return {...state, pending: false, rejected: false};

    return state;

}