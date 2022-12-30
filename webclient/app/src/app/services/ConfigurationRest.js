import CrudRest from "./CrudRest";
import axios from "axios";

class ApplicationRest extends CrudRest {
    constructor() {
        super(window.location.pathname + "api/configuration");
    }

    getSentryDsn = data => {
        return axios.get(this.baseUrl + "/sentry/dsn", data);
    };
}

export default ApplicationRest;
