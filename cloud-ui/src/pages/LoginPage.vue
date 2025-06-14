<template>
    <v-card class="mx-auto padded-3" width="25%">
        <v-form ref="form">
            <v-text-field
                v-model="data.username"
                :rules="[required]"
                label="Username"
                class="padded-3"
                required
            ></v-text-field>

            <v-text-field
                v-model="data.password"
                :rules="[required]"
                :append-inner-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append-inner="showPassword = !showPassword"
                :type="showPassword ? 'text' : 'password'"
                label="Password"
                class="padded-3"
                required
            ></v-text-field>

            <v-card-actions class="justify-center">
                <v-btn
                    :disabled="!form?.isValid"
                    @click="login"
                    variant="elevated"
                    color="primary"
                >
                    Login
                </v-btn>
            </v-card-actions>
        </v-form>
    </v-card>
</template>

<script setup>
import { ref } from "vue";

const form = ref(null);
const required = (value) => !!value || "Required";

const data = ref({
    username: "",
    password: "",
});

const showPassword = ref(false);

async function login() {
    const { valid } = await form.value.validate();

    if (!valid) {
        return;
    }

    const copy = { ...data.value };
    console.log(copy);
}

// :disabled="!form?.isValid"
// const isFormValid = computed(() => {
//     return form.value?.isValid;
// });
</script>

<style scoped></style>
