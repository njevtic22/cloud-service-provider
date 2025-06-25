<template>
    <v-btn @click="logout" block color="primary">Logout</v-btn>
</template>

<script setup>
import { useRouter } from "vue-router";
import { useMachineStore } from "@/stores/machine.js";
import { useAuthStore } from "@/stores/auth.js";
import { useProfileStore } from "@/stores/profile.js";
import { useUserStore } from "@/stores/user.js";

const router = useRouter();
const auth = useAuthStore();

function logout() {
    const callback = () => {
        useMachineStore().$reset();
        useProfileStore().$reset();
        useUserStore().$reset();

        router.push("/login");

        // _s is not officially documented - it is internal to pinia
        // import { getActivePinia } from 'pinia';
        // getActivePinia()._s.forEach(store => store.$reset());
    };
    auth.logout(callback);
}
</script>

<style scoped></style>
