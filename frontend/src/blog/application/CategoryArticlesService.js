import {CategoryArticles} from "../domain/CategoryArticles";

export function CategoryArticlesService({url}) {

    return (
        <div>
            <CategoryArticles url={url}/>
        </div>
    )
}