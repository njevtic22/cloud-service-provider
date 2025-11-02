<template>
    <v-card elevation="4">
        <v-row>
            <v-col cols="12" sm="6" md="4">
                <v-card class="d-flex align-center" :height="height" flat>
                    <div>
                        <v-card-title class="data-font-size">
                            {{ org.name }}
                        </v-card-title>
                        <v-card-text>
                            <v-btn
                                class="data-font-size redirect-button"
                                variant="text"
                            >
                                <strong>Number of users:</strong>
                                {{ org.users }}
                            </v-btn>
                            <v-btn
                                class="data-font-size redirect-button"
                                variant="text"
                            >
                                <strong>Number of machines:</strong>
                                {{ org.machines }}
                            </v-btn>
                            <v-btn
                                class="data-font-size redirect-button"
                                variant="text"
                            >
                                <strong>Number of drives:</strong>
                                {{ org.drivers }}
                            </v-btn>
                        </v-card-text>
                    </div>
                </v-card>
            </v-col>
            <v-col cols="12" sm="6" md="8">
                <!-- Not correct size -->
                <!-- <v-img
                    :src="org.logo"
                    alt="Organization image"
                    class="logo"
                >
                </v-img> -->

                <div class="py-8">
                    <v-carousel
                        :height="height"
                        hide-delimiters
                        :show-arrows="false"
                    >
                        <v-carousel-item :key="org.id" :src="org.logo">
                        </v-carousel-item>
                    </v-carousel>
                </div>
            </v-col>
        </v-row>

        <v-card>
            <v-card-text>
                <p class="description-font-size">
                    {{ org.description }}
                </p>
            </v-card-text>
        </v-card>
    </v-card>
</template>

<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";
import { useOrganizationStore } from "@/stores/organization.js";
import noImage from "@/assets/no-image.png";

const height = ref("300px");
const height2 = ref("300px");

const route = useRoute();
const store = useOrganizationStore();

const org = ref({});
const org2 = ref({});

function loadOrganization() {
    const successCallback = (response) => {
        const logo = response.data.logo
            ? `data:image/${response.data.logo.type};base64,${response.data.logo.content}`
            : noImage;

        org.value = { ...response.data, logo: logo };
        org2.value = {
            ...org.value,
            logo: { n: "n" },
            description: "Very Long Description",
        };
    };
    store.fetchOrganization(route.params.id, successCallback);
}
loadOrganization();
</script>

<style scoped>
.redirect-button {
    text-transform: none !important;
    padding: 0%;
    margin-bottom: 2%;
}

/* .profile-image */
.logo {
    width: 50%;
}

@media (max-width: 600px) {
    .logo {
        width: 25%;
    }
}

.data-font-size {
    font-size: 1.4em;
}

.description-font-size {
    font-size: 1.2em;
}
</style>
