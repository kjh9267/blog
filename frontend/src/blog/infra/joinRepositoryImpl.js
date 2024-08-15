import axios from "axios";
import {JOIN} from "../../support/UrlUtils";

export const joinImpl = (data) => {
    axios.post(JOIN, data)
        .then(response => console.log(response))
        .catch(reason => {
            console.log(reason);
            alert(reason);
        });
}