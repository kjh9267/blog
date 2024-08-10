import axios from "axios";
import {LOGIN} from "../../support/UrlUtils";

export const loginServiceImpl = (data, setCookie) => {
    axios.post(LOGIN, data)
        .then(response => {
                console.log(response);
                setCookie('access_token', response.data['access_token']);
            }
        )
        .catch(reason => {
            console.log(reason);
            alert(reason);
        });
}

