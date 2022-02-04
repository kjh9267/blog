import {HIDE_CONTENT, SHOW_CONTENT} from "./ActionTypes";

export const showContent = () => {
    return {
        type: SHOW_CONTENT
    }
}

export const hideContent = () => {
    return {
        type: HIDE_CONTENT
    }
}