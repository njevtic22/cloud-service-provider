import { defineStore } from "pinia";
import env from "@/environment/env";
import axios from "axios";

const userUrl = `${env.apiUrl}/users`;
const profileUrl = `${userUrl}/profile`;

export const useProfileStore = defineStore("profile", {
    state: () => ({
        profile: {
            id: -1,
            name: "",
            surname: "",
            email: "",
            username: "",
            role: "",
            organization: -1,
            organizationName: "",
        },
    }),

    actions: {
        fetchProfile(errorCallback = this.showErrorSnack) {
            axios
                .get(profileUrl)
                .then((response) => {
                    this.profile = response.data;
                })
                .catch(errorCallback);
        },

        update(changes, successCallback, errorCallback = this.showErrorSnack) {
            axios
                .put(`${userUrl}/${changes.id}`, changes)
                .then((response) => {
                    this.profile = response.data;
                    successCallback(response);
                })
                .catch(errorCallback);
        },
    },
});
