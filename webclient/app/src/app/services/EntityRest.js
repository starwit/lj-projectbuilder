import CrudRest from "./CrudRest";
import axios from "axios";

class EntityRest extends CrudRest {
    constructor() {
        super(window.location.pathname + "api/entities");
    }

    updateEntityByAppId(appId, entity) {
        return axios.put(this.baseUrl + "/by-app/" + appId, entity);
    }

    createEntityByApp(appId, entity) {
        return axios.post(this.baseUrl + "/by-app/" + appId, entity);
    }

    findAllEntitiesByApp(appId) {
        return axios.get(this.baseUrl + "/all-by-app/" + appId);
    }

    updateEntitiesByApp(appId, entities) {
        return axios.put(this.baseUrl + "/all-by-app/" + appId, entities);
    }

    createEntitiesByApp(appId, entities) {
        return axios.post(this.baseUrl + "/all-by-app/" + appId, entities);
    }

    findEntityByAppIdAndEntityId(appId, entityId) {
        return axios.get(this.baseUrl + "/by-app/" + appId + "/" + entityId);
    }
}

export default EntityRest;
