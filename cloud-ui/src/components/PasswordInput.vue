<template>
    <v-form>
        <v-text-field
            v-model="password"
            :append-inner-icon="innerIcon"
            @click:append-inner="showPassword = !showPassword"
            :append-icon="icon"
            @click:append="showRules = !showRules"
            :type="showPassword ? 'text' : 'password'"
            :rules="rules"
            :maxlength="50"
            :error="!isPasswordValid && Boolean(password)"
            :label="label"
            ref="fieldRef"
            counter
        >
            <template v-if="enableRules" v-slot:loader>
                <v-progress-linear
                    :model-value="progress.value"
                    :color="progress.color"
                    height="7"
                ></v-progress-linear>
            </template>
        </v-text-field>

        <v-expand-transition v-if="enableRules">
            <div v-show="showRules" class="padded-2">
                <password-strength
                    v-model="password"
                    @progress-changed="updateProgressBar"
                    @password-valid-changed="isPasswordValid = $event"
                ></password-strength>
            </div>
        </v-expand-transition>
    </v-form>
</template>

<script setup>
import { ref, computed, defineExpose } from "vue";

const props = defineProps(["rules", "label"]);

const fieldRef = ref(null);
const isPasswordValid = ref(true);
const progress = ref({
    value: 0,
    color: "red-darken-1",
});

const password = defineModel("password");
const showPassword = defineModel("showPassword", {
    type: Boolean,
    default: undefined,
});
const showRules = defineModel("showRules", {
    type: Boolean,
    default: undefined,
});

const enableRules = computed(() => {
    return showRules.value != undefined;
});
const innerIcon = computed(() => {
    if (showPassword.value == undefined) {
        return undefined;
    }
    return showPassword.value ? "mdi-eye" : "mdi-eye-off";
});
const icon = computed(() => {
    if (showRules.value == undefined) {
        return undefined;
    }
    return showRules.value ? "mdi-chevron-up" : "mdi-chevron-down";
});

function validate() {
    return fieldRef.value.validate();
}

function reset() {
    fieldRef.value.reset();
}

const isValid = computed(() => {
    return fieldRef.value.isValid;
});

defineExpose({
    isValid,
    validate,
    reset,
});

function updateProgressBar(newProgres) {
    progress.value.value = newProgres;
    progress.value.color = getProgressColor(newProgres);
}

function getProgressColor(progress) {
    if (progress <= 25) {
        return "red-darken-1";
    }

    if (progress <= 50) {
        return "orange-darken-1";
    }

    if (progress <= 75) {
        return "yellow-darken-1";
    }

    if (progress < 100) {
        return "light-blue-darken-1";
    }

    return "green-darken-1";
}
</script>

<style scoped></style>
