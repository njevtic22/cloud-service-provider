<template>
    <the-dialog v-model="dialog" :fullscreen="false">
        <the-dialog-card
            icon="mdi-pencil"
            title="Attach Drives"
            submit-text="Attach Selected"
            @submit="submit"
            @cancel="cancel"
        >
            <v-data-table-server
                v-model:items-per-page="size"
                :items="selectableDrives"
                :items-length="store.detachedDrives.totalElements"
                :items-per-page-options="sizeOptions"
                :headers="headers"
                :sort-by="sortBy"
                @update:options="updateOptions"
                class="elevation-4"
                show-select
                multi-sort
                striped
            >
                <!-- row checkbox slot -->
                <template #item.data-table-select="{ item }">
                    <v-checkbox-btn
                        @update:model-value="
                            (isSelected) => toggleSelection(item, isSelected)
                        "
                    />
                </template>
            </v-data-table-server>
        </the-dialog-card>
    </the-dialog>
</template>

<script setup>
import { ref, computed, watch } from "vue";
import { useDrivesStore } from "@/stores/drive.js";

const props = defineProps({
    organizationId: {
        type: Number,
        required: false,
        default: null,
    },
});

const dialog = defineModel({ default: false });
const store = useDrivesStore();

const headers = [
    {
        title: "Name",
        key: "name",
    },
    {
        title: "Capacity (GB)",
        key: "capacity",
    },
    {
        title: "Type",
        key: "type",
        align: "end",
    },
];

const sizeOptions = [
    { value: 5, title: "5" },
    { value: 10, title: "10" },
    { value: 50, title: "50" },
    { value: 100, title: "100" },
    { value: 2 ** 31 - 1, title: "$vuetify.dataFooter.itemsPerPageAll" },
];

let page = 0;
const size = ref(10);
const sortBy = ref([]);

const selectedStatus = ref({});
const selectableDrives = computed(() => {
    return store.detachedDrives.data.map((drive) => {
        return {
            ...drive,
            selected: Boolean(selectedStatus.value[drive.id]),
        };
    });
});

function toggleSelection(drive, isSelected) {
    selectedStatus.value[drive.id] = isSelected;
}

function updateOptions(options) {
    page = options.page - 1;
    size.value = options.itemsPerPage;
    sortBy.value = options.sortBy;

    loadDrives();
}

function loadDrives() {
    if (!props.organizationId) {
        return;
    }

    const filter = { organizationId: props.organizationId };
    store.fetchAllDetached(page, size.value, sortBy.value, filter);
}

function loadStatuses() {
    if (!props.organizationId) {
        return;
    }

    const filter = { organizationId: props.organizationId, attached: "false" };
    const fillStatuses = (response) => {
        for (const id of response.data) {
            selectedStatus.value[id] = false;
        }
    };
    store.fethcAllIds(filter, fillStatuses);
}

watch(
    () => dialog.value,
    (isOpened) => {
        if (isOpened) {
            loadStatuses();
        } else {
            selectedStatus.value = {};
        }
    },
);

function cancel() {
    dialog.value = false;
}

function submit() {
    const idsToAttach = Object.keys(selectedStatus.value)
        .filter((id) => selectedStatus.value[id])
        .map((id) => Number(id));
    console.log(idsToAttach);
    dialog.value = false;
}
</script>

<style scoped></style>
