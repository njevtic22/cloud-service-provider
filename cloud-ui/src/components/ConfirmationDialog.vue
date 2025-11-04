<template>
    <the-dialog v-model="dialog.show" :fullscreen="false" persistent>
        <v-card :append-icon="dialog.icon" :title="dialog.title">
            <v-card-text>{{ dialog.text }}</v-card-text>

            <v-card-actions>
                <v-btn
                    @click="confirm(true)"
                    variant="elevated"
                    color="primary"
                >
                    {{ dialog.yes }}
                </v-btn>
                <v-btn
                    @click="confirm(false)"
                    variant="outlined"
                    color="primary"
                >
                    {{ dialog.no }}
                </v-btn>
            </v-card-actions>
        </v-card>
    </the-dialog>
</template>

<script setup>
import { ref } from "vue";

const dialog = ref({
    show: false,
    title: "",
    icon: "",
    text: "",
    yes: "",
    no: "",
});

let resolvePromise;
function open(
    text,
    title = "Confirm action",
    icon = "mdi-help",
    yes = "Yes",
    no = "No"
) {
    dialog.value.text = text;
    dialog.value.icon = icon;
    dialog.value.title = title;
    dialog.value.yes = yes;
    dialog.value.no = no;

    dialog.value.show = true;

    return new Promise((resolve) => {
        resolvePromise = resolve;
    });
}

function confirm(decision) {
    resolvePromise(decision);
    dialog.value.show = false;
}

defineExpose({
    open,
});
</script>

<style scoped></style>
