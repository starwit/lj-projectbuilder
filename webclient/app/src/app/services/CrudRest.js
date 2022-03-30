import axios from "axios";

class CrudRest {
    constructor(baseUrl) {
        this.baseUrl = baseUrl;
    }

    create = entity => {
        return axios.post(this.baseUrl, entity);
    };

    update = entity => {
        return axios.put(this.baseUrl, entity);
    };

    delete = entityId => {
        return axios.delete(this.baseUrl + "/" + entityId);
    };

    findAll = () => {
        return axios.get(this.baseUrl);
    };

    findById = entityId => {
        return axios.get(this.baseUrl + "/" + entityId);
    };
}

export default CrudRest;
