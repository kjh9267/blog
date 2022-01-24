import React from "react";
import axios from "axios";
import {useState} from "react";
import qs from "qs";
import {QUERY_BLOG_ARTICLES} from "../support/UrlUtils";

function ArticleList(props) {

    const [articleList, setArticleList] = useState([]);

    const [isShow, setIsShow] = useState([]);

    const [page, setPage] = useState(0);

    const getArticleList = async () => {
        const queryString = qs.stringify(
            {
                page: page,
                size: 10
            }
        )
        setPage(page + 1);
        console.log(page);
        const response = await axios.get(props.url + queryString)
            .then(response => response)
            .catch(reason => console.log(reason));

        const articleArray = [...articleList];
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
                    <div className="article" key={index}>
                        <div onClick={ChangeVisible} index={index}>
                            <div className="title">title: {article.title}</div>
                            <h3>category: {article.categoryName}</h3>
                            <div className="content">{isShow[index] === true ? article.content : null}</div>
                        </div>
                    </div>
                )
            }
        )
    }

    const ChangeVisible = (event) => {
        const isShowArray = [...isShow];
        const index = event.target.getAttribute('index');

        const changeState = isShow[index] === true ? false : true

        isShowArray[index] = changeState;

        setIsShow(isShowArray);
    }

    return (
        <div>
            <div className="list">
                {showList()}
            </div>
            <h2 onClick={getArticleList}>load more ... </h2>
        </div>
    )
}

export default ArticleList;