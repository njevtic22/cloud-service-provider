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

function loadOrganizations() {
    loading.value = true;
    store.appendOrganizations(page, size, sort, filter);
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
    loadOrganizations();
}

onUnmounted(() => {
    store.$reset();
});
</script>

<style></style>
