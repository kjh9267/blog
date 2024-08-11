import {useEffect, useState} from "react";
import {fillTempLists, updatePage, updateVisibility} from "../domain/articles";
import {retrievePageService} from "../application/retrievePageService";

function ArticleListController({url}) {

    const [articleList, setArticleList] = useState([]);

    const [visibilityList, setVisibilityList] = useState([]);

    const [pageNum, setPageNum] = useState(0);

    const retrieveArticles = async () => {
        const response = await retrievePageService(url, pageNum);
        const [tempArticleList, tempVisibilityList] = fillTempLists(response.data.article_responses.content, articleList, visibilityList)

        setArticleList(tempArticleList);
        setVisibilityList(tempVisibilityList);

        const tempPageNum = updatePage(pageNum);
        setPageNum(tempPageNum);
    }

    useEffect(() => {
        retrieveArticles()
    }, [])

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
        const tempVisibilityList = updateVisibility(event, visibilityList);
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

export default ArticleListController;