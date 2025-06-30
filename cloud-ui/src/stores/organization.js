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
            mode,
            errorCallback = this.showErrorSnack
        ) {
            const sortStr = formSort(sort, "&");
            const filterStr = formFilter(filter, "&");
            const pageUrl = `${organizationsUrl}?page=${page}&size=${size}${sortStr}${filterStr}`;
            axios
                .get(pageUrl)
                .then((response) => {
                    if (mode === "append") {
                        this.organizations.data.push(...response.data.data);
                        this.organizations.totalElements =
                            response.data.totalElements;
                        this.organizations.totalPages =
                            response.data.totalPages;
                    } else {
                        this.organizations = response.data;
                    }
                })
                .catch(errorCallback);
        },
    },
});
