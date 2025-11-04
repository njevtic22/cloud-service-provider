<template>
    <paginated-autocomplete
        v-model="selected"
        :items="items.data"
        :loading="loading"
        :item-title="itemTitle"
        :item-value="itemValue"
        :return-object="returnObject"
        :label="label"
        :rules="rules"
        @end-reached="loadMoreItems"
        @update:search-input="handleFilter"
        ref="input"
    ></paginated-autocomplete>
</template>

<script setup>
import { ref, onUnmounted, computed } from "vue";

const props = defineProps({
    rules: {
        type: Array,
        required: false,
    },
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

const input = ref(null);

async function validate() {
    return await input.value.validate();
}

async function reset() {
    await input.value.reset();
}

async function resetValidation() {
    await input.value.resetValidation();
}

const isValid = computed(() => {
    return input.value.isValid;
});

defineExpose({
    isValid,
    validate,
    reset,
    resetValidation,
});

onUnmounted(() => {
    props.reset();
});
</script>

<style scoped></style>
