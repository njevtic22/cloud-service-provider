<template>
    <h1>Machine Page: {{ route.params.id }}</h1>
    {{ machine }}

    <div class="d-flex justify-space-between">
        <div class="drives-table">
            <h3>Connected drives</h3>
            <drives-table
                v-if="machine"
                :constant-filter="{ machineId: machine.id }"
            ></drives-table>
        </div>

        <div class="drives-table">
            <h3>Activities</h3>
            <activities-table></activities-table>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";
import { useMachineStore } from "@/stores/machine.js";

const route = useRoute();
const store = useMachineStore();
const machine = ref(null);

function loadMachine() {
    store.fetch(route.params.id, (response) => (machine.value = response.data));
}
loadMachine();
</script>

<style scoped>
.drives-table {
    max-width: 48%;
}
</style>
