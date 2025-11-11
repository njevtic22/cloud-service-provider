import { defineStore } from "pinia";
import env from "@/environment/env";
import axios from "axios";
import { formSort, formFilter } from "@/util/page-filter-util";

const categoriesUrl = `${env.apiUrl}/categories`;

export const useCategoryStore = defineStore("category", {
    state: () => ({
        categories: {
            totalElements: 0,
            totalPages: 0,
            data: [],
        },
    }),

    actions: {
        fetchAll(page, size, sort, filter, errorCallback = this.showErroSnack) {
            const overwriteCallback = (response) => {
                this.categories = response.data;
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

        appendAll(
            page,
            size,
            sort,
            filter,
            errorCallback = this.showErroSnack
        ) {
            const appendCallback = (response) => {
                this.categories.data.push(...response.data.data);
                this.categories.totalElements = response.data.totalElements;
                this.categories.totalPages = response.data.totalPages;
            };
            requestAll(page, size, sort, filter, appendCallback, errorCallback);
        },
    },
});

function requestAll(page, size, sort, filter, successCallback, errorCallback) {
    const sortStr = formSort(sort, "&");
    const filterStr = formFilter(filter, "&");
    const pageUrl = `${categoriesUrl}?page=${page}&size=${size}${sortStr}${filterStr}`;

    axios.get(pageUrl).then(successCallback).catch(errorCallback);
}
