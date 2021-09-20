import CrudRest from "./CrudRest";

class AppRest extends CrudRest {

    constructor() {
        super(window.location.pathname + "api/app");
    }

}

export default AppRest;
