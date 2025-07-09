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
import { ref } from "vue";

const dialog = defineModel({ default: false });
const form = ref(null);

function open(user) {
    dialog.value = true;

    const id = setTimeout(() => {
        form.value.init(user);
        clearTimeout(id);
    }, 0.1);
}

function submit(user) {
    dialog.value = false;
    console.log(user);
}

defineExpose({
    open,
});
</script>

<style scoped></style>
