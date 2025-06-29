<template>
    <v-dialog v-model="dialog" :width="width">
        <user-form
            @submit="submit"
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

function submit(user) {
    console.log(user);
    dialog.value = false;
}
</script>

<style scoped></style>
