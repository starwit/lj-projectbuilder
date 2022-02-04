import CrudRest from "./CrudRest";
import axios from 'axios';

class ApplicationRest extends CrudRest {

    constructor() {
        super(window.location.pathname + "api/apps");
    }

    updateAppProperties = (data) => {
        return axios.post(this.baseUrl + "/app-properties", data);
    }
}

export default ApplicationRest;
