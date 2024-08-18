import {useState} from "react";


export function Article({article}) {
    const [visibility, setVisibility] = useState(false)

    const changeVisibility = () => {
        setVisibility(!visibility)
    }

    return (
        <div className="article">
            <div>{article[0]}</div>
            <div onClick={changeVisibility}>
                title:
                <div className="title">{article[1]}</div>
                writer:
                <div className="title">{article[4]}</div>
                category:
                <div className="title">{article[3]}</div>
                <div className="content">
                    {visibility === true ? article[2] : null}
                </div>
            </div>
        </div>
    )
}