<template>
    <v-snackbar
        v-model="snack.visible"
        :timeout="snack.timeout"
        :color="snack.backgroundColor"
        :timer="snack.accentColor"
    >
        {{ snack.text }}

        <template v-slot:actions>
            <v-btn @click="snack.visible = false" :color="snack.accentColor">
                Close
            </v-btn>
        </template>
    </v-snackbar>
</template>

<script setup>
import { ref } from "vue";

const snack = ref({
    visible: false,
    text: "",
    backgroundColor: "",
    accentColor: "light-blue-darken-1",
    timeout: -1,
});

function show(
    newText,
    newTimeout = -1,
    newAccentColor = "light-blue-darken-1",
    newBackgroundColor = ""
) {
    snack.value.text = newText;
    snack.value.timeout = newTimeout;
    snack.value.accentColor = newAccentColor;
    snack.value.backgroundColor = newBackgroundColor;

    snack.value.visible = true;
}

defineExpose({
    show,
});
</script>

<style scoped></style>
