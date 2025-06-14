import { defineStore } from "pinia";
import env from "@/environment/env";
import axios from "axios";

const loginUrl = `${env.apiUrl}/auth/login`;

function logIn(data, successCallback, errorCallback = this.showErrorSnack) {
    axios
        .post(loginUrl, data)
        .then((response) => {
            localStorage.setItem("token", response.data.token);
            localStorage.setItem("role", response.data.role);

            axios.defaults.headers.common["Authorization"] =
                "Bearer " + response.data.token;

            successCallback(response);
        })
        .catch(errorCallback);
}

function logOut() {
    localStorage.removeItem("token");
    localStorage.removeItem("role");

    delete axios.defaults.headers.common["Authorization"];
}

export { logIn, logOut };
