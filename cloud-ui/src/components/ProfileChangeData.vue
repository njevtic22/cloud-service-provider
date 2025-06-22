<template>
    <v-form ref="form">
        <v-row class="d-flex justify-center">
            <v-col cols="12" sm="4">
                <v-text-field
                    v-model="profile.name"
                    :rules="[rules.required]"
                    label="Name"
                ></v-text-field>
            </v-col>
            <v-col cols="12" sm="4">
                <v-text-field
                    v-model="profile.surname"
                    :rules="[rules.required]"
                    label="Surname"
                ></v-text-field>
            </v-col>
        </v-row>
        <v-row class="d-flex justify-center">
            <v-col cols="12" sm="4">
                <v-text-field
                    v-model="profile.email"
                    :rules="[rules.required, rules.email]"
                    label="Email"
                ></v-text-field>
            </v-col>
            <v-col cols="12" sm="4">
                <v-text-field
                    v-model="profile.username"
                    :rules="[rules.required]"
                    label="Username"
                ></v-text-field>
            </v-col>
        </v-row>
        <v-row class="d-flex justify-center">
            <v-btn :disabled="!form?.isValid" @click="update" color="primary">
                Save
            </v-btn>
        </v-row>
        <br />
    </v-form>
</template>

<script setup>
import { ref, computed, inject } from "vue";
import { useProfileStore } from "@/stores/profile.js";

const snackbar = inject("snackbar");
const store = useProfileStore();

const profile = ref({ ...store.profile });

const form = ref(null);

const rules = {
    required: (value) => Boolean(value) || "Required",
    email: (email) =>
        /.+@.+\..+/.test(email) || "Email must be valid email adress",
};

function update() {
    const successCallback = () => snackbar("Profile updated", 3 * 1000);

    store.update(profile.value, successCallback);
}
</script>

<style scoped></style>
