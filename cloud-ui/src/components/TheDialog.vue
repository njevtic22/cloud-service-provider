<template>
    <v-dialog
        v-model="dialog"
        :width="width"
        :fullscreen="_fullscreen"
        :persistent="persistent"
    >
        <slot></slot>
    </v-dialog>
</template>

<script setup>
import { computed } from "vue";
import { useDisplay } from "vuetify";

const props = defineProps({
    fullscreen: {
        type: Boolean,
        default: undefined,
    },
    persistent: {
        type: Boolean,
        default: false,
    },
});

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

const _fullscreen = computed(() => {
    return props.fullscreen == undefined ? display.xs.value : props.fullscreen;
});
</script>

<style scoped></style>
