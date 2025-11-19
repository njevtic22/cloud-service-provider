import { defineStore } from "pinia";
import env from "@/environment/env";
import axios from "axios";
import { formFilter, formSort } from "@/util/page-filter-util";

const activitiesUrl = `${env.apiUrl}/activities`;

export const useActivitiesStore = defineStore("activity", {
    state: () => ({
        activities: {
            totalElements: 0,
            totalPages: 0,
            data: [],
        },
    }),

    actions: {
        fetchAll(page, size, sort, filter, errorCallback = this.showErroSnack) {
            const overwriteCallback = (response) => {
                this.activities = response.data;
            };

            requestAll(
                page,
                size,
                sort,
                filter,
                overwriteCallback,
                errorCallback
            );
        },
    },
});

function requestAll(page, size, sort, filter, successCallback, errorCallback) {
    const sortStr = formSort(sort, "&");
    const filterStr = formFilter(filter, "&");
    const pageUrl = `${activitiesUrl}?page=${page}&size=${size}${sortStr}${filterStr}`;

    axios.get(pageUrl).then(successCallback).catch(errorCallback);
}
