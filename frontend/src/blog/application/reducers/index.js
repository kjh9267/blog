import {HIDE_CONTENT, SHOW_CONTENT} from "../actions/ActionTypes";

const initialState = {
    isContentVisible: false,
    title: "sample title",
    categoryName: "sample category name",
    content: "sample content"
};

function article(state = initialState, action) {
    if (action.type === SHOW_CONTENT) {
        return {
            ...state,
            isContentVisible: true
        }
    }
    else if (action.type === HIDE_CONTENT) {
        return {
            ...state,
            isContentVisible: false
        }
    }
    return {
        ...state
    }
}

export default article;