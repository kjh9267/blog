import React from "react";
import {CATEGORY} from "../../support/UrlUtils";
import {useParams} from "react-router-dom";
import ArticleList from "../presentation/ArticleList";

function CategoryArticleList() {

    const params = useParams();

    console.log(params);

    return (
        <div>
            <ArticleList url={CATEGORY + '/' + params.name + '?'}/>
        </div>
    )
}

export default CategoryArticleList;