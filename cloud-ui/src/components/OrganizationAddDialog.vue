<template>
    <the-dialog v-model="dialog">
        <the-dialog-card
            @submit="submit"
            @cancel="cancel"
            :actions-disabled="true"
            icon="mdi-domain-plus"
            title="Add organization"
            submit-text="Add"
        >
            <organization-add-form></organization-add-form>
        </the-dialog-card>
    </the-dialog>
</template>

<script setup>
import { inject } from "vue";

const emit = defineEmits(["submit"]);

const snackbar = inject("snackbar");
const dialog = defineModel();

function submit() {
    dialog.value = false;
    // emit("submit");
    // snackbar("Organization added", 3000);
}

function cancel() {
    dialog.value = false;
    // clear form
}
</script>

<style scoped></style>
