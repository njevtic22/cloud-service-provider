<template>
    <h1>Machine Page: {{ route.params.id }}</h1>
    {{ machine }}
</template>

<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";
import { useMachineStore } from "@/stores/machine";

const route = useRoute();
const store = useMachineStore();
const machine = ref({});

function loadMachine() {
    store.fetch(route.params.id, (response) => (machine.value = response.data));
}
loadMachine();
</script>

<style scoped></style>
