<template>
    <v-app>
        <v-main>
            <the-header @toggle-sidebar="sidebarOpened = !sidebarOpened">
            </the-header>
            <the-sidebar v-model="sidebarOpened"></the-sidebar>
            <div class="main-container">
                <router-view />
            </div>
        </v-main>
    </v-app>
</template>

<script setup>
import { ref } from "vue";

const sidebarOpened = ref(true);
</script>

<style scoped>
.main-container {
    padding: 2%;
}
</style>
