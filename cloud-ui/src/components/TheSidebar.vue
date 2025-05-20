<template>
    <v-navigation-drawer v-model="isOpened" elevation="4">
        <v-list density="compact" nav>
            <v-list-item
                title="Machines"
                value="Machines"
                prepend-icon="mdi-account"
                @click="router.push('/')"
            >
            </v-list-item>
            <v-list-item
                title="About"
                value="About"
                prepend-icon="mdi-guitar-pick"
                @click="router.push('/about')"
            >
            </v-list-item>
        </v-list>
    </v-navigation-drawer>
</template>

<script setup>
import { useRouter } from "vue-router";
import { defineModel } from "vue";

const router = useRouter();
const isOpened = defineModel();
</script>

<style scoped></style>
