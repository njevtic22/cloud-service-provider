<template>
    <h1>Machines</h1>
    {{ machines }}
    {{ machines }}
    {{ machines }}
    {{ machines }}
    {{ machines }}
    {{ machines }}
</template>

<script setup>
import { machines, fetchMachines } from "@/stores/machine";

function loadMachines() {
    fetchMachines();
}
loadMachines();
</script>

<style scoped></style>
