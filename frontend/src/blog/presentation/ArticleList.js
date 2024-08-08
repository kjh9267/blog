import {useState} from "react";
import {retrievePage} from "../api/retrievePageApi";
import {articles, fillTempLists, page, updatePage, updateVisibility, visibilities} from "../domain/articles";

function ArticleList({url}) {

    const [articleList, setArticleList] = useState(articles);

    const [visibilityList, setVisibilityList] = useState(visibilities);

    const [pageNum, setPageNum] = useState(page);

    const retrieveArticles = async () => {
        const tempPageNum = updatePage();
        setPageNum(tempPageNum);

        const response = await retrievePage({url: url, page: pageNum});

        const [tempArticleList, tempVisibilityList] = fillTempLists(response.data.article_responses.content)

        setArticleList(tempArticleList);
        setVisibilityList(tempVisibilityList);
    }

    const showList = () => {
        return articleList.map(
            (article, index) => {
                return (
                    <div className="article" key={index}>
                        <div onClick={changeVisibility} index={index}>
                            <div className="title">{article.title}</div>
                            <h3>{article.category_name}</h3>
                            <div className="content">{visibilityList[index] === true ? article.content : null}</div>
                        </div>
                    </div>
                )
            }
        )
    }

    const changeVisibility = (event) => {
        const tempVisibilityList = updateVisibility(event);
        setVisibilityList(tempVisibilityList);
    }

    return (
        <div>
            <div className="list">
                {showList()}
            </div>
            <h2 onClick={retrieveArticles}>load more</h2>
        </div>
    )
}

export default ArticleList;