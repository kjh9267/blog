import React from "react";
import {CATEGORY} from "../../support/UrlUtils";
import {useParams} from "react-router-dom";
import ArticleListController from "./ArticleListController";

function CategoryArticleListController() {

    const params = useParams();

    console.log(params);

    return (
        <div>
            <ArticleListController url={CATEGORY + '/' + params.name + '?'}/>
        </div>
    )
}

export default CategoryArticleListController;