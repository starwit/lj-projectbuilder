import CrudRest from "./CrudRest";

class AppTemplateRest extends CrudRest {

    constructor() {
        super(window.location.pathname + "api/apptemplates/");
    }
}

export default AppTemplateRest;
