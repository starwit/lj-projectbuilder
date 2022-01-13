import CrudRest from "./CrudRest";
import axios from "axios";

class AppTemplateRest extends CrudRest {

    constructor() {
        super(window.location.pathname + "api/apptemplates");
    }
}

export default AppTemplateRest;
