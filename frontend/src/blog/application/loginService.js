import {Login} from "../domain/Login";

export function LoginService({setCookie}) {

    return (
        <div>
            <Login setCookie={setCookie}/>
        </div>
    )
}
