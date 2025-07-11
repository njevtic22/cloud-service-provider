<template>
    <div v-if="display.mdAndUp.value" class="text-right">
        <v-btn @click="addDialog = true" color="primary" class="mb-2">
            Add user
        </v-btn>
    </div>
    <hiding-button
        v-else
        @click="addDialog = true"
        :frozen="addDialog"
        icon="mdi-account-plus"
    ></hiding-button>

    <user-add-dialog v-model="addDialog" @submit="loadUsers"></user-add-dialog>
    <user-edit-dialog @submit="loadUsers" ref="editRef"></user-edit-dialog>
    <confirmation-dialog ref="confirm"></confirmation-dialog>

    <v-data-table-server
        v-model:items-per-page="size"
        :items="store.users.data"
        :items-length="store.users.totalElements"
        :items-per-page-options="sizeOptions"
        :headers="filteredHeaders"
        :sort-by="sortBy"
        @update:options="updateOptions"
        item-value="id"
        class="elevation-4"
        multi-sort
    >
        <!-- sorting works well with both 'role.name' and 'roleName' as key -->
        <!-- TODO: see how to convert sort key 'role' to 'role.name'/'roleName' in backend so frontend is not affected -->
        <template v-slot:item.roleName="{ item }">
            {{ item.role }}
        </template>
        <template v-slot:item.organizationName="{ item }">
            {{ item.organizationName ? item.organizationName : "/" }}
        </template>

        <template v-slot:item.actions="{ item }">
            <v-btn
                @click="editRef.open({ ...item })"
                icon="mdi-pencil"
                variant="flat"
                size="small"
            >
            </v-btn>
            <v-btn
                @click="openConfirmDialog(item)"
                icon="mdi-delete"
                variant="flat"
                size="small"
                class="text-red"
            ></v-btn>
        </template>

        <template v-slot:footer.prepend>
            <v-expansion-panels static elevation="0" variant="accordion">
                <v-expansion-panel title="Filter users">
                    <v-expansion-panel-text>
                        <users-filter
                            class="mx-n2"
                            @filter="filter"
                        ></users-filter>
                    </v-expansion-panel-text>
                </v-expansion-panel>
            </v-expansion-panels>
        </template>
    </v-data-table-server>
</template>

<script setup>
import { ref, computed } from "vue";
import { useDisplay } from "vuetify";
import { useUserStore } from "@/stores/user.js";
import { useAuthStore } from "@/stores/auth.js";

const display = useDisplay();
const store = useUserStore();
const authstore = useAuthStore();
const addDialog = ref(false);
const editRef = ref(null);
const confirm = ref(null);

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
        key: "roleName",
    },
    {
        title: "Organization",
        key: "organizationName",
        show: authstore.isSuperAdmin,
    },
    {
        title: "Actions",
        key: "actions",
        sortable: false,
        align: "end",
    },
];

const filteredHeaders = computed(() => {
    return headers.filter((h) => (h.show == undefined ? true : h.show));
});

const page = ref(0);
const size = ref(20);
const sortBy = ref([]);
let filterData = {};

const sizeOptions = [
    { value: 5, title: "5" },
    { value: 10, title: "10" },
    { value: 20, title: "20" },
    { value: 2 ** 31 - 1, title: "$vuetify.dataFooter.itemsPerPageAll" },
];

function filter(newFilter) {
    filterData = newFilter;
    loadUsers();
}

function updateOptions(options) {
    page.value = options.page - 1;
    size.value = options.itemsPerPage;
    sortBy.value = options.sortBy;

    loadUsers();
}

function loadUsers() {
    store.fetchUsers(page.value, size.value, sortBy.value, filterData);
}

async function openConfirmDialog(userToDelete) {
    const confirmed = await confirm.value.open(
        "Are you sure you want to permanently delete user with full name: " +
            userToDelete.name +
            " " +
            userToDelete.surname +
            "?",
        "Delete user"
    );

    console.log(confirmed);
}
</script>

<style scoped></style>
