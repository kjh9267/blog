import {useState} from "react";


export function CategoryArticle({article}) {
    const [visibility, setVisibility] = useState(false)

    const changeVisibility = () => {
        setVisibility(!visibility)
    }

    return (
        <div className="article">
            <div>{article.article_id}</div>
            <div onClick={changeVisibility}>
                <div className="title">{article.title}</div>
                <div className="content">
                    {visibility === true ? article.content : null}
                </div>
            </div>
        </div>
    )
}