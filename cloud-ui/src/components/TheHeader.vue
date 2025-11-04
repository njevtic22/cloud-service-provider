<template>
    <v-app-bar :density="density" scroll-behavior="hide" color="primary">
        <template v-slot:prepend>
            <v-app-bar-title
                v-if="display.width.value > 400"
                @click="redirect"
                class="cursor-pointer ma-2 pa-2"
            >
                Cloud service
            </v-app-bar-title>
            <v-app-bar-nav-icon
                @click="emit('toggle-sidebar')"
            ></v-app-bar-nav-icon>
        </template>

        <template v-slot:append>
            <transition name="fade">
                <v-app-bar-title
                    v-show="route.name"
                    :key="route.name"
                    class="ma-2 pa-2"
                >
                    {{ route.name }}
                </v-app-bar-title>
            </transition>
        </template>
    </v-app-bar>
</template>

<script setup>
import { computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useDisplay } from "vuetify";
import { useAuthStore } from "@/stores/auth.js";

const route = useRoute();
const router = useRouter();
const emit = defineEmits(["toggle-sidebar"]);

const auth = useAuthStore();

function redirect() {
    if (auth.isAnonymous) {
        router.push("/login");
        return;
    }

    router.push("/");
    return;
}

const display = useDisplay();

const density = computed(() => {
    if (display.mdAndDown.value) {
        return "compact";
    }
    return "default";
});
</script>

<style scoped>
/* using useDisplay instead
@media (max-width: 375px) {
    .hidden {
        display: none;
    }
} */

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
