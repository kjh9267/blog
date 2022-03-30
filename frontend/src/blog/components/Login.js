import React, {useState} from "react";
import {loginApi} from "../api/loginApi";

function Login({setCookie}) {

    const [email, setEmail] = useState('');

    const [password, setPassword] = useState('');

    const [userName, setUserName] = useState('');

    const handleInputEmail = (e) => {
        setEmail(e.target.value);
    }

    const handleInputPassword = (e) => {
        setPassword(e.target.value);
    }

    const handleInputUserName = (e) => {
        setUserName(e.target.value);
    }

    const handleSubmit = async () => {
        await loginApi(
            {data:
                    {
                        'email': email,
                        'name': userName,
                        'password': password
                    },
                setCookie: setCookie}
        )
    }

    return (
        <div className="list">
            <h3>Login</h3>
            <label htmlFor='input_email'>Email : </label>
            <input type='text' name='input_email' value={email} onChange={handleInputEmail}/>
            <br/>

            <label htmlFor='input_userName'>userName : </label>
            <input type='text' name='input_userName' value={userName} onChange={handleInputUserName}/>
            <br/>

            <label htmlFor='input_password'>password : </label>
            <input type='password' name='input_password' value={password} onChange={handleInputPassword}/>
            <br/>

            <button onClick={handleSubmit}>Submit</button>
        </div>
    )
}


export default Login;