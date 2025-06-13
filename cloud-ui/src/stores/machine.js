import { defineStore } from "pinia";
import env from "@/environment/env";
import axios from "axios";

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
        fetchMachines(errorCallback = this.showErrorSnack) {
            axios
                .get(machinesUrl)
                .then((response) => {
                    this.machines = response.data;
                })
                .catch(errorCallback);
        },

        // Not needed when pinia has $reset function
        // clear() {
        //     machines.value = getDefaultState();
        // },
    },
});
