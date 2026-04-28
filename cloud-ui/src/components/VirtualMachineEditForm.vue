<template>
    <v-form ref="form">
        <v-row>
            <v-col cols="12">
                <v-text-field
                    v-model="machine.name"
                    :rules="[required]"
                    label="Name"
                ></v-text-field>
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
import { useCategoryStore } from "@/stores/category";

const form = ref(null);
const machine = defineModel();

const catStore = useCategoryStore();
const category = ref(null);

const required = (value) => Boolean(value) || "Required";

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

function init() {
    category.value = {
        // id: machineToEdit.categoryId,
        name: machine.value.category,
        cpu: machine.value.cpu,
        ram: machine.value.ram,
        gpu: machine.value.gpu,
    };
}

function resetForm() {
    form.value.reset();
    category.value = null;
    machine.value = {
        id: null,
        name: "",
        category: null,
        categoryId: null,
    };
}

defineExpose({
    init,
    isValid: computed(() => form.value.isValid),
    validate: () => form.value.validate(),
    resetForm,
});
</script>

<style scoped></style>
