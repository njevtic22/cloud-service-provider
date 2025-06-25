<template>
    <v-data-table-server
        :items="store.users.data"
        :items-length="store.users.totalElements"
        :headers="headers"
        item-value="id"
        class="elevation-4"
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
store.fetchUsers();

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

const size = ref(5);
</script>

<style scoped></style>
