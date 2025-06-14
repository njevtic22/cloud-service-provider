<template>
    <v-app>
        <v-main>
            <the-snackbar ref="theSnack"></the-snackbar>
            <the-header @toggle-sidebar="sidebarOpened = !sidebarOpened">
            </the-header>
            <the-sidebar v-model="sidebarOpened"></the-sidebar>
            <div class="padded-2">
                <router-view />
            </div>
        </v-main>
    </v-app>
</template>

<script setup>
import { ref, provide, onMounted } from "vue";
import axios from "axios";

const sidebarOpened = ref(true);
const theSnack = ref(null);

function showErrorSnack(error) {
    let errorMessage = error.response.data.message;
    let errorDetails = error.response.data.details;

    if (errorMessage === "Invalid field(s)") {
        errorMessage = "";
        for (let i = 0; i < errorDetails.length; i++) {
            const detail = errorDetails[i];

            for (let j = 0; j < detail.messages.length; j++) {
                const message = detail.messages[j];

                let suffix = ". ";
                if (message.endsWith(".")) {
                    suffix = " ";
                }

                errorMessage += message + suffix;
            }
        }
    }

    theSnack.value.show(errorMessage, -1, "red-darken-1", "");
}

axios.defaults.headers.common["Authorization"] = localStorage.getItem("token")
    ? "Bearer " + localStorage.getItem("token")
    : null;

onMounted(() => {
    provide("snackbar", theSnack.value.show);
    provide("showErrorSnack", showErrorSnack);
});
</script>

<style scoped></style>
