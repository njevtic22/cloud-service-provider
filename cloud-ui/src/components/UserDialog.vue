<template>
    <v-dialog v-model="dialog" :width="width">
        <user-form
            @submit="
                console.log('submitted with ' + $event);
                dialog = false;
            "
            @cancel="dialog = false"
            icon="mdi-account-plus"
            title="Add user"
            submit-text="Add"
        ></user-form>
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
