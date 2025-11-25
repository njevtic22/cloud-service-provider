<template>
    <v-form ref="form">
        <v-row>
            <v-col cols="12" :sm="nameSize">
                <v-text-field
                    v-model="machine.name"
                    :rules="[required]"
                    label="Name"
                ></v-text-field>
            </v-col>
            <v-col v-if="authStore.isSuperAdmin" cols="12" sm="6">
                <fetching-autocomplete
                    v-model="machine.organizationId"
                    :items="orgStore.organizations"
                    :rules="[required]"
                    :fetch="orgStore.fetchOrganizations"
                    :append="orgStore.appendOrganizations"
                    :reset="() => orgStore.$reset()"
                    compare-property="name"
                    label="Organization"
                    item-title="name"
                    item-value="id"
                    ref="orgRef"
                ></fetching-autocomplete>
            </v-col>
        </v-row>
        <v-row>
            <v-col>
                <fetching-autocomplete
                    v-model="catComputed"
                    :items="catStore.categories"
                    :rules="[required]"
                    :fetch="catStore.fetchAll"
                    :append="catStore.appendAll"
                    :reset="() => catStore.$reset()"
                    :return-object="true"
                    compare-property="name"
                    label="Category"
                    item-title="name"
                ></fetching-autocomplete>
            </v-col>
        </v-row>
    </v-form>
    <v-row>
        <v-col cols="6" sm="4">
            <v-text-field
                v-model="cpu"
                variant="outlined"
                label="CPU Cores"
                readonly
            ></v-text-field>
        </v-col>
        <v-col cols="6" sm="4">
            <v-text-field
                v-model="ram"
                variant="outlined"
                label="RAM Capacity"
                readonly
            ></v-text-field>
        </v-col>
        <v-col cols="12" sm="4">
            <v-text-field
                v-model="gpu"
                variant="outlined"
                label="GPU Cores"
                readonly
            ></v-text-field>
        </v-col>
    </v-row>
</template>

<script setup>
import { computed, ref } from "vue";
import { useOrganizationStore } from "@/stores/organization";
import { useCategoryStore } from "@/stores/category";
import { useAuthStore } from "@/stores/auth";

const form = ref(null);
const machine = defineModel();

const orgStore = useOrganizationStore();
const catStore = useCategoryStore();
const authStore = useAuthStore();
const nameSize = computed(() => (authStore.isSuperAdmin ? "6" : "12"));

const required = (value) => Boolean(value) || "Required";

const category = ref(null);

const catComputed = computed({
    get() {
        return category.value;
    },

    set(newCategory) {
        category.value = newCategory;
        machine.value.categoryId = category.value?.id;
    },
});

const cpu = getCatProp("cpu");
const ram = getCatProp("ram");
const gpu = getCatProp("gpu");

function getCatProp(property) {
    return computed(() => category.value?.[property]);
}

defineExpose({
    isValid: computed(() => form.value.isValid),
    validate: () => form.value.validate(),
    reset: () => form.value.reset(),
    resetValidation: () => form.value.resetValidation(),
});
</script>

<style scoped></style>
