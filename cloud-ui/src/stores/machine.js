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
                "Bearer " + response.data.token;

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
