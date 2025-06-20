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
                <v-row>
                    <v-col cols="12" sm="4" md="4">
                        <v-text-field
                            v-model="store.profile.name"
                            label="Name"
                            variant="underlined"
                            readonly
                        >
                        </v-text-field>
                    </v-col>
                    <v-col cols="12" sm="4" md="4">
                        <v-text-field
                            v-model="store.profile.surname"
                            label="Surname"
                            variant="underlined"
                            readonly
                        >
                        </v-text-field>
                    </v-col>
                    <v-col cols="12" sm="4" md="4">
                        <v-text-field
                            v-model="store.profile.organizationName"
                            label="Organization"
                            variant="underlined"
                            readonly
                        >
                        </v-text-field>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="12" sm="4" md="4">
                        <v-text-field
                            v-model="store.profile.email"
                            label="Email"
                            variant="underlined"
                            readonly
                        >
                        </v-text-field>
                    </v-col>
                    <v-col cols="12" sm="4" md="4">
                        <v-text-field
                            v-model="store.profile.username"
                            label="Username"
                            variant="underlined"
                            readonly
                        >
                        </v-text-field>
                    </v-col>
                    <v-col cols="12" sm="4" md="4">
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
import { useProfileStore } from "@/stores/profile.js";

const store = useProfileStore();
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
