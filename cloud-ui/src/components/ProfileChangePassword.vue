<template>
    <v-form ref="form">
        <v-row class="d-flex justify-center">
            <v-col cols="11" sm="6">
                <v-text-field
                    v-model="data.oldPassword"
                    :append-inner-icon="
                        show.oldPassword ? 'mdi-eye' : 'mdi-eye-off'
                    "
                    @click:append-inner="show.oldPassword = !show.oldPassword"
                    :type="show.oldPassword ? 'text' : 'password'"
                    :rules="[rules.required]"
                    label="Old password"
                ></v-text-field>
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
                <v-text-field
                    v-model="data.repeatedPassword"
                    :append-inner-icon="
                        show.newPassword ? 'mdi-eye' : 'mdi-eye-off'
                    "
                    @click:append-inner="show.newPassword = !show.newPassword"
                    :type="show.newPassword ? 'text' : 'password'"
                    :rules="[rules.required, rules.matchWithNewPassword]"
                    label="Repeated password"
                    ref="repeatedPasswordRef"
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
import { ref, computed, watch, inject } from "vue";
import { useProfileStore } from "@/stores/profile.js";

const snackbar = inject("snackbar");
const store = useProfileStore();

const data = ref({
    oldPassword: "",
    newPassword: "",
    repeatedPassword: "",
});

const form = ref(null);
const repeatedPasswordRef = ref(null);
const newPasswordRef = ref(null);

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

async function update() {
    const { valid } = await form.value.validate();
    const passValid = newPasswordRef.value.validate();
    if (!valid || !passValid) {
        return;
    }

    const successCallback = () => {
        form.value.reset();
        newPasswordRef.value.reset();
        snackbar("Password changed", 3 * 1000);
    };

    store.updatePassword(data.value, successCallback);
}
</script>

<style scoped></style>
