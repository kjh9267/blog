import {ArticlesService} from "../application/ArticlesService";

function ArticleListController({url}) {

    return (
        <div>
            <ArticlesService url={url} />
        </div>
    )
}

export default ArticleListController;