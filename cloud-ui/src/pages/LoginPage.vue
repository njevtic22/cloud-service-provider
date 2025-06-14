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

            <v-card-item
                :class="error.occured ? '' : 'error-hidden'"
                class="d-flex justify-center error-color"
            >
                {{ error.message }}
            </v-card-item>

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
import { useRouter } from "vue-router";
import { logIn } from "@/stores/auth.js";

const router = useRouter();

const form = ref(null);
const required = (value) => !!value || "Required";

const data = ref({
    username: "",
    password: "",
});

const showPassword = ref(false);

const error = ref({
    message: "Bad credentials",
    occured: false,
});

async function login() {
    const { valid } = await form.value.validate();

    if (!valid) {
        return;
    }

    const copy = { ...data.value };

    const successCallback = () => router.push("/");
    const errorCallback = (err) => {
        error.value.message = err.response.data.message;
        error.value.occured = true;
    };

    logIn(copy, successCallback, errorCallback);
}

// :disabled="!form?.isValid"
// const isFormValid = computed(() => {
//     return form.value?.isValid;
// });
</script>

<style scoped>
.error-hidden {
    visibility: hidden;
}

.error-color {
    color: #b71c1c;
}
</style>
