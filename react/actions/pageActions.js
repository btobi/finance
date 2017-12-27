export function setPageTitle(pageTitle='', subTitle='', icon='') {
    return {
        type: 'CHANGE_PAGE_TITLE',
        payload: {
            pageTitle,
            subTitle,
            icon
        }
    }
}