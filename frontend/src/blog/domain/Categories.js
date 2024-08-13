import React, {useEffect, useState} from "react";
import {retrieveCategories} from "./repository/categoriesRepository";
import {Category} from "./Category";

export function Categories({url}) {

    const [categories, setCategories] = useState([]);

    const retrieveCategoryList = async () => {
        const response = await retrieveCategories(url);
        const categoryList = [...categories];

        response.data.categories.map(category =>
            categoryList.push(category)
        )

        setCategories(categoryList)
    }

    useEffect(() => {
        retrieveCategoryList()
    }, []);

    return (
        <div className="list">
            {
                categories.map((category, index) =>
                    <Category category={category} key={index}/>
                )
            }
        </div>
    )
}