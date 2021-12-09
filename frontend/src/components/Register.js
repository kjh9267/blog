import React from 'react'
import axios from 'axios';
import { useState } from 'react';


function Register() {

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

    const handleSubmit = () => {
        axios.post("http://localhost:8080/api/member/register",
                    {
                        'email': email,
                        'name': userName,
                        'password': password
                    }
                )
                .then(response => console.log(response))
                .catch(reason => console.log(reason));
    }

    return (
        <div>
            <h3>Register</h3>
            <label htmlFor='input_email'>Email : </label>
            <input type='text' name='input_email' value={email} onChange={handleInputEmail} />
            <br/>
            
            <label htmlFor='input_userName'>userName : </label>
            <input type='text' name='input_userName' value={userName} onChange={handleInputUserName} />
            <br/>

            <label htmlFor='input_password'>password : </label>
            <input type='password' name='input_password' value={password} onChange={handleInputPassword} />
            <br/>

            <button onClick={handleSubmit}>Submit</button>
        </div>
    )
}


export default Register;