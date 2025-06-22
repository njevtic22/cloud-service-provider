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
import { ref, computed } from "vue";
import { useProfileStore } from "@/stores/profile.js";

const store = useProfileStore();

const profile = ref({
    name: store.profile.name,
    surname: store.profile.surname,
    email: store.profile.email,
    username: store.profile.username,
});

const form = ref(null);

const rules = {
    required: (value) => Boolean(value) || "Required",
    email: (email) =>
        /.+@.+\..+/.test(email) || "Email must be valid email adress",
};

function update() {
    console.log("UPDATED");
}
</script>

<style scoped></style>
