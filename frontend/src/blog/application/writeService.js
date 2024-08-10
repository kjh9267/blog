import {writeServiceImpl} from "../infra/writeServiceImpl";

export const writeService = (data, cookie) => {
    return writeServiceImpl(data, cookie);
}