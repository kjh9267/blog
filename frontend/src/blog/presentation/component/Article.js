import React from "react";

function Article({title, categoryName, content, showContent, hideContent, isContentVisible}) {

    return (
        <div className="article">
            <div className="title" onClick={isContentVisible ? hideContent : showContent}>title: {title}</div>
            <div>category: {categoryName}</div>
            <div className="content">{isContentVisible ? content : null}</div>
        </div>
    )
}

export default Article;