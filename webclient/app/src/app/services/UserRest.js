import axios from "axios";

class UserRest {
    constructor() {
        this.baseUrl = window.location.pathname + "api/user";
    }

    getUserGroups = () => {
        return axios.get(this.baseUrl + "/groups");
    };
}

export default UserRest;
