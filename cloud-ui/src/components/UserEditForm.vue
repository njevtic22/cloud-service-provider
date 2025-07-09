<template>
    <v-card :append-icon="icon" :title="title">
        <v-card-text>
            {{ user }}
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
import { ref } from "vue";

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

const user = ref({
    name: "",
    surname: "",
    email: "",
    username: "",
    organization: null,
    role: "",
    password: "",
    repeatedPassword: "",
});

function init(data) {
    user.value = data;
}

function submit() {
    // validate form
    // resetForm is called from UserEditDialog if request is successful

    emit("submit", true);
}

function cancel() {
    emit("cancel");
    // resetForm
}

defineExpose({
    init,
});
</script>

<style scoped></style>
