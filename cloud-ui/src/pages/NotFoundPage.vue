<template>
    <div>
        <v-row align="stretch" class="justify-center">
            <!-- In future, try to make image responsive with CSS selector @media linke in ProfileData.vue -->
            <v-col cols="8" xs="8" sm="5" md="4" lg="4" xl="4" xxl="4">
                <v-img
                    src="@/assets/not-found.png"
                    error="Even image is not found"
                />
            </v-col>
        </v-row>
        <v-row align="stretch" class="justify-center">
            <v-col
                cols="12"
                xs="8"
                sm="8"
                md="4"
                lg="4"
                xl="4"
                xxl="4"
                class="d-flex justify-center"
            >
                <h1 class="not-found-text">Resource not found</h1>
            </v-col>
        </v-row>
    </div>
</template>

<script setup></script>

<style scoped>
.not-found-text {
    color: #b71c1c;
}
</style>
