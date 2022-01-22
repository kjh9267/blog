import React from "react";
import axios from "axios";
import {useState} from "react";
import {CATEGORY} from "../support/UrlUtils";

function Category() {

    const getCategory = () => {
        return axios.get(CATEGORY)
            .then(response => {
                console.log(response);


            })
            .catch(reason => console.log(reason));
    }

    return (
        <div>
            <div className="list">
                {getCategory()}
            </div>
        </div>
    )

}

export default Category;