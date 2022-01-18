import React from "react";
import axios from "axios";
import { useState } from "react";
import qs from "qs";
import { QUERY_BLOG_ARTICLES } from "../support/UrlUtils";

function ArticleList(props) {

    const [articleList, setArticleList] = useState([]);

    const [isShow, setIsShow] = useState([]);

    const getArticleList = async () => {
        const queryString = qs.stringify(
            {
                page: props.page,
                size: props.size
            }
        )
        const response = await axios.get(QUERY_BLOG_ARTICLES + queryString)
            .then(response => response)
            .catch(reason => console.log(reason));

        const articleArray = [];
        const isShowArray = [];

        Object.values(response.data.articleResponses.content).map(
            article => {
                articleArray.push(article);
                isShowArray.push(false);
            }
        )

        setArticleList(articleArray);
        setIsShow(isShowArray);
    }

    const showList = () => {
        return articleList.map(
            (article, index) => {
                return (
                    <div key={index}>
                        <div onClick={ChangeVisible} index={index}>
                            <h3>title: {article.title}</h3>
                            <h4>category: {article.categoryName}</h4>
                        </div>
                        <p>{isShow[index] === true ? article.content : null}</p>
                    </div>
                )
            }
        )
    }

    const ChangeVisible = (event) => {
        const isShowArray = [...isShow];
        const index = event.target.getAttribute('index');

        const changeState = isShow[index] === true ? false: true

        isShowArray[index] = changeState;

        setIsShow(isShowArray);
    }

    return (
        <div>
            <button onClick={getArticleList}>page</button>
            <div>
                {showList()}
            </div>
        </div>
    )
}

export default ArticleList;