<template>
    <the-dialog v-model="dialog">
        <the-dialog-card
            :actions-disabled="true"
            icon="mdi-domain-plus"
            title="Add organization"
            submit-text="Add"
        >
            <organization-add-form
                @submit-data="submitData"
                @submit-image="submitImage"
                @cancel="cancel"
            ></organization-add-form>
        </the-dialog-card>
    </the-dialog>
</template>

<script setup>
import { inject } from "vue";

const emit = defineEmits(["submit"]);

const snackbar = inject("snackbar");
const dialog = defineModel();

function submitData(data) {
    console.log(data);

    // emit("submit");
    // snackbar("Organization added", 3000);
}

function submitImage(image) {
    console.log(image);

    dialog.value = false;
    // emit("submit");
    // snackbar("Organization added", 3000);
}

function cancel(reload) {
    console.log("reload: " + reload);

    dialog.value = false;
    // clear form
}
</script>

<style scoped></style>
