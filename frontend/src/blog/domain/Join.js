import React, {useState} from "react";
import {join} from "./repository/joinRepository";

export function Join() {

    const [email, setEmail] = useState('');

    const [password, setPassword] = useState('');

    const [name, setName] = useState('');

    const handleInputEmail = (e) => {
        setEmail(e.target.value);
    }

    const handleInputPassword = (e) => {
        setPassword(e.target.value);
    }

    const handleInputName = (e) => {
        setName(e.target.value);
    }

    const handleSubmit = async () => {
        await join(
            {
                'email': email,
                'name': name,
                'password': password
            });
    }

    return (
        <div className="list">
            <h3>Join</h3>
            <label htmlFor='input_email'>Email : </label>
            <input type='text' id='input_email' onChange={handleInputEmail}/>
            <br/>

            <label htmlFor='input_name'>userName : </label>
            <input type='text' id='input_name' onChange={handleInputName}/>
            <br/>

            <label htmlFor='input_password'>password : </label>
            <input type='password' id='input_password' onChange={handleInputPassword}/>
            <br/>

            <button onClick={handleSubmit}>Submit</button>
        </div>
    )
}