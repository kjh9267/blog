import React, {useState} from "react";
import {Link} from "react-router-dom";
import {getApi} from "../api/getApi";


function CategoryList({url}) {

    const [categories, setCategories] = useState([]);

    const [isShow, setIsShow] = useState([]);

    const getCategory = async () => {

        const response = await getApi({url: url});

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
    }

    const showCategories = () => {
        return categories.map(
            (category, index) => {
                return (
                    <h1 key={index}>
                        {isShow[index] ?
                            <Link to={'/category/' + category.categoryName}>
                                {category.categoryName}
                                {`(${category.mappedArticleCount})`}
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