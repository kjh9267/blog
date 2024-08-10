import {registerServiceImpl} from "../infra/registerServiceImpl";

export const registerService = (data) => {
    return registerServiceImpl(data);
}