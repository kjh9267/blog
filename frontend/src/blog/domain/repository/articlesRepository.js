import {retrieveArticlesImpl} from "../../infra/retrieveArticlesImpl";

export const retrieveArticles = (url, page) => {
    return retrieveArticlesImpl(url, page);
}