<template>
    <h1>Machine Page: {{ route.params.id }}</h1>
    {{ machine }}
    <drives-table
        v-if="machine"
        :constant-filter="{ machineId: machine.id }"
        class="drives-table"
    ></drives-table>
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
    margin: auto;
    width: 100%;
    max-width: 75%;
}
</style>
