import axios from "axios";

class AppSetupRest {

    downloadApp = (data) => {
        return axios.post(window.location.pathname + "api/setup-app", data);
    }
}

export default AppSetupRest;
