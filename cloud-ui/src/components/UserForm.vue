<template>
    <v-card :append-icon="icon" :title="title">
        <v-card-text>
            <v-form ref="form">
                <v-row>
                    <v-col cols="12" sm="6">
                        <v-text-field
                            v-model="user.name"
                            :rules="[rules.required]"
                            label="Name"
                        ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6">
                        <v-text-field
                            v-model="user.surname"
                            :rules="[rules.required]"
                            label="Surname"
                        ></v-text-field>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="12" sm="6">
                        <v-text-field
                            v-model="user.email"
                            :rules="[rules.required, rules.email]"
                            label="Email"
                        ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6">
                        <v-text-field
                            v-model="user.username"
                            :rules="[rules.required]"
                            label="Username"
                        ></v-text-field>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="12" sm="6">
                        <v-text-field
                            v-model="user.organization"
                            :rules="[rules.required]"
                            label="Organization"
                        ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6">
                        <v-text-field
                            v-model="user.role"
                            :rules="[rules.required]"
                            label="Role"
                        ></v-text-field>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="12" sm="6">
                        <v-text-field
                            v-model="user.password"
                            :rules="[rules.required]"
                            label="Password"
                        ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6">
                        <v-text-field
                            v-model="user.repeatedPassword"
                            :rules="[rules.required]"
                            label="Repeated password"
                        ></v-text-field>
                    </v-col>
                </v-row>
            </v-form>
        </v-card-text>

        <v-card-actions>
            <v-btn @click="submit" color="primary" variant="elevated">
                {{ submitText }}
            </v-btn>
            <v-btn @click="cancel" color="primary" variant="outlined">
                {{ cancelText }}
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script setup>
import { ref } from "vue";

defineProps({
    icon: String,
    title: String,
    "submit-text": String,
    "cancel-text": {
        type: String,
        default: "Cancel",
    },
});

const emit = defineEmits(["submit", "cancel"]);
const form = ref(null);

const user = ref({
    name: "",
    surname: "",
    email: "",
    username: "",
    organization: -1,
    role: "",
    password: "",
    repeatedPassword: "",
});

const rules = {
    required: (value) => Boolean(value) || "Required",
    email: (email) =>
        /.+@.+\..+/.test(email) || "Email must be valid email adress",
    matchWithNewPassword: (value) =>
        value === data.value.newPassword ||
        "Repeated password does not match new password",
};

function clear() {
    console.log("Called clear");
}

function submit() {
    // Retrieve value
    clear();
    emit("submit", true);
}

function cancel() {
    clear();
    emit("cancel");
}
</script>

<style scoped></style>
