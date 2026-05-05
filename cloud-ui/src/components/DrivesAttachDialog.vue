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
            <v-btn
                @click="console.log(selectedStatus)"
                color="primary"
                variant="elevated"
            >
                check
            </v-btn>
        </the-dialog-card>
    </the-dialog>
</template>

<script setup>
import { ref, onMounted } from "vue";
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

const selectedStatus = ref({});

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

function loadStatuses() {
    if (!props.organizationId) {
        return;
    }

    const filter = { organizationId: props.organizationId, attached: "false" };
    const fillStatuses = (response) => {
        for (const id of response.data) {
            selectedStatus.value[id] = false;
        }
    };
    store.fethcAllIds(filter, fillStatuses);
}
onMounted(() => {
    // TODO: Detect when dialog is opened
    setTimeout(() => {
        loadStatuses();
    }, 1000);
});

function cancel() {
    dialog.value = false;
}

function submit() {
    console.log("Manage drives submit clicked");
    dialog.value = false;
}
</script>

<style scoped></style>
