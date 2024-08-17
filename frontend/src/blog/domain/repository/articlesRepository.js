import {retrieveArticlesImpl} from "../../infra/articlesRepositoryImpl";

export const retrieveArticles = (page) => {
    return retrieveArticlesImpl(page);
}