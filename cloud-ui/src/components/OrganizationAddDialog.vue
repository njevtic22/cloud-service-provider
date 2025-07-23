<template>
    <the-dialog v-model="dialog">
        <!-- Convert to <slot></slot> or <v-card><slot></slot></v-card> -->
        <organization-add-form></organization-add-form>
    </the-dialog>
</template>

<script setup>
import { inject } from "vue";

const emit = defineEmits(["submit"]);

const snackbar = inject("snackbar");
const dialog = defineModel();

function submit() {
    dialog.value = false;
    emit("submit");
    snackbar("Organization added", 3000);
}
</script>

<style scoped></style>
