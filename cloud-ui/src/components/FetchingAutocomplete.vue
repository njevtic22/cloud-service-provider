<template>
    <paginated-autocomplete
        v-model="selected"
        :items="items.data"
        :loading="loading"
        :item-title="itemTitle"
        :item-value="itemValue"
        :return-object="returnObject"
        :label="label"
        @end-reached="loadMoreItems"
        @update:search-input="handleFilter"
    ></paginated-autocomplete>
</template>

<script setup>
import { ref, onUnmounted } from "vue";

const props = defineProps({
    label: {
        type: String,
        required: false,
    },
    items: {
        type: Object,
        required: true,
    },
    "item-title": {
        type: String,
        default: "title",
    },
    "item-value": {
        type: String,
        default: "value",
    },
    "return-object": {
        type: Boolean,
        default: false,
    },
    "compare-property": {
        type: String,
        default: "value",
    },
    fetch: {
        type: Function,
        required: true,
    },
    append: {
        type: Function,
        required: true,
    },
    reset: {
        type: Function,
        default: () => {},
    },
});

const selected = defineModel();
const loading = ref(true);

let page = 0;
const size = 20;
const sort = [{ key: "id", order: "asc" }];
const filter = {};

function filterItems() {
    loading.value = true;
    props.fetch(page, size, sort, filter);
    loading.value = false;
}

function loadItems() {
    loading.value = true;
    props.append(page, size, sort, filter);
    loading.value = false;
}
loadItems();

function loadMoreItems() {
    if (page >= props.items.totalPages) {
        // no more data to fetch
        return;
    }

    page++;
    loadItems();
}

function handleFilter(filterValue) {
    const compare = selected.value?.[props.compareProperty];
    if (compare === filterValue) {
        return;
    }

    filter[props.compareProperty] = filterValue;
    page = 0;
    filterItems();
}

onUnmounted(() => {
    props.reset();
});
</script>

<style scoped></style>
