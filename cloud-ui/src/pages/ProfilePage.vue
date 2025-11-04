<template>
    <v-card class="ma-auto" elevation="4" width="90%">
        <profile-data></profile-data>
        <v-btn @click="expanded = !expanded" variant="flat" block>
            <v-icon class="ml-auto mr-auto mt-n1 mb-n1">
                <template v-if="expanded">mdi-chevron-up</template>
                <template v-else>mdi-chevron-down</template>
            </v-icon>
        </v-btn>
        <v-expand-transition>
            <profile-change v-if="expanded"></profile-change>
        </v-expand-transition>

        <!-- <br />
        <br />
        <v-expansion-panels static elevation="0" variant="accordion">
            <v-expansion-panel>
                <v-expansion-panel-title hide-actions>
                    <template v-slot:default="{ expanded }">
                        <v-icon class="ml-auto mr-auto mt-n1 mb-n1">
                            <template v-if="expanded">mdi-chevron-up</template>
                            <template v-else>mdi-chevron-down</template>
                        </v-icon>
                    </template>
                </v-expansion-panel-title>
                <v-expansion-panel-text>
                    <profile-change></profile-change>
                </v-expansion-panel-text>
            </v-expansion-panel>
        </v-expansion-panels> -->
    </v-card>
</template>

<script setup>
import { ref } from "vue";
import { useProfileStore } from "@/stores/profile.js";

useProfileStore().fetchProfile();

const expanded = ref(false);
</script>

<style scoped></style>
