<template>
    <v-card>
        <v-tabs v-model="tab" bg-color="primary" align-tabs="center" grow>
            <v-tab prepend-icon="mdi-account-edit" value="Profile">
                Profile
            </v-tab>
            <v-tab prepend-icon="mdi-lock" value="Password"> Password </v-tab>
        </v-tabs>

        <v-card-text>
            <v-tabs-window v-model="tab">
                <v-tabs-window-item value="Profile">
                    <profile-change-data></profile-change-data>
                </v-tabs-window-item>

                <v-tabs-window-item value="Password">
                    <profile-change-password></profile-change-password>
                </v-tabs-window-item>
            </v-tabs-window>
        </v-card-text>
    </v-card>
</template>

<script setup>
import { ref } from "vue";

const tab = ref("Profile");
</script>

<style scoped></style>
