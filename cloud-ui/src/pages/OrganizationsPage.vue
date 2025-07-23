<template>
    <div v-if="display.mdAndUp.value" class="text-right">
        <v-btn @click="addDialog = true" color="primary" class="mb-2">
            Add organization
        </v-btn>
    </div>
    <hiding-button
        v-else
        @click="addDialog = true"
        :frozen="addDialog"
        icon="mdi-domain-plus"
    >
    </hiding-button>

    <organization-add-dialog v-model="addDialog"></organization-add-dialog>

    <v-data-table-server
        v-model:items-per-page="size"
        :items="modofiedOrgs"
        :items-length="store.organizations.totalElements"
        :items-per-page-options="sizeOptions"
        :headers="headers"
        :sort-by="sortBy"
        @update:options="updateOptions"
        class="elevation-4"
        multi-sort
        show-expand
    >
        <template v-slot:item.logo="{ item }">
            <div class="padded-2">
                <img :src="item.logo" height="75" />
            </div>
        </template>

        <template v-slot:expanded-row="{ columns, item }">
            <tr class="details">
                <td :colspan="columns.length" class="pa-4">
                    {{ item.description }}
                </td>
            </tr>
        </template>

        <template v-slot:footer.prepend>
            <v-expansion-panels static elevation="0" variant="accordion">
                <v-expansion-panel title="Filter organizations">
                    <v-expansion-panel-text>
                        <organization-filter
                            @filter="filter"
                        ></organization-filter>
                    </v-expansion-panel-text>
                </v-expansion-panel>
            </v-expansion-panels>
        </template>
    </v-data-table-server>
</template>

<script setup>
import { ref, computed } from "vue";
import { useDisplay } from "vuetify";
import { useOrganizationStore } from "@/stores/organization.js";

const display = useDisplay();
const store = useOrganizationStore();

const addDialog = ref(false);

const modofiedOrgs = computed(() => {
    return store.organizations.data.map((org) => {
        const result = `data:image/${org.logo.type};base64,${org.logo.content}`;
        return { ...org, logo: result };
    });
});

const headers = [
    {
        key: "data-table-expand",
    },
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
        sortable: false,
    },
];

let page = 0;
const size = ref(5);
const sortBy = ref([]);
let filterData = {};

const sizeOptions = [
    { value: 2, title: "2" },
    { value: 5, title: "5" },
    { value: 10, title: "10" },
    { value: 2 ** 31 - 1, title: "$vuetify.dataFooter.itemsPerPageAll" },
];

function filter(newFilter) {
    filterData = newFilter;
    loadOrgs();
}

function updateOptions(options) {
    page = options.page - 1;
    size.value = options.itemsPerPage;
    sortBy.value = options.sortBy;

    loadOrgs();
}

function loadOrgs() {
    store.fetchOrganizations(page, size.value, sortBy.value, filterData);
}
</script>

<style scoped>
.details {
    box-shadow: inset 0 4px 10px rgba(0, 0, 0, 0.1),
        inset 0 -4px 10px rgba(0, 0, 0, 0.1);
}
</style>
