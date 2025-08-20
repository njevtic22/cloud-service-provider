<template>{{ Object.keys(data) }}</template>

<script setup>
import { ref } from "vue";

const data = ref({});

function init(data2) {
    data.value = data2;
}

defineExpose({
    init,
});
</script>

<style scoped></style>
