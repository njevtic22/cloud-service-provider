<template>
    <the-dialog v-model="dialog" @afterLeave="tab = 'Data'">
        <the-dialog-card
            icon="mdi-pencil"
            title="Edit organization"
            submit-text="Save"
            actions-disabled
        >
            <v-tabs v-model="tab" bg-color="primary" align-tabs="center" grow>
                <v-tab prepend-icon="mdi-pencil" value="Data"> Data</v-tab>
                <v-tab prepend-icon="mdi-image-edit" value="Logo"> Logo</v-tab>
            </v-tabs>

            <v-tabs-window v-model="tab" class="padded-3">
                <v-tabs-window-item value="Data">
                    <organization-edit-data
                        @submit="submit"
                        @cancel="close"
                        ref="editData"
                    ></organization-edit-data>
                </v-tabs-window-item>

                <v-tabs-window-item value="Logo" eager>
                    <organization-edit-logo
                        ref="editLogo"
                        @submit="submit"
                    ></organization-edit-logo>
                </v-tabs-window-item>
            </v-tabs-window>
        </the-dialog-card>
    </the-dialog>
</template>

<script setup>
import { ref } from "vue";

const emit = defineEmits(["submit"]);

const dialog = defineModel({ default: false });
const tab = ref("Data");

const editData = ref(null);
const editLogo = ref(null);

function open(org) {
    dialog.value = true;

    setTimeout(() => {
        editData.value.init({
            id: org.id,
            name: org.name,
            description: org.description,
        });
        editLogo.value.init({
            orgId: org.id,
            logo: org.logo,
        });
    }, 0.1);
}

function submit() {
    emit("submit");
    close();
}

function close() {
    dialog.value = false;
}

defineExpose({
    open,
});
</script>

<style scoped></style>
