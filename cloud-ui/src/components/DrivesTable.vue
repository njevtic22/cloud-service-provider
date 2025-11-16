<template>
    <v-data-table-server
        v-model:items-per-page="size"
        :items="store.drives.data"
        :items-length="store.drives.totalElements"
        :items-per-page-options="sizeOptions"
        :headers="filteredHeaders"
        :sort-by="sortBy"
        @update:options="updateOptions"
        class="elevation-4"
        multi-sort
    ></v-data-table-server>
</template>

<script setup>
import { ref } from "vue";
import { useDrivesStore } from "@/stores/drive.js";
import { filterShowHeaders } from "@/util/table-util";

const props = defineProps({
    constantFilter: {
        type: Object,
        required: false,
    },
    showOrganization: {
        type: Boolean,
        default: false,
    },
    showMachine: {
        type: Boolean,
        default: false,
    },
});

const store = useDrivesStore();

const headers = [
    // {
    //     title: "ID",
    //     key: "id",
    // },
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
        align: props.showOrganization && props.showMachine ? "center" : "end",
    },
    {
        title: "Organization",
        key: "organization",
        show: () => props.showOrganization,
    },
    {
        title: "Machine",
        key: "machine",
        align: "end",
        show: () => props.showMachine,
    },
];

const filteredHeaders = filterShowHeaders(headers);

let page = 0;
const size = ref(20);
const sortBy = ref([]);
let filterData = {};

const sizeOptions = [
    { value: 5, title: "5" },
    { value: 10, title: "10" },
    { value: 50, title: "50" },
    { value: 100, title: "100" },
    { value: 2 ** 31 - 1, title: "$vuetify.dataFooter.itemsPerPageAll" },
];

function updateOptions(options) {
    page = options.page - 1;
    size.value = options.itemsPerPage;
    sortBy.value = options.sortBy;

    loadDrives();
}

function loadDrives() {
    filterData = { ...filterData, ...props.constantFilter };
    store.fetchAll(page, size.value, sortBy.value, filterData);
}
</script>

<style scoped></style>
