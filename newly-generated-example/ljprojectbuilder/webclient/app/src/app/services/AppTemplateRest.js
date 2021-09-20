import CrudRest from "./CrudRest";

class AppTemplateRest extends CrudRest {

    constructor() {
        super(window.location.pathname + "api/apptemplate");
    }

}

export default AppTemplateRest;
