import React, {useState} from 'react';
import {writeApi} from "../api/writeApi";

function Write({cookie}) {

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

    const handleSubmit = () => {
        writeApi({
            data: {
                'title': title,
                'content': content,
                'categoryName': categoryName
            },
            cookie: cookie
        })
    }

    return(
        <div className="list">
            <h3>Write</h3>
            <label htmlFor='input_title'>Title : </label>
            <input type='text' name='input_title' value={title} onChange={handleInputTitle} />
            <br/>

            <label htmlFor='input_content'>Content : </label>
            <textarea name='input_content' value={content} onChange={handleInputContent} />
            <br/>

            <label htmlFor='input_categoryName'>Category :</label>
            <input type='text' name='input_categoryName' value={categoryName} onChange={handleInputCategoryName} />
            <br/>

            <button onClick={handleSubmit}>Submit</button>
        </div>
    )
}

export default Write;