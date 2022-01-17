import React from "react";
import { ReactMarkdown } from "react-markdown/lib/react-markdown";

function Article(props) {

    return(
        <div>
            <ReactMarkdown>{props.title}</ReactMarkdown>
            <ReactMarkdown>{props.content}</ReactMarkdown>
        </div>
    )
}


export default Article;