<template>
    <v-data-table-server
        v-model:items-per-page="size"
        :items="store.machines.data"
        :items-length="store.machines.totalElements"
        :items-per-page-options="sizeOptions"
        :headers="filteredHeaders"
        :sort-by="sortBy"
        @update:options="updateOptions"
        @click:row="redirect"
        class="elevation-4"
        multi-sort
    >
        <template #item.active="{ item }">
            {{ item.active ? "Active" : "Inactive" }}
        </template>

        <template #footer.prepend>
            <v-expansion-panels static elevation="0" variant="accordion">
                <v-expansion-panel title="Filter virtual machines">
                    <v-expansion-panel-text>
                        <virtual-machine-filter
                            @filter="filter"
                        ></virtual-machine-filter>
                    </v-expansion-panel-text>
                </v-expansion-panel>
            </v-expansion-panels>
        </template>
    </v-data-table-server>
</template>

<script setup>
import { computed, ref } from "vue";
import { useRouter } from "vue-router";
import { useMachineStore } from "@/stores/machine.js";
import { useAuthStore } from "@/stores/auth.js";

const router = useRouter();
const store = useMachineStore();
const authStore = useAuthStore();

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
        title: "Category",
        key: "category",
    },
    {
        title: "Organization",
        key: "organization",
        show: () => authStore.isSuperAdmin,
    },
    {
        title: "CPU",
        key: "cpu",
    },
    {
        title: "RAM",
        key: "ram",
    },
    {
        title: "GPU",
        key: "gpu",
    },
    {
        title: "Active",
        key: "active",
        align: "end",
    },
];

const filteredHeaders = computed(() => {
    return headers.filter((h) => (h.show ? h.show() : true));
});

let page = 0;
const size = ref(5);
const sortBy = ref([]);
let filterData = {};

const sizeOptions = [
    { value: 5, title: "5" },
    { value: 10, title: "10" },
    { value: 50, title: "50" },
    { value: 100, title: "100" },
    { value: 2 ** 31 - 1, title: "$vuetify.dataFooter.itemsPerPageAll" },
];

function filter(newFilter) {
    filterData = newFilter;
    loadMachines();
}

function updateOptions(options) {
    page = options.page - 1;
    size.value = options.itemsPerPage;
    sortBy.value = options.sortBy;

    loadMachines();
}

function loadMachines() {
    store.fetchMachines(page, size.value, sortBy.value, filterData);
}

function redirect(event, clickedRow) {
    router.push("/virtual-machines/" + clickedRow.item.id);
}
</script>

<style scoped></style>
