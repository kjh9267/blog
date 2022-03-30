import axios from "axios";
import {BLOG_ARTICLE} from "../../support/UrlUtils";

export const writeApi = ({data, cookie}) => {
        axios.post(BLOG_ARTICLE, data,
            {headers: {'Authorization': cookie['access_token']}}
        )
            .then(response => console.log(response))
            .catch(reason => {
                console.log(reason);
                alert(reason);
            });
};
