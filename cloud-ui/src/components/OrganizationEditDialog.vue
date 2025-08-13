<template>
    <the-dialog v-model="dialog">
        <the-dialog-card
            icon="mdi-pencil"
            title="Edit organization"
            submit-text="Save"
        >
            <organization-edit-form ref="form"></organization-edit-form>
        </the-dialog-card>
    </the-dialog>
</template>

<script setup>
import { ref } from "vue";

const dialog = defineModel({ default: false });
const form = ref(null);

function open(org) {
    dialog.value = true;

    setTimeout(() => {
        form.value.init(org);
    }, 0.1);
}

defineExpose({
    open,
});
</script>

<style scoped></style>
