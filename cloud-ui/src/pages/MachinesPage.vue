<template>
    <h1>Machines</h1>
    {{ store.machines }}
    {{ store.machines }}
    {{ store.machines }}
    {{ store.machines }}
    {{ store.machines }}
    {{ store.machines }}
</template>

<script setup>
import { useMachineStore } from "@/stores/machine";

const store = useMachineStore();

function loadMachines() {
    store.fetchMachines();
}
loadMachines();
</script>

<style scoped></style>
