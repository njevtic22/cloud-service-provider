<template>
    <the-dialog v-model="dialog">
        <user-edit-form
            @submit="submit"
            @cancel="dialog = false"
            icon="mdi-account-edit"
            title="Edit user"
            submit-text="Save"
            ref="form"
        ></user-edit-form>
    </the-dialog>
</template>

<script setup>
import { ref, inject } from "vue";
import { useUserStore } from "@/stores/user.js";

const emit = defineEmits(["submit"]);

const snackbar = inject("snackbar");
const store = useUserStore();
const dialog = defineModel({ default: false });
const form = ref(null);

function open(user) {
    dialog.value = true;

    const id = setTimeout(() => {
        user.organization = {
            id: user.organization,
            name: user.organizationName,
        };
        form.value.init(user);
        clearTimeout(id);
    }, 0.1);
}

function submit(user) {
    user.organization = user.organization.id;
    store.update(user, () => {
        dialog.value = false;
        emit("submit");
        snackbar("User updated", 3 * 3000);
    });
}

defineExpose({
    open,
});
</script>

<style scoped></style>
