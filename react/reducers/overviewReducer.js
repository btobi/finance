const defaultState = {
    overviewData: []
};

export default function overviewReducer(state = defaultState, action) {

    if (action.type === "FETCH_OVERVIEW_DATA_FULFILLED") {
        console.log(action.payload);
        return { ...state, overviewData: action.payload.data};
    }

    return state;
}