<template>
    {{ Object.keys(org) }}
</template>

<script setup>
import { ref } from "vue";

const org = ref({
    id: "",
    name: "",
    description: "",
    logo: "",
});

function init(data) {
    org.value = data;
}

defineExpose({
    init,
});
</script>

<style scoped></style>
