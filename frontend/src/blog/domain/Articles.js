import {useEffect, useState} from "react";
import {retrieveArticles} from "./repository/articlesRepository";
import {Article} from "./Article";

export function Articles() {
    const [articles, setArticles] = useState([])

    const [page, setPage] = useState(0);

    const appendArticles = async () => {
        const response = await retrieveArticles(page);
        const articleList = [...articles]

        console.log(response.data)

        response.data.page.content.map(article => {
            console.log(article)
            articleList.push(article);
        })

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
