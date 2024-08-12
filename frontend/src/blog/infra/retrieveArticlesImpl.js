import qs from "qs";
import axios from "axios";

export const retrieveArticlesImpl = (url, page) => {

    const queryString = qs.stringify(
        {
            page: page,
            size: 10
        }
    )
    console.log("page: ", page);

    return axios.get(url + queryString)
        .then(response => response)
        .catch(reason => console.log(reason));
}