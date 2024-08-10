import {retrieveServiceImpl} from "../infra/retrieveServiceImpl";

export const retrieveService = (url) => {
    return retrieveServiceImpl(url);
}