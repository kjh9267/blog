import qs from "qs";
import axios from "axios";
import {DISPLAY} from "../../support/UrlUtils";

export const retrieveArticlesImpl = (page) => {
    const queryString = qs.stringify(
        {
            page: page,
            size: 10
        }
    )
    console.log("page: ", page);

    return axios.get(DISPLAY + '?' + queryString)
        .then(response => response)
        .catch(reason => console.log(reason));
}