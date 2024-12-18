import React, {useState} from "react";
import {login} from "./repository/loginRepository";

export function Login({setCookie}) {

    const [email, setEmail] = useState('');

    const [password, setPassword] = useState('');

    const handleInputEmail = (e) => {
        setEmail(e.target.value);
    }

    const handleInputPassword = (e) => {
        setPassword(e.target.value);
    }

    const handleSubmit = async () => {
        await login(
            {
                'email': email,
                'password': password
            },
            setCookie
        )
    }

    return (
        <div className="list">
            <h3>Login</h3>
            <label htmlFor='input_email'>Email : </label>
            <input type='text' id='input_email' onChange={handleInputEmail}/>
            <br/>

            <label htmlFor='input_password'>password : </label>
            <input type='password' id='input_password' onChange={handleInputPassword}/>
            <br/>

            <button onClick={handleSubmit}>Submit</button>
        </div>
    )
}