import {Link} from "react-router-dom";
import React from "react";

export function Category({category, key}) {
    return (
        <div className="title">
            <Link to={'/category/' + category.category_name}>
                {category.category_name}
                {` (${category.mapped_article_count})`}
            </Link>
        </div>
    )
}