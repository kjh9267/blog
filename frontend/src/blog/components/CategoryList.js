import React, {useState} from "react";
import {Link} from "react-router-dom";
import {retrieveApi} from "../api/retrieveApi";


function CategoryList({url}) {

    const [categories, setCategories] = useState([]);

    const [isShow, setIsShow] = useState([]);

    const retrieveCategory = async () => {

        const response = await retrieveApi({url: url});

        const categoryArray = [];

        const isShowArray = [];

        console.log(response.data)

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
                console.log(category, index)
                return (
                    <div className='title' key={index}>
                        {isShow[index] ?
                            <Link to={'/category/' + category.category_name}>
                                {category.category_name}
                                {` (${category.mapped_article_count})`}
                            </Link>
                            : null}
                    </div>
                )
            }
        );
    }

    return (
        <div>
            <div className="list">
                <h2 onClick={retrieveCategory}>
                    show categories
                </h2>
                {showCategories()}
            </div>
        </div>
    )
}

export default CategoryList;