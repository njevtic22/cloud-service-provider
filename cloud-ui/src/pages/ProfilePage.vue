<template>
    <v-card class="mx-auto" elevation="4" width="90%">
        <profile-data></profile-data>
    </v-card>
</template>

<script setup>
import { useProfileStore } from "@/stores/profile.js";

useProfileStore().fetchProfile();
</script>

<style scoped></style>
