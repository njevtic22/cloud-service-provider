<template>
    <v-data-table-server
        v-model:items-per-page="itemsPerPage"
        v-model="selected"
        :items="selectableItems"
        :items-length="itemsLength"
        :items-per-page-options="itemsPerPageOptions"
        :headers="headers"
        :sort-by="sortBy"
        :multi-sort="multiSort"
        :class="class"
        :striped="striped"
        :show-select="showSelect"
        @update:options="updateOptions"
    >
        <!-- header checkbox slot -->
        <template #header.data-table-select>
            <v-checkbox-btn
                v-model="selectedAll"
                :indeterminate="currentAllState === SELECTED_ALL_STATE.MIXED"
                @update:model-value="(isSelected) => toggleAll(isSelected)"
            />
        </template>

        <!-- row checkbox slot -->
        <template #item.data-table-select="{ item }">
            <v-checkbox-btn
                v-model="selectedIdStatuses[item[idProperty]]"
                @update:model-value="
                    (isSelected) => toggleSelection(item, isSelected)
                "
            />
        </template>
    </v-data-table-server>
</template>

<script setup>
import { ref, computed, watch } from "vue";

const props = defineProps({
    items: {
        type: Array,
        required: true,
    },
    itemsLength: {
        type: Number,
        required: true,
    },
    itemsPerPageOptions: {
        type: Array,
        required: true,
    },
    headers: {
        type: Array,
        required: true,
    },
    sortBy: {
        type: Array,
        required: true,
    },
    multiSort: {
        type: Boolean,
        default: false,
    },
    striped: {
        type: String,
        default: "odd",
    },
    showSelect: {
        type: Boolean,
        default: true,
    },
    class: {
        type: String,
        default: "",
    },
    idProperty: {
        type: String,
        default: "id",
    },
    itemIds: {
        type: Array,
        required: true,
    },
});

const emit = defineEmits(["update:options"]);

const selected = defineModel();
const itemsPerPage = defineModel("items-per-page");

const selectedAll = ref(false);

const selectedIdStatuses = ref({});

const SELECTED_ALL_STATE = Object.freeze({
    ALL: "ALL",
    MIXED: "MIXED",
    NONE: "NONE",
});

const currentAllState = computed(() => {
    let hasSelected = false;
    let hasDeselected = false;

    for (const id in selectedIdStatuses.value) {
        const isSelected = selectedIdStatuses.value[id];

        if (isSelected) {
            hasSelected = true;
        } else {
            hasDeselected = true;
        }

        if (hasSelected && hasDeselected) {
            return SELECTED_ALL_STATE.MIXED;
        }
    }

    return hasSelected ? SELECTED_ALL_STATE.ALL : SELECTED_ALL_STATE.NONE;
});

watch(
    () => props.itemIds,
    (newIds) => {
        // Doesn't work when page is quick refreshed when saving file changes
        // therefore page refresh is required after saved file changes
        for (const id of newIds) {
            selectedIdStatuses.value[id] = false;
        }
    },
);

const selectableItems = computed(() => {
    return props.items.map((item) => {
        const itemId = item[props.idProperty];
        return {
            ...item,
            selected: Boolean(selectedIdStatuses.value[itemId]),
        };
    });
});

function toggleAll(isSelected) {
    selectedAll.value = isSelected;
    for (const id of Object.keys(selectedIdStatuses.value)) {
        selectedIdStatuses.value[id] = isSelected;
    }

    if (isSelected) {
        selected.value = Object.keys(selectedIdStatuses.value).map((id) =>
            Number(id),
        );
    } else {
        selected.value = [];
    }
}

function toggleSelection(item, isSelected) {
    selectedIdStatuses.value[item[props.idProperty]] = isSelected;
    selectedAll.value = currentAllState === SELECTED_ALL_STATE.ALL;

    selected.value = Object.keys(selectedIdStatuses.value)
        .filter((id) => selectedIdStatuses.value[id])
        .map((id) => Number(id));
}

function updateOptions(options) {
    emit("update:options", options);
}
</script>

<style scoped></style>
