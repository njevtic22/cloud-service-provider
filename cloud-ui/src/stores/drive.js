import { defineStore } from "pinia";
import env from "@/environment/env";
import axios from "axios";
import { formFilter, formSort } from "@/util/page-filter-util";

const drivesUrl = `${env.apiUrl}/drives`;

export const useDrivesStore = defineStore("drive", {
    state: () => ({
        drives: {
            totalElements: 0,
            totalPages: 0,
            data: [],
        },
    }),

    actions: {
        fetchAll(page, size, sort, filter, errorCallback = this.showErroSnack) {
            const overwriteCallback = (response) => {
                this.drives = response.data;
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
    const pageUrl = `${drivesUrl}?page=${page}&size=${size}${sortStr}${filterStr}`;

    axios.get(pageUrl).then(successCallback).catch(errorCallback);
}
