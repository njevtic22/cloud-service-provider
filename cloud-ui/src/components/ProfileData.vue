<template>
    <v-row no-gutters>
        <v-col cols="12" sm="2" md="4" class="d-flex flex-column align-center">
            <v-img
                src="/src/assets/user-logo.png"
                alt="Profile image"
                class="profile-image"
            >
            </v-img>
        </v-col>
        <v-col cols="12" sm="10" md="8">
            <v-card-text>
                <v-row class="d-xs-flex justify-center">
                    <v-col cols="11" :sm="nameWidth" :md="nameWidth">
                        <v-text-field
                            v-model="store.profile.name"
                            label="Name"
                            variant="underlined"
                            readonly
                        >
                        </v-text-field>
                    </v-col>
                    <v-col cols="11" :sm="nameWidth" :md="nameWidth">
                        <v-text-field
                            v-model="store.profile.surname"
                            label="Surname"
                            variant="underlined"
                            readonly
                        >
                        </v-text-field>
                    </v-col>
                    <v-col v-if="!authStore.isSuperAdmin" cols="11" sm="4">
                        <v-text-field
                            v-model="store.profile.organizationName"
                            label="Organization"
                            variant="underlined"
                            readonly
                        >
                        </v-text-field>
                    </v-col>
                </v-row>
                <v-row class="d-xs-flex justify-center">
                    <v-col cols="11" sm="4">
                        <v-text-field
                            v-model="store.profile.email"
                            label="Email"
                            variant="underlined"
                            readonly
                        >
                        </v-text-field>
                    </v-col>
                    <v-col cols="11" sm="4">
                        <v-text-field
                            v-model="store.profile.username"
                            label="Username"
                            variant="underlined"
                            readonly
                        >
                        </v-text-field>
                    </v-col>
                    <v-col cols="11" sm="4">
                        <v-text-field
                            v-model="store.profile.role"
                            label="Role"
                            variant="underlined"
                            readonly
                        >
                        </v-text-field>
                    </v-col>
                </v-row>
            </v-card-text>
        </v-col>
    </v-row>
</template>

<script setup>
import { computed } from "vue";
import { useProfileStore } from "@/stores/profile.js";
import { useAuthStore } from "@/stores/auth.js";

const store = useProfileStore();
const authStore = useAuthStore();

const nameWidth = computed(() => {
    if (authStore.isSuperAdmin) {
        return "6";
    }
    return "4";
});
</script>

<style scoped>
.profile-image {
    width: 50%;
}

@media (max-width: 600px) {
    .profile-image {
        width: 25%;
    }
}
</style>
