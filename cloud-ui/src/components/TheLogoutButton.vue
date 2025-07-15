<template>
    <v-btn @click="logout" block color="primary">Logout</v-btn>
</template>

<script setup>
import { useRouter } from "vue-router";
import { useMachineStore } from "@/stores/machine.js";
import { useAuthStore } from "@/stores/auth.js";
import { useProfileStore } from "@/stores/profile.js";
import { useUserStore } from "@/stores/user.js";
import { useOrganizationStore } from "@/stores/organization.js";

const router = useRouter();
const auth = useAuthStore();

function logout() {
    const callback = () => {
        useMachineStore().$reset();
        useProfileStore().$reset();

        router.push("/login");

        setTimeout(() => {
            useUserStore().$reset();
            useOrganizationStore().$reset();
        }, 10);

        // _s is not officially documented - it is internal to pinia
        // import { getActivePinia } from 'pinia';
        // getActivePinia()._s.forEach(store => store.$reset());
    };
    auth.logout(callback);
}
</script>

<style scoped></style>
