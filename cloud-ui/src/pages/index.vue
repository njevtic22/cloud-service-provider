<template>
    {{ route.meta.title }}
    <HelloWorld />
</template>

<script setup>
import { useRoute } from "vue-router";

const route = useRoute();
</script>

<style scoped></style>
