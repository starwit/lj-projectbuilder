import CrudRest from "./CrudRest";

class TemplateFileRest extends CrudRest {

    constructor() {
        super(window.location.pathname + "api/templatefile");
    }

}

export default TemplateFileRest;
