import {retrieveCategoryArticlesImpl} from "../../infra/categoryArticlesRepositoryImpl";

export const retrieveCategoryArticles = (url, page) => {
    return retrieveCategoryArticlesImpl(url, page);
}