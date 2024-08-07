import {useState} from "react";
import {retrievePage} from "../api/retrievePageApi";

function ArticleList({url}) {

    const [articleList, setArticleList] = useState([]);

    const [isShow, setIsShow] = useState([]);

    const [page, setPage] = useState(0);

    const retrieveArticleList = async () => {

        setPage(page + 1);

        const response = await retrievePage({url: url, page: page});

        const articleArray = [...articleList];
        const isShowArray = [];

        response.data.article_responses.content.map(
            (article, index) => {
                articleArray.push(article);
                isShowArray.push(isShow[index]);
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
                            <div className="title">{article.title}</div>
                            <h3>{article.category_name}</h3>
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
            <h2 onClick={retrieveArticleList}>load more</h2>
        </div>
    )
}

export default ArticleList;