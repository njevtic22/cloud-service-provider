<template>
    <paginated-autocomplete
        v-model="organization"
        :items="store.organizations.data"
        :loading="loading"
        :return-object="true"
        @end-reached="loadMoreOrganizations"
        @update:search-input="handleFilter"
        label="Organization"
        item-title="name"
    ></paginated-autocomplete>
</template>

<script setup>
import { ref, onUnmounted } from "vue";
import { useOrganizationStore } from "@/stores/organization.js";

const organization = defineModel();
const loading = ref(false);

const store = useOrganizationStore();

let page = 0;
const size = 20;
const sort = [{ key: "id", order: "asc" }];
const filter = { name: "" };
let fetchMode = "append";

function loadOrganizations() {
    loading.value = true;
    store.fetchOrganizations(page, size, sort, filter, fetchMode);
    loading.value = false;
}
loadOrganizations();

function loadMoreOrganizations() {
    if (page >= store.organizations.totalPages) {
        // no more data to fetch
        return;
    }

    page++;
    loadOrganizations();
}

function handleFilter(filterName) {
    if (organization.value?.name === filterName) {
        return;
    }

    filter.name = filterName;
    page = 0;
    store.$reset();
    // fetchMode = "overwrite";
    loadOrganizations();
    // fetchMode = "append";
}

onUnmounted(() => {
    store.$reset();
});
</script>

<style></style>
