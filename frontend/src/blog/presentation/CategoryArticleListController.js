import React from "react";
import {CATEGORY} from "../../support/UrlUtils";
import {useParams} from "react-router-dom";
import {ArticlesService} from "../application/ArticlesService";

function CategoryArticleListController() {

    const params = useParams();

    console.log(params);

    return (
        <div>
            <ArticlesService url={CATEGORY + '/' + params.name + '?'}/>
        </div>
    )
}

export default CategoryArticleListController;