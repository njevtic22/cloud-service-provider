<template>
    <the-dialog v-model="dialog" persistent>
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
                ref="orgAdder"
            ></organization-add-form>
        </the-dialog-card>
    </the-dialog>
</template>

<script setup>
import { ref, inject } from "vue";
import { useOrganizationStore } from "@/stores/organization.js";

const emit = defineEmits(["submit"]);

const snackbar = inject("snackbar");
const dialog = defineModel();
const orgStore = useOrganizationStore();

const orgAdder = ref(null);

let orgId = null;

function submitData(data) {
    orgStore.add(data, (response) => {
        orgAdder.value.secondStep();

        const uploadedUrl = response.headers.location;
        orgId = uploadedUrl.split("/").at(-1);
    });
}

function submitImage(image) {
    if (!orgId) {
        snackbar("Organization data is not added", -1, "red-darken-1");
    }

    orgStore.uploadImage(image, orgId, () => {
        dialog.value = false;
        emit("submit");
    });
}

// Perhaps 'close' fits better in this context
function cancel(reload) {
    if (reload) {
        emit("submit");
        snackbar("Organization added", 3000);
    }

    dialog.value = false;
    // clear form
}
</script>

<style scoped></style>
