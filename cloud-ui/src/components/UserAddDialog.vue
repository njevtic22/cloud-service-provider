<template>
    <v-dialog v-model="dialog" width="50%">
        <user-form
            icon="mdi-account-plus"
            title="Add user"
            submit-text="Add"
        ></user-form>
    </v-dialog>
</template>

<script setup>
const dialog = defineModel();
</script>

<style scoped></style>
