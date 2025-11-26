<template>
    <confirmation-dialog ref="confirm"></confirmation-dialog>

    <v-card v-if="machine" elevation="4" class="machine-data" :width="width">
        <v-card-title class="d-flex justify-center mb-2">
            {{ machine.name }}
        </v-card-title>

        <v-card-text>
            <v-row>
                <v-col class="text-right"> Organization </v-col>
                <v-col>
                    {{ machine.organization }}
                </v-col>
            </v-row>
            <v-row>
                <v-col class="text-right"> Category </v-col>
                <v-col>
                    {{ machine.category }}
                </v-col>
            </v-row>
            <v-row>
                <v-col class="text-right"> CPU cores </v-col>
                <v-col>
                    {{ machine.cpu }}
                </v-col>
            </v-row>
            <v-row>
                <v-col class="text-right"> RAM capacity </v-col>
                <v-col>
                    {{ machine.ram }}
                </v-col>
            </v-row>
            <v-row>
                <v-col class="text-right"> GPU cores </v-col>
                <v-col>
                    {{ machine.gpu }}
                </v-col>
            </v-row>
            <V-row>
                <v-col class="d-flex justify-center">
                    <the-switch
                        v-model="machine.active"
                        :label="machine.active ? 'Turned on' : 'Turned off'"
                        @click="toggle"
                        inset
                    ></the-switch>
                </v-col>
            </V-row>

            <div v-if="!machine.active">
                <br />
                <v-divider></v-divider>
                <br />
                <v-row class="d-flex justify-center">
                    <v-btn
                        @click="openConfirmDialog"
                        prepend-icon="mdi-delete"
                        color="error"
                    >
                        Delete
                    </v-btn>
                </v-row>
            </div>
        </v-card-text>
    </v-card>

    <v-row v-if="machine" class="d-flex justify-space-between mt-5">
        <v-col cols="12" sm="6">
            <h3>Connected drives</h3>
            <drives-table
                :initial-size="5"
                :constant-filter="{ 'machine-id': machine.id }"
            ></drives-table>
        </v-col>

        <v-col cols="12" sm="6">
            <h3>Activities</h3>
            <activities-table
                :initial-size="5"
                :initial-sort="[{ key: 'turnedOff', order: 'desc' }]"
                :constant-filter="{ 'machine-id': machine.id }"
                ref="actRef"
            ></activities-table>
        </v-col>
    </v-row>
</template>

<script setup>
import { ref, computed, inject } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useDisplay } from "vuetify";
import { useMachineStore } from "@/stores/machine.js";

const route = useRoute();
const router = useRouter();

const snackbar = inject("snackbar");

const store = useMachineStore();
const machine = ref(null);

const actRef = ref(null);
const confirm = ref(null);

function loadMachine() {
    store.fetch(route.params.id, (response) => (machine.value = response.data));
}
loadMachine();

function toggle() {
    store.toggle(machine.value.id, (response) => {
        machine.value = response.data;
        actRef.value.loadActivities();
    });
}

async function openConfirmDialog() {
    const confirmed = await confirm.value.open(
        "Are you sure you want to permanently delete virtual machine with name: " +
            machine.value.name +
            "?",
        "Delete Virtual Machine"
    );

    if (!confirmed) {
        return;
    }

    store.delete(machine.value.id, () => {
        router.push("/");
        snackbar("Machine deleted", 3000);
    });
}

const display = useDisplay();
const width = computed(() => {
    if (display.xs.value) {
        return "100%";
    } else if (display.smAndDown.value) {
        return "65%";
    }
    return "40%";
});
</script>

<style scoped>
.machine-data {
    margin: auto;
    padding: 2%;
}
</style>
