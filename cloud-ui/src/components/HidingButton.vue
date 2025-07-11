<template>
    <v-fab
        @click="emit('click')"
        :icon="icon"
        :text="text"
        :app="true"
        :active="active"
        color="primary"
        size="large"
        elevation="16"
        transition="slide-y-transition"
    ></v-fab>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import debounce from "lodash/debounce";

const props = defineProps({
    icon: {
        type: String,
        required: false,
    },
    text: {
        type: String,
        default: "",
    },
    frozen: {
        type: Boolean,
        default: false,
    },
});

const emit = defineEmits(["click"]);

const active = ref(true);
const debouncedScroll = debounce((event) => {
    handleScroll(event);
}, 55);

let lastScroll = 0;
function handleScroll(event) {
    if (props.frozen) {
        return;
    }

    const currentScroll = window.scrollY;
    const scrollDelta = currentScroll - lastScroll;

    if (scrollDelta > 0) {
        // scroll down
        if (currentScroll >= 300) {
            active.value = false;
        }
    } else if (scrollDelta < 0) {
        // scroll up
        active.value = true;
    }

    lastScroll = currentScroll;
}

onMounted(() => {
    window.addEventListener("scroll", debouncedScroll);
});
onUnmounted(() => {
    window.removeEventListener("scroll", debouncedScroll);
});
</script>

<style scoped></style>
