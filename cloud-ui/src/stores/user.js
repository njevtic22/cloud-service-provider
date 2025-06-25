import { defineStore } from "pinia";
import env from "@/environment/env";
import axios from "axios";
import { formSort } from "@/util/page-filter-util";

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
        fetchUsers(page, size, sort, errorCallback = this.showErrorSnack) {
            const sortStr = formSort(sort, "&");
            const pageUrl = `${usersUrl}?page=${page}&size=${size}${sortStr}`;
            axios
                .get(pageUrl)
                .then((response) => {
                    this.users = response.data;
                })
                .catch(errorCallback);
        },
    },
});
