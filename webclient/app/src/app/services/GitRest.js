import axios from "axios";

class GitRest {

    constructor() {
        this.baseUrl = window.location.pathname + "api/git/";
    }

    updateTemplates = (data) => {
        return axios.post(this.baseUrl + "update-templates", data);
    }

    setupApp = (data) => {
        return axios.post(this.baseUrl + "setup-app", data);
    }

    downloadApp = (appId) => {
        return axios.get(this.baseUrl + "download-app/" + appId);
    }
}

export default GitRest;
