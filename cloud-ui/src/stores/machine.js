import { defineStore } from "pinia";
import env from "@/environment/env";
import axios from "axios";
import { formFilter, formSort } from "@/util/page-filter-util";

const machinesUrl = `${env.apiUrl}/virtual-machines`;

export const useMachineStore = defineStore("machine", {
    state: () => ({
        machines: {
            totalElements: 0,
            totalPages: 0,
            data: [],
        },
    }),

    getters: {
        machinesGetter: (state) => state,
    },

    actions: {
        fetchMachines(
            page,
            size,
            sort,
            filter,
            errorCallback = this.showErrorSnack
        ) {
            const overwriteCallback = (response) => {
                this.machines = response.data;
            };

            requestMachines(
                page,
                size,
                sort,
                filter,
                overwriteCallback,
                errorCallback
            );
        },

        fetch(id, successCallback, errorCallback = this.showErrorSnack) {
            const url = `${machinesUrl}/${id}`;
            axios.get(url).then(successCallback).catch(errorCallback);
        },

        add(newMachine, successCallback, errorCallback = this.showErrorSnack) {
            axios
                .post(machinesUrl, newMachine)
                .then(successCallback)
                .catch(errorCallback);
        },

        // Not needed when pinia has $reset function
        // clear() {
        //     machines.value = getDefaultState();
        // },
    },
});

function requestMachines(
    page,
    size,
    sort,
    filter,
    successCallback,
    errorCallback
) {
    const sortStr = formSort(sort, "&");
    const filterStr = formFilter(filter, "&");
    const pageUrl = `${machinesUrl}?page=${page}&size=${size}${sortStr}${filterStr}`;

    axios.get(pageUrl).then(successCallback).catch(errorCallback);
}
