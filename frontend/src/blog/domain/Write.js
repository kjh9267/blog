import React, {useState} from "react";
import {write} from "./repository/writeRepository";

export function Write({cookie}) {

    const [title, setTitle] = useState('');

    const [content, setContent] = useState('');

    const [categoryName, setCategoryName] = useState('');

    const handleInputTitle = (e) => {
        setTitle(e.target.value)
    }

    const handleInputContent = (e) => {
        setContent(e.target.value)
    }

    const handleInputCategoryName = (e) => {
        setCategoryName(e.target.value)
    }

    const handleSubmit = async () => {
        await write(
            {
                'title': title,
                'content': content,
                'categoryName': categoryName
            },
            cookie
        )
    }

    return(
        <div className="list">
            <h3>Write</h3>
            <label htmlFor='input_title'>Title : </label>
            <input type='text' id='input_title' onChange={handleInputTitle} />
            <br/>

            <label htmlFor='input_content'>Content : </label>
            <textarea id='input_content' onChange={handleInputContent} />
            <br/>

            <label htmlFor='input_categoryName'>Category :</label>
            <input type='text' id='input_categoryName' onChange={handleInputCategoryName} />
            <br/>

            <button onClick={handleSubmit}>Submit</button>
        </div>
    )
}