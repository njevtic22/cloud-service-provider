<template>
    <v-card :append-icon="icon" :title="title">
        <v-card-text>
            <v-form ref="form">
                <v-row>
                    <v-col cols="12" sm="6">
                        <v-text-field
                            v-model="user.name"
                            :rules="[rules.required]"
                            label="Name"
                        ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6">
                        <v-text-field
                            v-model="user.surname"
                            :rules="[rules.required]"
                            label="Surname"
                        ></v-text-field>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="12" sm="6">
                        <v-text-field
                            v-model="user.email"
                            :rules="[rules.required, rules.email]"
                            label="Email"
                        ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6">
                        <v-text-field
                            v-model="user.username"
                            :rules="[rules.required]"
                            label="Username"
                        ></v-text-field>
                    </v-col>
                </v-row>
                <v-row v-show="user.role !== 'ROLE_SUPER_ADMIN'">
                    <v-col cols="12" sm="6">
                        <fetching-autocomplete
                            v-show="authStore.isSuperAdmin"
                            v-model="user.organization"
                            :items="store.organizations"
                            :return-object="true"
                            :rules="[rules.required]"
                            :fetch="store.fetchOrganizations"
                            :append="store.appendOrganizations"
                            :reset="clearOrganizations"
                            ref="organizationRef"
                            compare-property="name"
                            label="Organization"
                            item-title="name"
                        ></fetching-autocomplete>
                        <v-text-field
                            v-if="authStore.isAdmin"
                            v-model="profileStore.profile.organizationName"
                            label="Organization"
                            disabled
                        ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6">
                        <v-select
                            v-model="user.role"
                            :rules="[rules.required]"
                            :items="['ROLE_ADMIN', 'ROLE_USER']"
                            label="Role"
                            clearable
                        ></v-select>
                    </v-col>
                </v-row>
            </v-form>
        </v-card-text>

        <v-card-actions>
            <v-btn
                :disabled="!form?.isValid"
                @click="submit"
                color="primary"
                variant="elevated"
            >
                {{ submitText }}
            </v-btn>
            <v-btn @click="cancel" color="primary" variant="outlined">
                {{ cancelText }}
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script setup>
import { ref, computed } from "vue";
import { useOrganizationStore } from "@/stores/organization.js";
import { useProfileStore } from "@/stores/profile.js";
import { useAuthStore } from "@/stores/auth.js";

const store = useOrganizationStore();
function clearOrganizations() {
    store.$reset();
}

const profileStore = useProfileStore();
const authStore = useAuthStore();
const profileOrg = computed(() => profileStore.profile.organization);
function initOrganization() {
    if (authStore.isAdmin) {
        profileStore.fetchProfile();
        user.value.organization = { id: profileOrg };
    }
}

defineProps({
    icon: String,
    title: String,
    "submit-text": String,
    "cancel-text": {
        type: String,
        default: "Cancel",
    },
});

const emit = defineEmits(["submit", "cancel"]);

const form = ref(null);
const organizationRef = ref(null);
const user = ref({
    name: "",
    surname: "",
    email: "",
    username: "",
    organization: null,
    role: "",
});
initOrganization();

const rules = {
    required: (value) => Boolean(value) || "Required",
    email: (email) =>
        /.+@.+\..+/.test(email) || "Email must be valid email adress",
};

function init(data) {
    user.value = data;
}

async function validateForm() {
    const { valid } = await form.value.validate();
    const organizationValid =
        (await organizationRef.value.validate()).length === 0;
    return valid && organizationValid;
}

function resetForm() {
    form.value.reset();
    organizationRef.value.reset();
}

async function submit() {
    const valid = await validateForm();
    if (!valid) {
        return;
    }

    emit("submit", { ...user.value });
}

function cancel() {
    emit("cancel");
    // Commented out so that form does not have additional fields
    // for a split second when dialog closes
    // resetForm();
}

defineExpose({
    init,
    resetForm,
});
</script>

<style scoped></style>
