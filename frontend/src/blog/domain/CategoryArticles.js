import {useEffect, useState} from "react";
import {CategoryArticle} from "./CategoryArticle";
import {retrieveCategoryArticles} from "./repository/categoryArticlesRepository";

export function CategoryArticles({url, key}) {
    const [articles, setArticles] = useState([])

    const [page, setPage] = useState(0);

    const appendArticles = async () => {
        const response = await retrieveCategoryArticles(url, page);
        const articleList = [...articles]

        response.data.article_responses.content.map(article => {
            articleList.push(article);
        })

        console.log(response.data)

        setArticles(articleList)
        setPage(page + 1);
    }

    useEffect(() => {
        console.log("render")
        appendArticles()
    }, []);

    return (
        <div className="list">
            {
                articles.map((article, index) => {
                    return <CategoryArticle article={article} key={index}/>
                })
            }
            <h2 onClick={appendArticles}>LOAD MORE</h2>
        </div>
    )
}
