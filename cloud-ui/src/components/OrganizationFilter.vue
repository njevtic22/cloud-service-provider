<template>
    <v-form ref="form">
        <v-row class="d-flex justify-center">
            <v-col cols="12" sm="6" md="4">
                <v-text-field
                    v-model="filter.name"
                    label="Search name"
                    hide-details
                    clearable
                >
                </v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="4">
                <v-text-field
                    v-model="filter.description"
                    label="Search description"
                    hide-details
                    clearable
                >
                </v-text-field>
            </v-col>
        </v-row>
        <v-row class="py-2 d-flex justify-center">
            <v-btn @click="emitFilter" color="primary" class="mx-2">
                Apply
            </v-btn>
            <v-btn
                @click="clear"
                color="primary"
                variant="outlined"
                class="mx-2"
            >
                Clear
            </v-btn>
        </v-row>
    </v-form>
</template>

<script setup>
import { ref } from "vue";

const emit = defineEmits(["filter"]);

const form = ref(null);
const filter = ref({
    name: "",
    description: "",
});

function emitFilter() {
    emit("filter", filter.value);
}

function clear() {
    form.value.reset();
    emitFilter();
}
</script>

<style scoped></style>
