import React from 'react';
import axios from 'axios';
import { useState } from 'react';
import { BLOG_ARTICLE } from '../support/UrlUtils';

function Write(props) {

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
        axios.post(BLOG_ARTICLE,
                    {
                        'title': title,
                        'content': content,
                        'categoryName': categoryName
                    },
                    {headers: {'Authorization': props.cookie['access_token']}}
                )
                .then(response => console.log(response))
                .catch(reason => {
                    console.log(reason);
                    alert(reason);
                });
    }

    return(
        <div className="list">
            <h3>Write</h3>
            <label htmlFor='input_title'>Title : </label>
            <input type='text' name='input_title' value={title} onChange={handleInputTitle} />
            <br/>

            <label htmlFor='input_content'>Content : </label>
            <input type='text' name='input_content' value={content} onChange={handleInputContent} />
            <br/>

            <label htmlFor='input_categoryName'>Category :</label>
            <input type='text' name='input_categoryName' value={categoryName} onChange={handleInputCategoryName} />
            <br/>

            <button onClick={handleSubmit}>Submit</button>
        </div>
    )
}

export default Write;