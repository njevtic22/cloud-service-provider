import { defineStore } from "pinia";
import env from "@/environment/env";
import axios from "axios";

function getdefaultState() {
    return {
        totalElements: 0,
        totalPages: 0,
        data: [],
    };
}

const machinesUrl = `${env.apiUrl}/virtual-machines`;

export const useMachineStore = defineStore("machine", {
    state: () => ({
        machines: getdefaultState(),
    }),

    getters: {
        machinesGetter: (state) => state,
    },

    actions: {
        fetchMachines() {
            axios.defaults.headers.common["Authorization"] =
                "Bearer " +
                "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJhZG1pbi1hcHAiLCJzdWIiOiIzMSIsImF1ZCI6IndlYiIsImlhdCI6MTc0OTc1NDMwNCwiZXhwIjoxNzQ5NzcyMzA0fQ.1JmOnOH9WIRewLwNFEZcdvEBuW7k0_oYoaujRNL5S4y9dYNbet5300OlffgHEZBHjB0KooqOnwbT4bXyUezRUA";

            axios
                .get(machinesUrl)
                .then((response) => {
                    this.machines.data = response.data.data;
                    this.machines.totalElements = response.totalElements;
                    this.machines.totalPages = response.data.totalPages;
                })
                .catch((err) => {
                    console.log(err);
                });
        },

        // Not needed when pinia has $reset function
        // clear() {
        //     machines.value = getDefaultState();
        // },
    },
});
