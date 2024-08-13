import React from "react";
import {CategoriesService} from "../application/CategoriesService";


function CategoryListController({url}) {

    return (
        <div>
            <CategoriesService url={url}/>
        </div>
    )
}

export default CategoryListController;