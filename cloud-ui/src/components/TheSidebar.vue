<template>
    <v-navigation-drawer v-model="isOpened" elevation="4">
        <v-list density="compact" nav>
            <v-list-item
                v-for="link in activeLinks"
                :key="link.name"
                :title="link.name"
                :prepend-icon="link.icon"
                @click="link.redirect"
            ></v-list-item>
        </v-list>

        <template v-slot:append>
            <the-logout-button v-if="!auth.isAnonymous"></the-logout-button>
        </template>
    </v-navigation-drawer>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/auth.js";

const router = useRouter();
const auth = useAuthStore();

const isOpened = defineModel();

const links = ref([
    {
        name: "Login",
        icon: "mdi-login",
        redirect() {
            router.push("/login");
        },
        isActive() {
            return auth.isAnonymous;
        },
    },
    {
        name: "Machines",
        icon: "mdi-server",
        redirect() {
            router.push("/");
        },
        isActive() {
            return !auth.isAnonymous;
        },
    },
    {
        name: "Users",
        icon: "mdi-account-multiple",
        redirect() {
            router.push("/users");
        },
        isActive() {
            return auth.isSuperAdmin || auth.isAdmin;
        },
    },
    {
        name: "Profile",
        icon: "mdi-account",
        redirect() {
            router.push("/profile");
        },
        isActive() {
            return !auth.isAnonymous;
        },
    },
]);

const activeLinks = computed(() => {
    return links.value.filter((link) => link.isActive());
});
</script>

<style scoped></style>
