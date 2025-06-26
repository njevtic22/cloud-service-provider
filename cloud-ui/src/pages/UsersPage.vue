<template>
    <div class="text-right">
        <v-btn @click="dialog = true" color="primary" class="mb-2">
            Add user
        </v-btn>
    </div>
    <user-dialog v-model="dialog"></user-dialog>

    <v-data-table-server
        v-model:items-per-page="size"
        :items="store.users.data"
        :items-length="store.users.totalElements"
        :items-per-page-options="sizeOptions"
        :headers="headers"
        :sort-by="sortBy"
        @update:options="updateOptions"
        item-value="id"
        class="elevation-4"
        multi-sort
    >
        <!-- sorting works well with both 'role.name' and 'roleName' as key -->
        <!-- TODO: see how to convert sort key 'role' to 'role.name'/'roleName' in backend so frontend is not affected -->
        <template v-slot:item.roleName="{ item }">
            {{ item.role }}
        </template>
        <template v-slot:item.organizationName="{ item }">
            {{ item.organizationName ? item.organizationName : "/" }}
        </template>

        <template v-slot:footer.prepend>
            <v-expansion-panels static elevation="0" variant="accordion">
                <v-expansion-panel title="Filter users">
                    <v-expansion-panel-text>
                        <users-filter
                            class="mx-n2"
                            @filter="filter"
                        ></users-filter>
                    </v-expansion-panel-text>
                </v-expansion-panel>
            </v-expansion-panels>
        </template>
    </v-data-table-server>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "@/stores/user.js";

const store = useUserStore();
const dialog = ref(false);

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
        title: "Surname",
        key: "surname",
    },
    {
        title: "Email",
        key: "email",
    },
    {
        title: "Username",
        key: "username",
    },
    {
        title: "Role",
        key: "roleName",
    },
    {
        title: "Organization",
        key: "organizationName",
        align: "end",
    },
];

const page = ref(0);
const size = ref(20);
const sortBy = ref([]);
let filterData = {};

const sizeOptions = [
    { value: 5, title: "5" },
    { value: 10, title: "10" },
    { value: 20, title: "20" },
    { value: 2 ** 31 - 1, title: "$vuetify.dataFooter.itemsPerPageAll" },
];

function filter(newFilter) {
    filterData = newFilter;
    loadUsers();
}

function updateOptions(options) {
    page.value = options.page - 1;
    size.value = options.itemsPerPage;
    sortBy.value = options.sortBy;

    // TODO: fix sending request after logout
    loadUsers();
}

function loadUsers() {
    store.fetchUsers(page.value, size.value, sortBy.value, filterData);
}
</script>

<style scoped></style>
