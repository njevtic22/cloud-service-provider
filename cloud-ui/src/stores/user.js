import { defineStore } from "pinia";
import env from "@/environment/env";
import axios from "axios";
import { formSort, formFilter } from "@/util/page-filter-util";

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
        fetchUsers(
            page,
            size,
            sort,
            filter,
            errorCallback = this.showErrorSnack
        ) {
            const sortStr = formSort(sort, "&");
            const filterStr = formFilter(filter, "&");
            const pageUrl = `${usersUrl}?page=${page}&size=${size}${sortStr}${filterStr}`;
            axios
                .get(pageUrl)
                .then((response) => {
                    this.users = response.data;
                })
                .catch(errorCallback);
        },

        addUser(newUser, successCallback, errorCallback = this.showErrorSnack) {
            axios
                .post(usersUrl, newUser)
                .then(successCallback)
                .catch(errorCallback);
        },

        update(changes, successCallback, errorCallback = this.showErrorSnack) {
            const url = `${usersUrl}/${changes.id}`;
            axios.put(url, changes).then(successCallback).catch(errorCallback);
        },
    },
});
