import React from "react";
import {CATEGORY} from "../../support/UrlUtils";
import {useParams} from "react-router-dom";
import {CategoryArticlesService} from "../application/CategoryArticlesService";

function CategoryArticlesController() {

    const params = useParams();

    console.log('params: ', params.name);

    return (
        <div>
            <CategoryArticlesService url={CATEGORY + '/' + params.name + '?'}/>
        </div>
    )
}

export default CategoryArticlesController;