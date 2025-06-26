<template>
    <v-card :append-icon="icon" :title="title">
        <v-card-text>
            <h1>This is card</h1>
        </v-card-text>

        <v-card-actions>
            <v-btn @click="submit" color="primary" variant="elevated">
                {{ submitText }}
            </v-btn>
            <v-btn @click="cancel" color="primary" variant="outlined">
                {{ cancelText }}
            </v-btn>
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

const emit = defineEmits(["submit", "cancel"]);

function clear() {
    console.log("Called clear");
}

function submit() {
    // Retrieve value
    clear();
    emit("submit", true);
}

function cancel() {
    clear();
    emit("cancel");
}
</script>

<style scoped></style>
