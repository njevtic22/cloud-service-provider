<template>
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
        <template v-slot:item.organizationName="{ item }">
            {{ item.organizationName ? item.organizationName : "/" }}
        </template>
    </v-data-table-server>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "@/stores/user.js";

const store = useUserStore();

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
        key: "role",
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

const sizeOptions = [
    { value: 5, title: "5" },
    { value: 10, title: "10" },
    { value: 20, title: "20" },
    { value: 2 ** 31 - 1, title: "$vuetify.dataFooter.itemsPerPageAll" },
];

function updateOptions(options) {
    page.value = options.page - 1;
    size.value = options.itemsPerPage;
    // sortBy.value = options.sortBy;

    // TODO: fix sending request after logout
    // TODO: different users for admin and superadmin
    loadUsers();
}

function loadUsers() {
    store.fetchUsers(page.value, size.value);
}
</script>

<style scoped></style>
