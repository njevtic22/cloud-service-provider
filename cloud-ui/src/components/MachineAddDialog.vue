<template>
    <the-dialog v-model="dialog">
        <the-dialog-card
            icon="mdi-server-plus"
            title="Add Virtual Machine"
            submit-text="Add"
            @submit="submit"
            @cancel="close"
        >
            Form
        </the-dialog-card>
    </the-dialog>
</template>

<script setup>
const emit = defineEmits(["submit"]);
const dialog = defineModel();

function submit() {
    emit("submit", { name: "Machine" });
    close();
}

function close() {
    dialog.value = false;
}
</script>

<style scoped></style>
