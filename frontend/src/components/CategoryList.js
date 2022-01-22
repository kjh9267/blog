import React, {useState} from "react";
import axios from "axios";
import {CATEGORY} from "../support/UrlUtils";
import {Link} from "react-router-dom";

function CategoryList() {

    const [categories, setCategories] = useState([]);

    const [isShow, setIsShow] = useState([]);

    const getCategory = () => {
        return axios.get(CATEGORY)
            .then(response => {
                console.log(response);

                const categoryArray = [];

                const isShowArray = [];

                response.data.categories.map(
                    category => {
                        categoryArray.push(category);
                        isShowArray.push(true);
                    }
                );

                setCategories(categoryArray);
                setIsShow(isShowArray);

                return categories;
            })
            .catch(reason => console.log(reason));
    }

    const showCategories = () => {
        return categories.map(
            (category, index) => {
                return (
                    <h1 key={index}>
                        {isShow[index] ?
                            <Link to={'/category/' + category}>
                                {category}
                            </Link>
                            : null}
                    </h1>
                )
            }
        );
    }

    return (
        <div>
            <div className="list">
                <h2 onClick={getCategory}>
                    show categories
                </h2>
                {showCategories()}
            </div>
        </div>
    )
}

export default CategoryList;