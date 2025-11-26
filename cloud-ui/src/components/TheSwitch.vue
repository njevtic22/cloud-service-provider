<template>
    <v-switch
        v-model="model"
        :label="label"
        :inset="inset"
        :color="color"
        :hide-details="hideDetails"
    ></v-switch>
</template>

<script setup>
const props = defineProps({
    label: {
        type: String,
        required: true,
    },
    inset: {
        type: Boolean,
        default: false,
    },
    color: {
        type: String,
        default: "primary",
    },
    hideDetails: {
        type: Boolean,
        default: true,
    },
});

const model = defineModel();
</script>

<style scoped></style>
