import CrudRest from "./CrudRest";
import axios from 'axios';

class ApplicationRest  extends CrudRest {

    constructor() {
        this.baseUrl = window.location.pathname + "/api/apps";
    }

    getAll = () => {
        return axios.get(this.baseUrl);
    }

    createApp = (data) => {
        return axios.put(this.baseUrl, data);
    }

    updateApp = (data) => {
        return axios.post(this.baseUrl, data);
    }

    updateAppProperties = (data) => {
        return axios.post(this.baseUrl + "/appproperties", data);
    }

    getAppById = (appId) => {
        return axios.get(this.baseUrl + "/" + appId);
    }

    deleteAppById = (appId) => {
        return axios.delete(this.baseUrl + "/" + appId);
    }
}

export default ApplicationRest;
