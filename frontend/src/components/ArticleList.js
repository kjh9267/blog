/* eslint-disable array-callback-return */
import React from "react";
import axios from "axios";
import { useState } from "react";
import qs from "qs";

function ArticleList(props) {

    const [articleList, setArticleList] = useState([]);

    const getArticleList = async () => {
        const queryString = qs.stringify(
            {
                page: props.page,
                size: props.size
            }
        )
        console.log(queryString);
        const response = await axios.get("http://localhost:8080/api/blog/articles/query?" + queryString)
            .then(response => response)
            .catch(reason => console.log(reason));

        console.log(response);

        const newArray = [];

        Object.values(response.data.articleResponses.content).map(
            article => {
                newArray.push(article);
            }
        )
        
        setArticleList(newArray);

        console.log(articleList);
    }

    return (
        <div>
            <button onClick={getArticleList}>page</button>
            <div>
                {articleList.map(
                    (article, index) => {
                        return (
                            <div key={index}>
                                <p>{article.title}</p>
                                <p>{article.content}</p>
                            </div>
                        )
                    }
                )}
            </div>
        </div>
    )
}

export default ArticleList;