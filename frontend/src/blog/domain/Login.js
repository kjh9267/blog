import React, {useState} from "react";
import {loginImpl} from "../infra/loginRepositoryImpl";

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
        await loginImpl(
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
            <input type='text' name='input_email' value={email} onChange={handleInputEmail}/>
            <br/>

            <label htmlFor='input_password'>password : </label>
            <input type='password' name='input_password' value={password} onChange={handleInputPassword}/>
            <br/>

            <button onClick={handleSubmit}>Submit</button>
        </div>
    )
}