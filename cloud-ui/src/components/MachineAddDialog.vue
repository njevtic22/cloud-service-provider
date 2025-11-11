<template>
    <the-dialog v-model="dialog" @afterLeave="clear">
        <the-dialog-card
            icon="mdi-server-plus"
            title="Add Virtual Machine"
            submit-text="Add"
            :submit-disabled="!form?.isValid"
            @submit="submit"
            @cancel="close"
        >
            <virtual-machine-add-form
                v-model="machine"
                ref="form"
            ></virtual-machine-add-form>
        </the-dialog-card>
    </the-dialog>
</template>

<script setup>
import { ref } from "vue";

const emit = defineEmits(["submit"]);
const dialog = defineModel();
const form = ref(null);

const machine = ref({
    name: "",
    categoryId: null,
    organizationId: null,
});

async function submit() {
    const { valid } = await form.value.validate();
    if (!valid) {
        return;
    }

    emit("submit", { ...machine.value });
    close();
}

function close() {
    clear();
    dialog.value = false;
}

function clear() {
    // must be called after emmiting event
    form.value.reset();
}
</script>

<style scoped></style>
