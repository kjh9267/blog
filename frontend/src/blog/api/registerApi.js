import axios from "axios";
import {REGISTER} from "../../support/UrlUtils";

export const registerApi = ({data}) => {
    axios.post(REGISTER, data)
        .then(response => console.log(response))
        .catch(reason => {
            console.log(reason);
            alert(reason);
        });
}

