import CrudRest from "./CrudRest";
import axios from "axios";

class EnumRest extends CrudRest {
    constructor() {
        super(window.location.pathname + "api/enums");
    }

    updateByApp(appId, entity) {
        return axios.put(this.baseUrl + "/by-app/" + appId, entity);
    }

    createByApp(appId, entity) {
        return axios.post(this.baseUrl + "/by-app/" + appId, entity);
    }

    findAllByApp(appId) {
        return axios.get(this.baseUrl + "/all-by-app/" + appId);
    }
}

export default EnumRest;
