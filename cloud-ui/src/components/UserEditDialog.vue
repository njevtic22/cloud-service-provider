<template>
    <the-dialog v-model="dialog">
        <v-card> {{ user }} </v-card>
    </the-dialog>
</template>

<script setup>
import { ref } from "vue";

const dialog = defineModel();

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

defineExpose({
    init,
});
</script>

<style scoped></style>
