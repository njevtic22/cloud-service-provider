<template>
    <v-data-table-server
        v-model:items-per-page="size"
        :items="store.machines.data"
        :items-length="store.machines.totalElements"
        :items-per-page-options="sizeOptions"
        :headers="filteredHeaders"
        :sort-by="sortBy"
        @update:options="updateOptions"
        class="elevation-4"
    >
        <template #item.active="{ item }">
            {{ item.active ? "Active" : "Inactive" }}
        </template>
    </v-data-table-server>
</template>

<script setup>
import { computed, ref } from "vue";
import { useMachineStore } from "@/stores/machine.js";
import { useAuthStore } from "@/stores/auth.js";

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
        title: "Organization",
        key: "organization",
        show: () => authStore.isSuperAdmin,
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

    loadMachines();
}

function loadMachines() {
    store.fetchMachines(page, size.value, []);
}
</script>

<style scoped></style>
