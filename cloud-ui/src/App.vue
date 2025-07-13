<template>
    <v-app>
        <v-main>
            <the-snackbar ref="theSnack"></the-snackbar>
            <the-header @toggle-sidebar="sidebarOpened = !sidebarOpened">
            </the-header>
            <the-sidebar v-model="sidebarOpened"></the-sidebar>
            <div class="padded-2">
                <router-view v-slot="{ Component, route }">
                    <transition name="fade">
                        <div :key="route.path">
                            <component :is="Component" />
                        </div>
                    </transition>
                </router-view>
            </div>
        </v-main>
    </v-app>
</template>

<script setup>
import { ref, provide, onMounted } from "vue";
import { getActivePinia } from "pinia";
import axios from "axios";

const sidebarOpened = ref(true);
const theSnack = ref(null);

function showErrorSnack(error) {
    // What if error happens inside success callback and therefore
    // there is no error.response.data

    let errorMessage = error.response.data.message;
    let errorDetails = error.response.data.details;

    if (errorMessage === "Invalid field(s).") {
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

const pinia = getActivePinia();
pinia.use(() => ({
    showErrorSnack,
}));

provide("showErrorSnack", showErrorSnack);
onMounted(() => {
    provide("snackbar", theSnack.value.show);
});
</script>

<style scoped>
.fade-enter-active {
    transition: opacity 0.3s ease;
    transition-delay: 0.3s;
}

.fade-leave-active {
    transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}
</style>
