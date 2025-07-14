<template>
    <v-data-table-server
        v-model:items-per-page="size"
        :items="modofiedOrgs"
        :items-length="store.organizations.totalElements"
        :items-per-page-options="sizeOptions"
        :headers="headers"
        :sort-by="sortBy"
        @update:options="console.log('update options')"
        class="elevation-4"
        multi-sort
    >
        <template v-slot:item.logo="{ item }">
            <div class="padded-2">
                <v-img :src="item.logo" height="100" />
            </div>
        </template>
    </v-data-table-server>
</template>

<script setup>
import { ref, computed } from "vue";
import { useOrganizationStore } from "@/stores/organization.js";

const store = useOrganizationStore();

const headers = [
    // {
    //     title: "ID",
    //     key: "id",
    // },
    {
        title: "Name",
        key: "name",
    },
    // {
    //     title: "Description",
    //     key: "description",
    // },
    {
        title: "Logo",
        key: "logo",
        align: "center",
    },
];

let page = 0;
const size = ref(20);
const sortBy = ref([]);
let filterData = {};

const sizeOptions = [
    { value: 5, title: "5" },
    { value: 10, title: "10" },
    { value: 20, title: "20" },
    { value: 2 ** 31 - 1, title: "$vuetify.dataFooter.itemsPerPageAll" },
];

store.fetchOrganizations(page, size.value, sortBy.value, filterData);

const modofiedOrgs = computed(() => {
    return store.organizations.data.map((org) => {
        const result = `data:image/${org.logo.type};base64,${org.logo.content}`;
        return { ...org, logo: result };
    });
});
</script>

<style scoped></style>
