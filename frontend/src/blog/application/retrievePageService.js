import {retrievePageServiceImpl} from "../infra/retrievePageServiceImpl";

export const retrievePageService = (url, page) => {
    return retrievePageServiceImpl(url, page);
}