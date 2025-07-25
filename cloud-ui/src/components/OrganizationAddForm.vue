<template>
    <v-stepper flat v-model="stepper.step">
        <v-stepper-header>
            <v-stepper-item
                :value="1"
                :complete="stepper.completedFirst"
                title="Data"
            ></v-stepper-item>
            <v-divider></v-divider>
            <v-stepper-item :value="2" title="Image"></v-stepper-item>
        </v-stepper-header>

        <v-stepper-window>
            <v-stepper-window-item :key="1" :value="1">
                <v-form ref="dataForm">
                    <v-row>
                        <v-col>
                            <v-text-field
                                v-model="data.name"
                                :rules="[rules.required]"
                                label="Name"
                            ></v-text-field>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col>
                            <v-textarea
                                v-model="data.description"
                                :rules="[rules.required]"
                                label="Description"
                                maxlength="1000"
                                counter
                            >
                            </v-textarea>
                        </v-col>
                    </v-row>
                </v-form>
            </v-stepper-window-item>
            <v-stepper-window-item :key="2" :value="2">
                <v-form ref="imageForm">
                    <v-row>
                        <v-col>
                            <v-file-input
                                v-model="image"
                                @click:clear="fileInput.blur()"
                                :rules="[rules.required, rules.imageType]"
                                color="primary"
                                label="Upload image"
                                accept="image/jpeg, image/jpg, image/png"
                                clearable
                                ref="fileInput"
                            >
                                <!-- fileInput.blur() is not necessary in musical instrument shop. So why is it necessary here? -->
                                <!-- Multiple file upload example is in musical instrument shop -->
                            </v-file-input>
                        </v-col>
                    </v-row>
                    <v-row v-if="image" class="pa-2 d-flex justify-center">
                        <v-col cols="12" sm="6" md="4" lg="3">
                            <v-img :src="imagePreview"></v-img>
                        </v-col>
                    </v-row>
                </v-form>
            </v-stepper-window-item>
        </v-stepper-window>

        <v-stepper-actions>
            <template #prev> <v-spacer></v-spacer> </template>
            <template #next>
                <v-btn
                    @click="submit"
                    :disabled="isNextActive"
                    variant="elevated"
                    color="primary"
                >
                    {{ buttons.next }}
                </v-btn>
                <v-btn
                    @click="close"
                    :disabled="false"
                    variant="outlined"
                    color="primary"
                    class="ml-2"
                >
                    {{ buttons.cancel }}
                </v-btn>
            </template>
        </v-stepper-actions>
    </v-stepper>
</template>

<script setup>
import { ref, computed } from "vue";

const emit = defineEmits(["submit-data", "submit-image", "cancel"]);

const dataForm = ref(null);
const imageForm = ref(null);
const fileInput = ref(null);

const stepper = ref({
    step: 1,
    completedFirst: false,
});
// What if there are 2 subcomponents as forms?
const data = ref({
    name: "",
    description: "",
});
const image = ref(null);

const rules = {
    required: (value) => Boolean(value) || "Required",
    imageType: (image) => {
        const type = image.type;
        return (
            type === "image/png" ||
            type === "image/jpg" ||
            type === "image/jpeg" ||
            "Allowed image types are: jpeg, jpg, png"
        );
    },
};

const buttons = ref({
    next: "Save and next",
    cancel: "Cancel",
});

function submit() {
    if (stepper.value.step === 1) {
        emit("submit-data", data.value);

        stepper.value.step++;
        stepper.value.completedFirst = true;
        buttons.value.next = "Save and close";
        buttons.value.cancel = "Skip";
        return;
    }

    emit("submit-image", image.value);
}

function close() {
    emit("cancel", stepper.value.completedFirst);

    dataForm.value.reset();
    imageForm.value?.reset();
    // stepper.value.step = 1;
    buttons.value.next = "Save and next";
    buttons.value.cancel = "Cancel";
}

const isNextActive = computed(() => {
    const form = stepper.value.step === 1 ? dataForm.value : imageForm.value;
    return !form?.isValid;
});

const imagePreview = computed(() => {
    return image.value ? URL.createObjectURL(image.value) : null;
});
</script>

<style scoped></style>
