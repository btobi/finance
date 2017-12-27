import Http from "../Http";

export function getOverviewData() {
    return {
        type: 'FETCH_OVERVIEW_DATA',
        payload: Http.get('/stats/complete')
    }
}