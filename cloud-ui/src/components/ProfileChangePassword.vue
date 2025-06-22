<template>
    <v-form ref="form">
        <v-row>
            <v-col>
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

        <v-row>
            <v-col>
                <v-text-field
                    v-model="data.newPassword"
                    :append-inner-icon="
                        show.newPassword ? 'mdi-eye' : 'mdi-eye-off'
                    "
                    @click:append-inner="show.newPassword = !show.newPassword"
                    :type="show.newPassword ? 'text' : 'password'"
                    :rules="[rules.required]"
                    :maxlength="50"
                    label="New password"
                    counter
                ></v-text-field>
            </v-col>
        </v-row>

        <v-row>
            <v-col>
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
        <v-btn @click="form.validate()">validate</v-btn>
    </v-form>
</template>

<script setup>
import { ref, computed, watch, inject } from "vue";
import { useProfileStore } from "@/stores/profile.js";

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
});

const rules = {
    required: (value) => Boolean(value) || "Required",
    matchWithNewPassword: (value) =>
        value === data.value.newPassword ||
        "Repeated password does not match new password",
};
</script>

<style scoped></style>
