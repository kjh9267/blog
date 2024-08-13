import {retrieveCategoriesImpl} from "../../infra/categoriesRepositoryImpl";

export const retrieveCategories = (url) => {
    return retrieveCategoriesImpl(url);
}