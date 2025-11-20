<template>
    <h1>Machine Page: {{ route.params.id }}</h1>
    {{ machine }}

    <div v-if="machine" class="d-flex justify-space-between">
        <div class="table">
            <h3>Connected drives</h3>
            <drives-table
                :constant-filter="{ 'machine-id': machine.id }"
            ></drives-table>
        </div>

        <div class="table">
            <h3>Activities</h3>
            <activities-table
                :initial-size="5"
                :constant-filter="{ 'machine-id': machine.id }"
            ></activities-table>
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
.table {
    width: 48%;
}
</style>
