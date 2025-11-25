<template>
    <v-form ref="form">
        <v-row>
            <v-col cols="12" sm="4" md="3">
                <v-text-field
                    v-model="filter.name"
                    placeholder="Search name"
                    density="compact"
                    hide-details
                    clearable
                ></v-text-field>
            </v-col>
            <v-col cols="12" sm="4" md="3">
                <v-text-field
                    v-model="filter.category"
                    placeholder="Search category"
                    density="compact"
                    hide-details
                    clearable
                ></v-text-field>
            </v-col>
            <v-col cols="12" sm="4" md="2">
                <v-text-field
                    v-model="filter['min-cpu']"
                    @keydown="validateDigit"
                    placeholder="Minimum cpu"
                    density="compact"
                    type="number"
                    hide-details
                    clearable
                ></v-text-field>
            </v-col>
            <v-col cols="12" sm="4" md="2">
                <v-text-field
                    v-model="filter['min-ram']"
                    @keydown="validateDigit"
                    placeholder="Minimum ram"
                    density="compact"
                    type="number"
                    hide-details
                    clearable
                ></v-text-field>
            </v-col>
            <v-col cols="12" sm="4" md="2">
                <v-text-field
                    v-model="filter['min-gpu']"
                    @keydown="validateDigit"
                    placeholder="Minimum gpu"
                    density="compact"
                    type="number"
                    hide-details
                    clearable
                ></v-text-field>
            </v-col>
        </v-row>
        <v-row>
            <v-col cols="12" sm="4" md="3">
                <v-text-field
                    v-model="filter.organization"
                    placeholder="Search organization"
                    density="compact"
                    hide-details
                    clearable
                ></v-text-field>
            </v-col>

            <v-col cols="12" sm="4" md="3">
                <!-- Taking items title and value is default behaviour if item object contains them -->
                <v-select
                    v-model="filter.active"
                    :items="[
                        { title: 'Active', value: 'true' },
                        { title: 'Inactive', value: 'false' },
                    ]"
                    placeholder="Search active"
                    density="compact"
                    hide-details
                    clearable
                ></v-select>
            </v-col>

            <v-col cols="12" sm="4" md="2">
                <v-text-field
                    v-model="filter['max-cpu']"
                    @keydown="validateDigit"
                    placeholder="Maximum cpu"
                    density="compact"
                    type="number"
                    hide-details
                    clearable
                ></v-text-field>
            </v-col>

            <v-col cols="12" sm="4" md="2">
                <v-text-field
                    v-model="filter['max-ram']"
                    @keydown="validateDigit"
                    placeholder="Maximum ram"
                    density="compact"
                    type="number"
                    hide-details
                    clearable
                ></v-text-field>
            </v-col>

            <v-col cols="12" sm="4" md="2">
                <v-text-field
                    v-model="filter['max-gpu']"
                    @keydown="validateDigit"
                    placeholder="Maximum gpu"
                    density="compact"
                    type="number"
                    hide-details
                    clearable
                ></v-text-field>
            </v-col>
        </v-row>

        <v-row class="py-2 d-flex justify-center">
            <div>
                <v-btn @click="emitFilter" color="primary" class="mr-2">
                    Apply
                </v-btn>
                <v-btn
                    @click="clear"
                    color="primary"
                    variant="outlined"
                    class="ml-2"
                >
                    Clear
                </v-btn>
            </div>
        </v-row>
    </v-form>
</template>

<script setup>
import { ref } from "vue";

const emit = defineEmits(["filter"]);

const form = ref(null);
const filter = ref({
    name: "",
    category: "",
    "min-cpu": "",
    "max-cpu": "",
    "min-ram": "",
    "max-ram": "",
    "min-gpu": "",
    "max-gpu": "",
    organization: "",
    active: null,
});

function validateDigit(event) {
    if (event.key === "Backspace") {
        return;
    }

    if (event.key === " " || isNaN(Number(event.key))) {
        event.preventDefault();
    }
}

function emitFilter() {
    emit("filter", filter.value);
}

function clear() {
    form.value.reset();
    emitFilter();
}
</script>

<style scoped></style>
