import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {retrieveService} from "../application/retrieveService";


function CategoryListController({url}) {

    const [categories, setCategories] = useState([]);

    const [visibilityList, setVisibilityList] = useState([]);

    const retrieveCategory = async () => {

        const response = await retrieveService(url);

        const categoryArray = [];

        const isShowArray = [];

        response.data.categories.map(
            category => {
                categoryArray.push(category);
                isShowArray.push(true);
            }
        );

        setCategories(categoryArray);
        setVisibilityList(isShowArray);

        return categories;
    }

    useEffect(() => {
        retrieveCategory()
    }, [])

    const showCategories = () => {
        return categories.map(
            (category, index) => {
                return (
                    <div className='title' key={index}>
                        {visibilityList[index] ?
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
                {showCategories()}
            </div>
        </div>
    )
}

export default CategoryListController;