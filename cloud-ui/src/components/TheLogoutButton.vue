<template>
    <v-btn @click="logout" block color="primary">Logout</v-btn>
</template>

<script setup>
import { useRouter } from "vue-router";
import axios from "axios";
import { useMachineStore } from "@/stores/machine.js";

const router = useRouter();

function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("role");

    useMachineStore().$reset();

    delete axios.defaults.headers.common["Authorization"];
    router.push("/login");

    // _s is not officially documented - it is internal to pinia
    // import { getActivePinia } from 'pinia';
    // getActivePinia()._s.forEach(store => store.$reset());
}
</script>

<style scoped></style>
