import axios from "axios";

class GitRest {
    constructor() {
        this.baseUrl = window.location.pathname + "api/git/";
    }

    updateTemplates = (appTemplateId, data) => {
        return axios.post(
            this.baseUrl + "update-templates/" + appTemplateId,
            data
        );
    };

    setupApp = (appId, data) => {
        return axios.post(this.baseUrl + "setup-app/" + appId, data);
    };

    getDownloadUrl = appId => {
        return axios.get(this.baseUrl + "download-app/" + appId);
    };
}

export default GitRest;
