<template>
    <v-form ref="form">
        <v-row>
            <v-col>
                <v-text-field
                    v-model="machine.name"
                    :rules="[required]"
                    label="Name"
                ></v-text-field>
            </v-col>
            <v-col>
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
                <!-- <v-text-field
                    v-model="machine.organizationId"
                    :rules="[required]"
                    label="Organization"
                ></v-text-field> -->
            </v-col>
        </v-row>
        <v-row>
            <v-col>
                <v-text-field
                    v-model="machine.categoryId"
                    :rules="[required]"
                    label="Category"
                ></v-text-field>
            </v-col>
        </v-row>
        <v-row>
            <v-col cols="12" sm="4">
                <v-text-field
                    v-model="machine.cpu"
                    variant="outlined"
                    label="CPU Cores"
                    readonly
                ></v-text-field>
            </v-col>
            <v-col cols="12" sm="4">
                <v-text-field
                    v-model="machine.ram"
                    variant="outlined"
                    label="RAM Capacity"
                    readonly
                ></v-text-field>
            </v-col>
            <v-col cols="12" sm="4">
                <v-text-field
                    v-model="machine.gpu"
                    variant="outlined"
                    label="GPU Cores"
                    readonly
                ></v-text-field>
            </v-col>
        </v-row>
    </v-form>
</template>

<script setup>
import { computed, ref } from "vue";
import { useOrganizationStore } from "@/stores/organization";

const form = ref(null);
const machine = defineModel();
const orgStore = useOrganizationStore();

const category = ref({
    id: 0,
    cpu: "",
    ram: "",
    gpu: "",
});

const org = ref({
    name: "",
});

const required = (value) => Boolean(value) || "Required";

defineExpose({
    isValid: computed(() => form.value.isValid),
    validate: () => form.value.validate(),
    reset: () => form.value.reset(),
    resetValidation: () => form.value.resetValidation(),
});
</script>

<style scoped></style>
