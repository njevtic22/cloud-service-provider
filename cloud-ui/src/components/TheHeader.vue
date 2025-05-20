<template>
    <v-app-bar scroll-behavior="hide" color="primary">
        <template v-slot:prepend>
            <v-app-bar-title
                @click="router.push('/')"
                class="cursor-pointer ma-2 pa-2"
            >
                Cloud service
            </v-app-bar-title>
            <v-app-bar-nav-icon
                @click="emit('toggle-sidebar')"
            ></v-app-bar-nav-icon>
        </template>

        <template v-slot:append>
            <v-app-bar-title
                v-show="route.name"
                :key="route.meta.title"
                class="ma-2 pa-2"
            >
                {{ route.name }}
            </v-app-bar-title>
        </template>
    </v-app-bar>
</template>

<script setup>
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();
const emit = defineEmits(["toggle-sidebar"]);
</script>

<style scoped></style>
