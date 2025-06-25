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
        fetchUsers(page, size, errorCallback = this.showErrorSnack) {
            const pageUrl = `${usersUrl}?page=${page}&size=${size}`;
            axios
                .get(pageUrl)
                .then((response) => {
                    this.users = response.data;
                })
                .catch(errorCallback);
        },
    },
});
