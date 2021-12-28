import CrudRest from "./CrudRest";
import axios from "axios";

class AppTemplateRest extends CrudRest {

    constructor() {
        super(window.location.pathname + "api/apptemplates");
    }

    updateTemplates = (data) => {
        return axios.post(window.location.pathname + "api/updatetemplates", data);
    }
}

export default AppTemplateRest;
