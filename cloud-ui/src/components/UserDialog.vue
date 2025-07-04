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
import { computed, inject } from "vue";
import { useDisplay } from "vuetify";
import { useUserStore } from "@/stores/user.js";

const emit = defineEmits(["submit"]);

const snackbar = inject("snackbar");

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

const store = useUserStore();

function submit(user) {
    user.organization = user.organization.id;
    store.addUser(user, () => {
        dialog.value = false;
        emit("submit");
        snackbar("User added", 3 * 3000);
    });
}
</script>

<style scoped></style>
