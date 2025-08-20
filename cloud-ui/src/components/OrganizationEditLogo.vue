<template>{{ Object.keys(logo) }}</template>

<script setup>
import { ref } from "vue";

const logo = ref({});

function init(logo2) {
    console.log(logo2);

    logo.value = logo2;
    console.log(logo.value);
}

defineExpose({
    init,
});
</script>

<style scoped></style>
