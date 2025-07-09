<template>
    <v-dialog v-model="dialog" :width="width">
        <slot></slot>
    </v-dialog>
</template>

<script setup>
import { computed } from "vue";
import { useDisplay } from "vuetify";

const dialog = defineModel();
const display = useDisplay();
const width = computed(() => {
    if (display.xs.value) {
        return "100%";
    } else if (display.smAndDown.value) {
        return "85%";
    } else if (display.mdAndDown.value) {
        return "75%";
    }
    return "50%";
});
</script>

<style scoped></style>
