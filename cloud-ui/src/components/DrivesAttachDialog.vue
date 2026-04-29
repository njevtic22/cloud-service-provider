<template>
    <the-dialog v-model="dialog" :fullscreen="false">
        <the-dialog-card
            icon="mdi-pencil"
            title="Attach Drives"
            submit-text="Attach Selected"
            @submit="submit"
            @cancel="cancel"
        >
            <v-data-table-server
                v-model:items-per-page="size"
                :items="store.detachedDrives.data"
                :items-length="store.detachedDrives.totalElements"
                :items-per-page-options="sizeOptions"
                :headers="headers"
                :sort-by="sortBy"
                @update:options="updateOptions"
                class="elevation-4"
                multi-sort
                striped
            ></v-data-table-server>
        </the-dialog-card>
    </the-dialog>
</template>

<script setup>
import { ref } from "vue";
import { useDrivesStore } from "@/stores/drive.js";

const props = defineProps({
    organization: {
        type: String,
        required: false,
        default: null,
    },
});

const dialog = defineModel({ default: false });
const store = useDrivesStore();

const headers = [
    {
        title: "Name",
        key: "name",
    },
    {
        title: "Capacity (GB)",
        key: "capacity",
    },
    {
        title: "Type",
        key: "type",
        align: "end",
    },
];

const sizeOptions = [
    { value: 5, title: "5" },
    { value: 10, title: "10" },
    { value: 50, title: "50" },
    { value: 100, title: "100" },
    { value: 2 ** 31 - 1, title: "$vuetify.dataFooter.itemsPerPageAll" },
];

let page = 0;
const size = ref(10);
const sortBy = ref([]);

function updateOptions(options) {
    page = options.page - 1;
    size.value = options.itemsPerPage;
    sortBy.value = options.sortBy;

    loadDrives();
}

function loadDrives() {
    if (!props.organization) {
        return;
    }

    const filter = { organization: props.organization };
    store.fetchAllDetached(page, size.value, sortBy.value, filter);
}

function cancel() {
    dialog.value = false;
}

function submit() {
    console.log("Manage drives submit clicked");
    dialog.value = false;
}
</script>

<style scoped></style>
