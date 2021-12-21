import CrudRest from "./CrudRest";
import axios from "axios";

class AppTemplateRest extends CrudRest {

    constructor() {
        super(window.location.pathname + "api/apptemplates/");
    }

    updateTemplates = (entity) => {
        return axios.post(this.baseUrl + "updateTemplates");
    }
}

export default AppTemplateRest;
