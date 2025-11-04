<template>
    <v-form ref="form">
        <v-row class="pa-2 d-flex justify-center">
            <v-col cols="12" sm="6">
                <v-img :src="logoPreview"></v-img>
            </v-col>
        </v-row>
        <v-row class="d-flex justify-center">
            <v-col cols="12" sm="6">
                <!-- @click:clear does not fix opening file select dialoge and then pressing cancel -->
                <v-file-input
                    v-model="logo"
                    :rules="[rules.required, rules.imageType]"
                    :loading="loading"
                    @click:clear="form.reset()"
                    height="7"
                    color="primary"
                    label="Upload logo"
                    accept="image/jpeg, image/jpg, image/png"
                ></v-file-input>
            </v-col>
        </v-row>
        <v-row class="d-flex justify-center">
            <v-btn :disabled="isUploadDisabled" @click="upload" color="primary">
                Upload
            </v-btn>
        </v-row>
        <br />
        <v-divider></v-divider>
        <br />
        <v-row class="d-flex justify-center">
            <v-btn
                :disabled="
                    prevLogo.logo?.includes('no-image.png') || delLoading
                "
                :loading="delLoading"
                @click="deleteLogo"
                color="error"
            >
                Delete
            </v-btn>
        </v-row>
    </v-form>
</template>

<script setup>
import { ref, computed, inject } from "vue";
import { useOrganizationStore } from "@/stores/organization.js";

const snackbar = inject("snackbar");
const emit = defineEmits(["submit"]);
const store = useOrganizationStore();

const form = ref(null);
const loading = ref(false);
const delLoading = ref(false);
const logo = ref(null);
const prevLogo = ref("");

const rules = {
    required: (image) => {
        return Boolean(image);
    },

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

function upload() {
    loading.value = true;
    store.uploadImage(logo.value, prevLogo.value.orgId, () => {
        loading.value = false;
        emit("submit");
        snackbar("Logo uploaded", 3000);
    });
}

function deleteLogo() {
    delLoading.value = true;
    store.deleteImage(prevLogo.value.orgId, () => {
        delLoading.value = false;
        emit("submit");
        snackbar("Logo deleted", 3000);
    });
}

const logoPreview = computed(() => {
    return logo.value ? URL.createObjectURL(logo.value) : prevLogo.value.logo;
});

const isUploadDisabled = computed(() => {
    return !form.value?.isValid || loading.value;
});

function init(prevLogo2) {
    prevLogo.value = prevLogo2;
}

defineExpose({
    init,
});
</script>

<style scoped></style>
