import {ArticlesService} from "../application/ArticlesService";

function ArticlesController({url}) {

    return (
        <div>
            <ArticlesService url={url} />
        </div>
    )
}

export default ArticlesController;