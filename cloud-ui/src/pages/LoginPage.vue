<template>
    <!-- <v-card class="mx-auto padded-3 w-25"> -->
    <v-card class="login-card" elevation="4">
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
import { useAuthStore } from "@/stores/auth.js";

const router = useRouter();
const auth = useAuthStore();

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

    // TODO: reset form in callback (or not because component is destroyed)
    const successCallback = () => router.push("/");
    const errorCallback = (err) => {
        error.value.message = err.response.data.message;
        error.value.occured = true;
    };

    auth.login(copy, successCallback, errorCallback);
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

.login-card {
    margin: auto;
    width: 100%;
    max-width: 25%; /* default fallback */
    padding: 3%;
}

/* xs */
@media (max-width: 600px) {
    .login-card {
        max-width: 60%;
        padding: 5%;
    }
}

/* sm */
@media (min-width: 601px) and (max-width: 960px) {
    .login-card {
        max-width: 40%;
        padding: 4%;
    }
}
</style>
