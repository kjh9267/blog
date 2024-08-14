import {loginImpl} from "../../infra/loginRepositoryImpl";

export const login = (data, setCookie) => {
    return loginImpl(data, setCookie);
}