import {loginServiceImpl} from "../infra/loginServiceImpl";

export const loginService = (data, setCookie) => {
    return loginServiceImpl(data, setCookie)
}