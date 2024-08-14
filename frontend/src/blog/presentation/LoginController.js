import React from "react";
import {LoginService} from "../application/LoginService";

function LoginController({setCookie}) {

    return (
        <div>
            <LoginService setCookie={setCookie}/>
        </div>
    )
}


export default LoginController;