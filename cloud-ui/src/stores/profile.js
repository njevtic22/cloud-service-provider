import { defineStore } from "pinia";
import env from "@/environment/env";
import axios from "axios";

const profileUrl = `${env.apiUrl}/users/profile`;

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
    },
});
