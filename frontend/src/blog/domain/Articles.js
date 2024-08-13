import {useEffect, useState} from "react";
import {Article} from "./Article";
import {retrieveArticles} from "./repository/articlesRepository";

export function Articles({url, key}) {
    const [articles, setArticles] = useState([])

    const [page, setPage] = useState(0);

    const appendArticles = async () => {
        const response = await retrieveArticles(url, page);
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
                    return <Article article={article} key={index}/>
                })
            }
            <h2 onClick={appendArticles}>LOAD MORE</h2>
        </div>
    )
}
