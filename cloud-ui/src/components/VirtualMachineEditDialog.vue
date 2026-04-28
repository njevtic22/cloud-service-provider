<template>
    <the-dialog v-model="dialog" @afterLeave="clear">
        <the-dialog-card
            icon="mdi-pencil"
            title="Edit Virtual Machine"
            submit-text="Save"
            :submit-disabled="!form?.isValid"
            @submit="submit"
            @cancel="close"
        >
            <virtual-machine-edit-form
                v-model="machine"
                ref="form"
            ></virtual-machine-edit-form>
        </the-dialog-card>
    </the-dialog>
</template>

<script setup>
import { ref, inject } from "vue";
import { useMachineStore } from "@/stores/machine";

const emit = defineEmits(["submit"]);
const snackbar = inject("snackbar");
const store = useMachineStore();

const dialog = defineModel({ default: false });
const form = ref(null);

const machine = ref({
    id: null,
    name: "",
    category: null,
});

function open(editedMachine) {
    dialog.value = true;
    machine.value = { ...editedMachine };

    setTimeout(() => {
        form.value.init();
    }, 0.1);
}

async function submit() {
    const { valid } = await form.value.validate();
    if (!valid) {
        return;
    }

    const changes = {
        id: machine.value.id,
        name: machine.value.name,
        categoryId: machine.value.categoryId,
    };

    store.update(changes, () => {
        snackbar("Machine updated", 3 * 1000);
        emit("submit");
        close();
    });
}

function close() {
    clear();
    dialog.value = false;
}

function clear() {
    form.value.resetForm();
}

defineExpose({
    open,
});
</script>

<style scoped></style>
