import { defineStore } from "pinia";
import env from "@/environment/env";
import axios from "axios";

const Role = Object.freeze({
    SUPER_ADMIN: "ROLE_SUPER_ADMIN",
    ADMIN: "ROLE_ADMIN",
    USER: "ROLE_USER",
    ANONYMOUS: "ROLE_ANONYMOUS",
});

const loginUrl = `${env.apiUrl}/auth/login`;

const useAuthStore = defineStore("auth", {
    state: () => ({
        role: localStorage.getItem("role") || Role.ANONYMOUS,
    }),

    getters: {
        isSuperAdmin(state) {
            return state.role === Role.SUPER_ADMIN;
        },

        isAdmin(state) {
            return state.role === Role.ADMIN;
        },

        isUser(state) {
            return state.role === Role.USER;
        },

        isAnonymous(state) {
            return state.role === Role.ANONYMOUS;
        },
    },

    actions: {
        login(data, successCallback, errorCallback = this.showErrorSnack) {
            axios
                .post(loginUrl, data)
                .then((response) => {
                    localStorage.setItem("token", response.data.token);
                    localStorage.setItem("role", response.data.role);
                    this.role = response.data.role;

                    axios.defaults.headers.common["Authorization"] =
                        "Bearer " + response.data.token;

                    successCallback(response);
                })
                .catch(errorCallback);
        },

        logout(callback) {
            localStorage.removeItem("token");
            localStorage.removeItem("role");
            this.role = Role.ANONYMOUS;

            delete axios.defaults.headers.common["Authorization"];
            callback();
        },
    },
});

export { Role, useAuthStore };
