import { ref } from "vue";
import env from "@/environment/env";
import axios from "axios";

function getdefaultState() {
    return {
        totalElements: 0,
        totalPages: 0,
        data: [],
    };
}

const machines = ref(getdefaultState());
const machinesUrl = `${env.apiUrl}/virtual-machines`;

function fetchMachines() {
    axios.defaults.headers.common["Authorization"] =
        "Bearer " + response.data.token;

    axios
        .get(machinesUrl)
        .then((response) => {
            machines.value.data = response.data.data;
            machines.value.totalElements = response.data.totalElements;
            machines.value.totalPages = response.data.totalPages;
        })
        .catch((err) => {
            console.log(err);
        });
}

function clear() {
    machines.value = getDefaultState();
}

export { machines, fetchMachines, clear };
