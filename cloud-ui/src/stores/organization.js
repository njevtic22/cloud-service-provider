import { defineStore } from "pinia";
import env from "@/environment/env";
import axios from "axios";
import { formSort, formFilter } from "@/util/page-filter-util";

const organizationsUrl = `${env.apiUrl}/organizations`;

export const useOrganizationStore = defineStore("organization", {
    state: () => ({
        organizations: {
            totalElements: 0,
            totalPages: 0,
            data: [],
        },
    }),

    actions: {
        fetchOrganizations(
            page,
            size,
            sort,
            filter,
            errorCallback = this.showErrorSnack
        ) {
            const overwriteCallback = (response) => {
                this.organizations = response.data;
            };
            requestOrganizations(
                page,
                size,
                sort,
                filter,
                overwriteCallback,
                errorCallback
            );
        },

        appendOrganizations(
            page,
            size,
            sort,
            filter,
            errorCallback = this.showErrorSnack
        ) {
            const appendCallback = (response) => {
                this.organizations.data.push(...response.data.data);
                this.organizations.totalElements = response.data.totalElements;
                this.organizations.totalPages = response.data.totalPages;
            };
            requestOrganizations(
                page,
                size,
                sort,
                filter,
                appendCallback,
                errorCallback
            );
        },
    },
});

function requestOrganizations(
    page,
    size,
    sort,
    filter,
    successCallback,
    errorCallback
) {
    const sortStr = formSort(sort, "&");
    const filterStr = formFilter(filter, "&");
    const pageUrl = `${organizationsUrl}?page=${page}&size=${size}${sortStr}${filterStr}`;

    axios.get(pageUrl).then(successCallback).catch(errorCallback);
}
