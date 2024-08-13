import {retrieveArticlesImpl} from "../../infra/articlesRepositoryImpl";

export const retrieveArticles = (url, page) => {
    return retrieveArticlesImpl(url, page);
}