<template>
    <v-form ref="form">
        <v-row class="d-flex justify-center">
            <v-col cols="11" sm="6">
                <password-input
                    v-model:password="data.oldPassword"
                    v-model:showPassword="show.oldPassword"
                    :rules="[rules.required]"
                    label="Old password"
                    ref="oldPasswordRef"
                ></password-input>
            </v-col>
        </v-row>

        <v-row class="d-flex justify-center">
            <v-col cols="11" sm="6">
                <password-input
                    v-model:password="data.newPassword"
                    v-model:showPassword="show.newPassword"
                    v-model:showRules="show.passwordRules"
                    :rules="[rules.required]"
                    label="New password"
                    ref="newPasswordRef"
                ></password-input>
            </v-col>
        </v-row>

        <v-row class="d-flex justify-center">
            <v-col cols="11" sm="6">
                <password-input
                    v-model:password="data.repeatedPassword"
                    v-model:showPassword="show.newPassword"
                    :rules="[rules.required, rules.matchWithNewPassword]"
                    label="Repeated password"
                    ref="repeatedPasswordRef"
                ></password-input>
            </v-col>
        </v-row>

        <v-row class="d-flex justify-center">
            <v-btn :disabled="!isFormValid" @click="update" color="primary">
                Save
            </v-btn>
        </v-row>
        <br />
    </v-form>
</template>

<script setup>
import { ref, computed, watch, inject } from "vue";
import { useProfileStore } from "@/stores/profile.js";

const snackbar = inject("snackbar");
const store = useProfileStore();

const data = ref({
    oldPassword: "",
    newPassword: "",
    repeatedPassword: "",
});

// form constant can be removed because it does not registers input fields from child components of this component
const form = ref(null);
const repeatedPasswordRef = ref(null);
const newPasswordRef = ref(null);
const oldPasswordRef = ref(null);

watch(
    () => data.value.newPassword,
    () => {
        if (data.value.repeatedPassword) {
            repeatedPasswordRef.value.validate();
        }
    }
);

const show = ref({
    oldPassword: false,
    newPassword: false,
    passwordRules: false,
});

const rules = {
    required: (value) => Boolean(value) || "Required",
    matchWithNewPassword: (value) =>
        value === data.value.newPassword ||
        "Repeated password does not match new password",
};

async function validateForm() {
    // const { valid } = await form.value.validate();
    const oldValid = (await oldPasswordRef.value.validate()).length === 0;
    const newValid = (await newPasswordRef.value.validate()).length === 0;
    const repeatedValid =
        (await repeatedPasswordRef.value.validate()).length === 0;

    return /* valid && */ oldValid && newValid && repeatedValid;
}

function resetForm() {
    form.value.reset();
    oldPasswordRef.value.reset();
    newPasswordRef.value.reset();
    repeatedPasswordRef.value.reset();
}

async function update() {
    const valid = await validateForm();
    if (!valid) {
        return;
    }

    const successCallback = () => {
        resetForm();
        snackbar("Password changed", 3 * 1000);
    };

    store.updatePassword(data.value, successCallback);
}

const isFormValid = computed(() => {
    return (
        // is always null when form has no input fields
        // form.value?.isValid &&
        oldPasswordRef.value?.isValid &&
        newPasswordRef.value?.isValid &&
        repeatedPasswordRef.value?.isValid
    );
});
</script>

<style scoped></style>
