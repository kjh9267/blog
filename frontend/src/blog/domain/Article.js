import {useState} from "react";


export function Article({article}) {
    const [title, setTitle] = useState(article.title)

    const [content, setContent] = useState(article.content)

    const [visibility, setVisibility] = useState(false)

    const changeVisibility = () => {
        setVisibility(!visibility)
    }

    return (
        <div className="article">
            <div>{article.article_id}</div>
            <div onClick={changeVisibility}>
                <div className="title">{title}</div>
                <div className="content">
                    {visibility === true ? content : null}
                </div>
            </div>
        </div>
    )
}