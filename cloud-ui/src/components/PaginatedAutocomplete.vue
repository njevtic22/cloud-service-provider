<template>
    <v-autocomplete
        v-model="selected"
        @update:search="emitSearch"
        :items="items"
        :loading="loading"
        :item-title="itemTitle"
        :item-value="itemValue"
        :return-object="returnObject"
        :label="label"
        :menu-props="{ class: menuClass }"
        :rules="rules"
        ref="input"
        clearable
        no-filter
    >
        <template v-slot:append-item>
            <template v-if="Boolean(items.length)">
                <div v-if="!loading" v-intersect="handleIntersection"></div>

                <v-list-item v-else class="text-primary">
                    Loading more...
                </v-list-item>
            </template>
        </template>

        <template v-slot:loader>
            <v-progress-linear
                color="primary"
                :indeterminate="loading"
            ></v-progress-linear>
        </template>
    </v-autocomplete>
</template>

<script setup>
import { ref, computed } from "vue";
import debounce from "lodash/debounce";

defineProps({
    rules: {
        type: Array,
        required: false,
    },
    label: {
        type: String,
        required: false,
    },
    items: {
        type: Array,
        required: true,
    },
    loading: {
        type: Boolean,
        default: false,
    },
    "item-title": {
        type: String,
        default: "title",
    },
    "item-value": {
        type: String,
        default: "value",
    },
    "return-object": {
        type: Boolean,
        default: false,
    },
});

const emit = defineEmits(["end-reached", "update:search-input"]);

const selected = defineModel();

function handleIntersection(isIntersecting) {
    if (isIntersecting) {
        emit("end-reached");
    }
}

const debouncedEmit = debounce((search) => {
    emit("update:search-input", search);

    resetDropdownScroll();
}, 500);

function emitSearch(search) {
    debouncedEmit(search);
}

const menuClass = "menu-1";

function resetDropdownScroll() {
    const menuWrapper = document.getElementsByClassName(menuClass)[0];
    const menuList = menuWrapper?.firstElementChild?.firstElementChild;

    if (menuList) {
        menuList.scrollTop = 0;
    }
}

const input = ref(null);

async function validate() {
    return await input.value.validate();
}

async function reset() {
    await input.value.reset();
}

async function resetValidation() {
    await input.value.resetValidation();
}

const isValid = computed(() => {
    return input.value.isValid;
});

defineExpose({
    isValid,
    validate,
    reset,
    resetValidation,
});
</script>

<style scoped></style>
