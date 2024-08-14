import React from "react";
import {CategoriesService} from "../application/CategoriesService";


function CategoriesController({url}) {

    return (
        <div>
            <CategoriesService url={url}/>
        </div>
    )
}

export default CategoriesController;