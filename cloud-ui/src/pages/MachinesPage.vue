<template>
    <div v-if="!authStore.isUser">
        <div v-if="display.mdAndUp.value" class="text-right">
            <v-btn @click="addDialog = true" color="primary" class="mb-2">
                Add machine
            </v-btn>
        </div>
        <hiding-button
            v-else
            @click="addDialog = true"
            :frozen="addDialog || deleteDialog"
            icon="mdi-server-plus"
        ></hiding-button>
    </div>

    <virtual-machine-add-dialog
        v-model="addDialog"
        @submit="loadMachines"
    ></virtual-machine-add-dialog>
    <confirmation-dialog ref="confirm"></confirmation-dialog>

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
        hover
    >
        <template #item.active="{ item }">
            {{ item.active ? "Active" : "Inactive" }}
        </template>

        <template #item.actions="{ item }">
            <v-btn
                v-if="!item.active"
                @click="openConfirmDialog($event, item)"
                icon="mdi-delete"
                variant="flat"
                size="small"
                class="text-red"
            ></v-btn>
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
import { inject, ref } from "vue";
import { useRouter } from "vue-router";
import { useDisplay } from "vuetify";
import { useMachineStore } from "@/stores/machine.js";
import { useAuthStore } from "@/stores/auth.js";
import { filterShowHeaders } from "@/util/table-util";

const router = useRouter();
const display = useDisplay();

const snackbar = inject("snackbar");
const store = useMachineStore();
const authStore = useAuthStore();

const addDialog = ref(false);
const confirm = ref(null);

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
        title: "Organization",
        key: "organization",
        show: () => authStore.isSuperAdmin,
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
        title: "Active",
        key: "active",
    },
    {
        title: "Actions",
        key: "actions",
        sortable: false,
        align: "end",
        show: () => !authStore.isUser,
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

const deleteDialog = ref(false);
async function openConfirmDialog(event, machineToDelete) {
    deleteDialog.value = true;
    event.stopPropagation();

    const confirmed = await confirm.value.open(
        "Are you sure you want to permanently delete virtual machine with name: " +
            machineToDelete.name +
            "?",
        "Delete Virtual Machine"
    );
    deleteDialog.value = false;

    if (!confirmed) {
        return;
    }

    store.delete(machineToDelete.id, () => {
        snackbar("Machine deleted", 3000);
        loadMachines();
    });
}
</script>

<style scoped></style>
