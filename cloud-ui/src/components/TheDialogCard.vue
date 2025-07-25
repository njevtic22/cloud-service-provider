<template>
    <v-card :append-icon="icon" :title="title">
        <v-card-text v-if="$slots.default">
            <slot name="default"></slot>
        </v-card-text>

        <v-card-actions v-if="!actionsDisabled">
            <slot name="card-actions">
                <v-btn
                    :disabled="submitDisabled"
                    @click="emit('submit')"
                    color="primary"
                    variant="elevated"
                >
                    {{ submitText }}
                </v-btn>
                <v-btn
                    @click="emit('cancel')"
                    color="primary"
                    variant="outlined"
                >
                    {{ cancelText }}
                </v-btn>
            </slot>
        </v-card-actions>
    </v-card>
</template>

<script setup>
const emit = defineEmits(["submit", "cancel"]);

defineProps({
    icon: String,
    title: String,
    submitText: String,
    cancelText: {
        type: String,
        default: "Cancel",
    },
    submitDisabled: {
        type: Boolean,
        default: false,
    },
    actionsDisabled: {
        type: Boolean,
        default: false,
    },
});
</script>

<style scoped></style>
