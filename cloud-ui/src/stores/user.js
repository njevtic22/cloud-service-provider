import { defineStore } from "pinia";
import env from "@/environment/env";
import axios from "axios";

const usersUrl = `${env.apiUrl}/users`;

export const useUserStore = defineStore("user", {
    state: () => ({
        users: {
            totalElements: 0,
            totalPages: 0,
            data: [],
        },
    }),

    actions: {
        fetchUsers(errorCallback = this.showErrorSnack) {
            axios
                .get(usersUrl)
                .then((response) => {
                    this.users = response.data;
                })
                .catch(errorCallback);
        },
    },
});
