<template>
    <the-dialog v-model="dialog">
        <!-- Convert to <slot></slot> or <v-card><slot></slot></v-card> -->
        <user-add-form
            @submit="submit"
            @cancel="dialog = false"
            icon="mdi-account-plus"
            title="Add user"
            submit-text="Add"
        ></user-add-form>
    </the-dialog>
</template>

<script setup>
import { inject } from "vue";
import { useUserStore } from "@/stores/user.js";

const emit = defineEmits(["submit"]);

const snackbar = inject("snackbar");
const dialog = defineModel();
const store = useUserStore();

function submit(user) {
    user.organization = user.organization.id;
    store.addUser(user, () => {
        dialog.value = false;
        emit("submit");
        snackbar("User added", 3000);
    });
}
</script>

<style scoped></style>
