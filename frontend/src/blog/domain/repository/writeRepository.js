import {writeImpl} from "../../infra/writeRepositoryImpl";

export const write = (data, cookie) => {
    return writeImpl(data, cookie)
}