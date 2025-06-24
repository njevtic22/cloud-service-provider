<template>
    <div v-for="rule in rules" :key="rule.text">
        {{ rule.text }}
        <v-icon :icon="rule.icon" :color="rule.color"></v-icon>
    </div>
</template>

<script setup>
import { ref, defineModel, watch } from "vue";
import specialCharsRegex from "@/util/validator/special-characters";

const password = defineModel();
const emit = defineEmits(["progress-changed", "password-valid-changed"]);

const regs = {
    upperCase: /.*[A-Z].*/,
    lowerCase: /.*[a-z].*/,
    digit: /.*[0-9].*/,
    special: specialCharsRegex,
    noWhitespace: /^\S+$/,
};

// Validation of illegal sequences and blacklist can be found in musical instrument shop
const rules = ref([
    {
        text: "Maximum 50 characters long",
        icon: getIcon(false),
        color: getColor(false),
        validate(password) {
            const valid = password?.length <= 50;
            this.icon = getIcon(valid);
            this.color = getColor(valid);
            return valid;
        },
    },
    {
        text: "Minimum 8 characters long",
        icon: getIcon(false),
        color: getColor(false),
        validate(password) {
            const valid = password?.length >= 8;
            this.icon = getIcon(valid);
            this.color = getColor(valid);
            return valid;
        },
    },
    {
        text: "At least one uppercase character",
        icon: getIcon(false),
        color: getColor(false),
        validate(password) {
            const valid = regs.upperCase.test(password);
            this.icon = getIcon(valid);
            this.color = getColor(valid);
            return valid;
        },
    },
    {
        text: "At least one lowercase character",
        icon: getIcon(false),
        color: getColor(false),
        validate(password) {
            const valid = regs.lowerCase.test(password);
            this.icon = getIcon(valid);
            this.color = getColor(valid);
            return valid;
        },
    },
    {
        text: "At least one digit character",
        icon: getIcon(false),
        color: getColor(false),
        validate(password) {
            const valid = regs.digit.test(password);
            this.icon = getIcon(valid);
            this.color = getColor(valid);
            return valid;
        },
    },
    {
        text: "At least one special character",
        icon: getIcon(false),
        color: getColor(false),
        validate(password) {
            const valid = regs.special.test(password);
            this.icon = getIcon(valid);
            this.color = getColor(valid);
            return valid;
        },
    },
    {
        text: "No whitespace",
        icon: getIcon(false),
        color: getColor(false),
        validate(password) {
            const valid = regs.noWhitespace.test(password);
            this.icon = getIcon(valid);
            this.color = getColor(valid);
            return valid;
        },
    },
]);

let passwordValid = false;

watch(
    () => password.value,
    (newValue) => {
        let fulfilled = 0;
        for (let i = 0; i < rules.value.length; i++) {
            const rule = rules.value[i];
            const valid = rule.validate(newValue);
            fulfilled += Number(valid);
        }

        let required = Object.keys(rules.value).length;
        const fulfilledPercent = (fulfilled / required) * 100;
        emit("progress-changed", fulfilledPercent);

        let tmpValid = fulfilledPercent === 100;
        if (passwordValid !== tmpValid) {
            passwordValid = tmpValid;
            emit("password-valid-changed", passwordValid);
        }
    }
);

function getIcon(isValid) {
    return isValid ? "mdi-check-bold" : "mdi-close-thick";
}

function getColor(isValid) {
    return isValid ? "green-darken-1" : "red-darken-1";
}
</script>

<style scoped></style>
