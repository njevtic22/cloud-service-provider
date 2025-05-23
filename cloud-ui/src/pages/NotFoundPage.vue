<template>
    <div>
        <v-row align="stretch" class="justify-center">
            <v-col cols="4">
                <v-img
                    src="@/assets/not-found.png"
                    error="Even image is not found"
                />
            </v-col>
        </v-row>
        <v-row align="stretch" class="justify-center">
            <v-col cols="5" class="d-flex justify-center">
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
