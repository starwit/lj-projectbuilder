import axios from 'axios';

class VersionRest {

    constructor() {
        this.baseUrl = window.location.pathname + "/monitoring";
    }

    info = () => {
        return axios.get(this.baseUrl + "/info");
    }
}

export default VersionRest;
