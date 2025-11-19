<template>
    {{ store.activities.data }}
    {{ store.activities.totalElements }}
</template>

<script setup>
import { useRoute } from "vue-router";
import { useActivitiesStore } from "@/stores/activity";

const route = useRoute();

const store = useActivitiesStore();
store.fetchAll(0, 21, {}, { "machine-id": route.params.id });
</script>

<style scoped></style>
