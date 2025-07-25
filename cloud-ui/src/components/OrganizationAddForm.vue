<template>
    <v-stepper flat v-model="stepper.step">
        <v-stepper-header>
            <v-stepper-item
                title="Data"
                :value="1"
                :complete="stepper.completedFirst"
            ></v-stepper-item>
            <v-divider></v-divider>
            <v-stepper-item title="Image" :value="2"></v-stepper-item>
        </v-stepper-header>

        <v-stepper-window>
            <v-stepper-window-item :key="1" :value="1">
                <v-form> Data form </v-form>
            </v-stepper-window-item>
            <v-stepper-window-item :key="2" :value="2">
                <v-form> Image form </v-form>
            </v-stepper-window-item>
        </v-stepper-window>

        <v-stepper-actions>
            <template #prev> <v-spacer></v-spacer> </template>
            <template #next>
                <v-btn
                    @click="
                        stepper.step++;
                        stepper.completedFirst = true;
                    "
                    variant="elevated"
                    color="primary"
                >
                    Next
                </v-btn>
            </template>
        </v-stepper-actions>
    </v-stepper>
</template>

<script setup>
import { ref } from "vue";

const stepper = ref({
    step: 1,
    completedFirst: false,
});
</script>

<style scoped></style>
