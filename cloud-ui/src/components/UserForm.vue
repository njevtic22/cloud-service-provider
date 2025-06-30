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
                        <organization-select
                            v-model="user.organization"
                        ></organization-select>
                    </v-col>
                    <v-col cols="12" sm="6">
                        <v-select
                            v-model="user.role"
                            :rules="[rules.required]"
                            :items="['ROLE_ADMIN', 'ROLE_USER']"
                            label="Role"
                            clearable
                        ></v-select>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="12" sm="6">
                        <password-input
                            v-model:password="user.password"
                            v-model:showPassword="show.password"
                            v-model:showRules="show.rules"
                            :rules="[rules.required]"
                            label="Password"
                            ref="passwordRef"
                        ></password-input>
                    </v-col>
                    <v-col cols="12" sm="6">
                        <password-input
                            v-model:password="user.repeatedPassword"
                            v-model:showPassword="show.password"
                            :rules="[
                                rules.required,
                                rules.matchWithNewPassword,
                            ]"
                            label="Repeated password"
                            ref="repeatedRef"
                        ></password-input>
                    </v-col>
                </v-row>
            </v-form>
        </v-card-text>

        <v-card-actions>
            <v-btn
                :disabled="!isFormValid"
                @click="submit"
                color="primary"
                variant="elevated"
            >
                {{ submitText }}
            </v-btn>
            <v-btn @click="cancel" color="primary" variant="outlined">
                {{ cancelText }}
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script setup>
import { ref, computed } from "vue";

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
const passwordRef = ref(null);
const repeatedRef = ref(null);

const user = ref({
    name: "",
    surname: "",
    email: "",
    username: "",
    organization: null,
    role: "",
    password: "",
    repeatedPassword: "",
});

const show = ref({
    password: false,
    rules: false,
});

const rules = {
    required: (value) => Boolean(value) || "Required",
    email: (email) =>
        /.+@.+\..+/.test(email) || "Email must be valid email adress",
    matchWithNewPassword: (value) =>
        value === user.value.password ||
        "Repeated password does not match new password",
};

async function validateForm() {
    const { valid } = await form.value.validate();
    const passValid = (await passwordRef.value.validate()).length === 0;
    const repeatedValid = (await repeatedRef.value.validate()).length === 0;
    return valid && passValid && repeatedValid;
}

function resetForm() {
    form.value.reset();
    passwordRef.value.reset();
    repeatedRef.value.reset();
}

function clear() {
    resetForm();
}

async function submit() {
    const valid = await validateForm();
    if (!valid) {
        return;
    }

    emit("submit", user.value);
    clear();
}

function cancel() {
    emit("cancel");
    clear();
}

const isFormValid = computed(() => {
    return (
        form.value?.isValid &&
        passwordRef.value?.isValid &&
        repeatedRef.value?.isValid
    );
});
</script>

<style scoped></style>
