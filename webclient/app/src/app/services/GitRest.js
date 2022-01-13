import axios from "axios";

class GitRest {

    constructor() {
        this.baseUrl = window.location.pathname + "api/git/";
    }

    updateTemplates = (data) => {
        return axios.post(this.baseUrl + "update-templates", data);
    }

    setupApp = (data) => {
        return axios.post(this.baseUrl + "api/setup-app", data);
    }

    downloadApp = (data) => {
        return axios.post(this.baseUrl + "api/download-app", data);
    }
}

export default GitRest;
