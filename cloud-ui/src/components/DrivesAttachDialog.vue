<template>
    <the-dialog v-model="dialog" :fullscreen="false">
        <the-dialog-card
            icon="mdi-pencil"
            title="Attach Drives"
            submit-text="Attach Selected"
            @submit="submit"
            @cancel="cancel"
        >
            <the-selectable-data-table-server
                v-model:items-per-page="size"
                v-model="driveIdsToAttach"
                :items="store.detachedDrives.data"
                :items-length="store.detachedDrives.totalElements"
                :items-per-page-options="sizeOptions"
                :headers="headers"
                :sort-by="sortBy"
                :item-ids="driveIds"
                @update:options="updateOptions"
                class="elevation-4"
                multi-sort
            ></the-selectable-data-table-server>
        </the-dialog-card>
    </the-dialog>
</template>

<script setup>
import { ref, watch } from "vue";
import { useDrivesStore } from "@/stores/drive.js";

const props = defineProps({
    organizationId: {
        type: Number,
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

const driveIds = ref([]);
const driveIdsToAttach = ref([]);

function updateOptions(options) {
    page = options.page - 1;
    size.value = options.itemsPerPage;
    sortBy.value = options.sortBy;

    loadDrives();
}

function loadDrives() {
    if (!props.organizationId) {
        return;
    }

    const filter = { organizationId: props.organizationId };
    store.fetchAllDetached(page, size.value, sortBy.value, filter);
}

function loadDriveIds() {
    if (!props.organizationId) {
        return;
    }

    const filter = { organizationId: props.organizationId, attached: "false" };
    store.fethcAllIds(filter, (response) => (driveIds.value = response.data));
}

watch(
    () => dialog.value,
    (isOpened) => {
        if (isOpened) {
            loadDriveIds();
        }
    },
);

function cancel() {
    dialog.value = false;
}

function submit() {
    const toAttach = [...driveIdsToAttach.value];
    console.log(toAttach);
    dialog.value = false;
}
</script>

<style scoped></style>
