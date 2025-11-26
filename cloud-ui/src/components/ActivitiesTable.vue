<template>
    <v-data-table-server
        v-model:items-per-page="size"
        :items="store.activities.data"
        :items-length="store.activities.totalElements"
        :items-per-page-options="sizeOptions"
        :headers="headers"
        :sort-by="sortBy"
        @update:options="updateOptions"
        class="elevation-4"
        multi-sort
        striped
    >
        <template #item.turnedOn="{ item }">
            {{ formatLocale(item.turnedOn, "sr-RS") }}
        </template>

        <template #item.turnedOff="{ item }">
            {{ item.turnedOff ? formatLocale(item.turnedOff, "sr-RS") : "/" }}
        </template>
    </v-data-table-server>
</template>

<script setup>
import { ref } from "vue";
import { useActivitiesStore } from "@/stores/activity.js";
import { formatLocale } from "@/util/date.js";

const props = defineProps({
    constantFilter: {
        type: Object,
        required: false,
    },
    initialSize: {
        type: Number,
        default: 20,
    },
    initialSort: {
        type: Array,
        default: [{ key: "id", order: "asc" }],
    },
});

const store = useActivitiesStore();

const headers = [
    // {
    //     title: "ID",
    //     key: "id",
    // },
    {
        title: "Turned On",
        key: "turnedOn",
    },
    {
        title: "Turned Off",
        key: "turnedOff",
    },
    {
        title: "Profit",
        key: "profit",
        align: "end",
    },
];

let page = 0;
const size = ref(props.initialSize);
const sortBy = ref(props.initialSort);
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

    loadActivities();
}

function loadActivities() {
    filterData = { ...filterData, ...props.constantFilter };
    store.fetchAll(page, size.value, sortBy.value, filterData);
}

defineExpose({
    loadActivities,
});
</script>

<style scoped></style>
