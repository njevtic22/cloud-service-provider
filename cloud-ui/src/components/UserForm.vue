<template>
    <v-card :prepend-icon="icon" :title="title">
        <v-card-text>
            <h1>This is card</h1>
        </v-card-text>

        <v-card-actions>
            <v-btn>{{ submitText }}</v-btn>
            <v-btn>{{ cancelText }}</v-btn>
        </v-card-actions>
    </v-card>
</template>

<script setup>
defineProps({
    icon: String,
    title: String,
    "submit-text": String,
    "cancel-text": {
        type: String,
        default: "Cancel",
    },
});
</script>

<style scoped></style>
