<template>
    <v-navigation-drawer v-model="isOpened" elevation="4">
        <v-list density="compact" nav>
            <v-list-item
                v-for="link in links"
                :key="link.name"
                :title="link.name"
                :prepend-icon="link.icon"
                @click="link.redirect"
            ></v-list-item>
        </v-list>
    </v-navigation-drawer>
</template>

<script setup>
import { useRouter } from "vue-router";
import { ref } from "vue";

const router = useRouter();
const isOpened = defineModel();

const links = ref([
    {
        name: "Machines",
        icon: "mdi-server",
        redirect() {
            router.push("/");
        },
    },
    {
        name: "Users",
        icon: "mdi-account-multiple",
        redirect() {
            router.push("/users");
        },
    },
]);
</script>

<style scoped></style>
