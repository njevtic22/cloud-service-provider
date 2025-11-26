<template>
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
            ></activities-table>
        </v-col>
    </v-row>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRoute } from "vue-router";
import { useDisplay } from "vuetify";
import { useMachineStore } from "@/stores/machine.js";

const route = useRoute();
const store = useMachineStore();
const machine = ref(null);

function loadMachine() {
    store.fetch(route.params.id, (response) => (machine.value = response.data));
}
loadMachine();

function toggle() {
    store.toggle(
        machine.value.id,
        (response) => (machine.value = response.data)
    );
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
