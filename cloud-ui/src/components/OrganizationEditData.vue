<template>
    <v-form ref="form">
        <v-row>
            <v-col>
                <v-text-field
                    v-model="data.name"
                    :rules="[required]"
                    label="Name"
                ></v-text-field>
            </v-col>
        </v-row>
        <v-row>
            <v-col>
                <v-textarea
                    v-model="data.description"
                    :rules="[required]"
                    label="Description"
                    maxlength="1000"
                    counter
                >
                </v-textarea>
            </v-col>
        </v-row>

        <div class="text-right">
            <v-btn @click="submit" variant="elevated" color="primary">
                Save
            </v-btn>
            <v-btn
                @click="cancel"
                variant="outlined"
                color="primary"
                class="ml-2"
            >
                Cancel
            </v-btn>
        </div>
    </v-form>
</template>

<script setup>
import { ref } from "vue";

const emit = defineEmits(["submit", "cancel"]);

const form = ref(null);
const required = (value) => Boolean(value) || "Required";
const data = ref({});

function init(data2) {
    data.value = data2;
}

function submit() {
    console.log("Submitted");
    emit("submit");
}

function cancel() {
    console.log("Cancelled");
    emit("cancel");
}

defineExpose({
    init,
});
</script>

<style scoped></style>
