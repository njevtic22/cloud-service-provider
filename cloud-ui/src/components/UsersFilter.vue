<template>
    <v-form ref="form">
        <v-row>
            <v-col>
                <v-text-field
                    v-model="filter.name"
                    density="compact"
                    placeholder="Search name"
                    hide-details
                    clearable
                ></v-text-field>
            </v-col>
            <v-col>
                <v-text-field
                    v-model="filter.surname"
                    density="compact"
                    placeholder="Search surname"
                    hide-details
                    clearable
                ></v-text-field>
            </v-col>
            <v-col>
                <v-text-field
                    v-model="filter.email"
                    density="compact"
                    placeholder="Search email"
                    hide-details
                    clearable
                ></v-text-field>
            </v-col>
            <v-col>
                <v-text-field
                    v-model="filter.username"
                    density="compact"
                    placeholder="Search username"
                    hide-details
                    clearable
                ></v-text-field>
            </v-col>
            <v-col>
                <v-text-field
                    v-model="filter.role"
                    density="compact"
                    placeholder="Search role"
                    hide-details
                    clearable
                ></v-text-field>
            </v-col>
            <v-col>
                <v-text-field
                    v-model="filter.organization"
                    density="compact"
                    placeholder="Search organization"
                    hide-details
                    clearable
                ></v-text-field>
            </v-col>
        </v-row>
        <v-row class="py-2 d-flex justify-center">
            <div>
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
    surname: "",
    email: "",
    username: "",
    role: "",
    organization: "",
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
