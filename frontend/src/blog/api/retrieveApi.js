import axios from "axios";

export const retrieveApi = ({url}) => {

    return axios.get(url)
        .then(response => response)
        .catch(reason => console.log(reason));
}