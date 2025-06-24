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
                <v-text-field
                    v-model="data.newPassword"
                    :append-inner-icon="
                        show.newPassword ? 'mdi-eye' : 'mdi-eye-off'
                    "
                    @click:append-inner="show.newPassword = !show.newPassword"
                    :append-icon="
                        show.passwordRules
                            ? 'mdi-chevron-up'
                            : 'mdi-chevron-down'
                    "
                    @click:append="show.passwordRules = !show.passwordRules"
                    :type="show.newPassword ? 'text' : 'password'"
                    :rules="[rules.required]"
                    :maxlength="50"
                    :error="!isNewPasswordValid && Boolean(data.newPassword)"
                    label="New password"
                    counter
                >
                    <template v-slot:loader>
                        <v-progress-linear
                            :model-value="progress.value"
                            :color="progress.color"
                            height="7"
                        ></v-progress-linear>
                    </template>
                </v-text-field>

                <v-expand-transition>
                    <div v-show="show.passwordRules" class="padded-2">
                        <password-strength
                            v-model="data.newPassword"
                            @progress-changed="updateProgressBar"
                            @password-valid-changed="
                                isNewPasswordValid = $event
                            "
                        ></password-strength>
                    </div>
                </v-expand-transition>
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

const progress = ref({
    value: 0,
    color: "red-darken-1",
});

const rules = {
    required: (value) => Boolean(value) || "Required",
    matchWithNewPassword: (value) =>
        value === data.value.newPassword ||
        "Repeated password does not match new password",
};

async function update() {
    const { valid } = await form.value.validate();
    if (!valid) {
        return;
    }

    const successCallback = () => {
        form.value.reset();
        snackbar("Password changed", 3 * 1000);
    };

    store.updatePassword(data.value, successCallback);
}

const isNewPasswordValid = ref(false);

function updateProgressBar(newProgres) {
    progress.value.value = newProgres;
    progress.value.color = getProgressColor(newProgres);
}

function getProgressColor(progress) {
    if (progress <= 25) {
        return "red-darken-1";
    }

    if (progress <= 50) {
        return "orange-darken-1";
    }

    if (progress <= 75) {
        return "yellow-darken-1";
    }

    if (progress < 100) {
        return "light-blue-darken-1";
    }

    return "green-darken-1";
}
</script>

<style scoped></style>
