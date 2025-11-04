<template>
    <div v-for="rule in rules" :key="rule.text">
        {{ rule.text }}
        <v-icon :icon="rule.icon" :color="rule.color"></v-icon>
    </div>
</template>

<script setup>
import { ref, watch } from "vue";
import { Rule, validate } from "@/util/validator/password-validator.js";

const password = defineModel();
const emit = defineEmits(["progress-changed", "password-valid-changed"]);

// Validation of illegal sequences and blacklist can be found in musical instrument shop
const rules = ref([
    {
        text: "Maximum 50 characters long",
        icon: getIcon(false),
        color: getColor(false),
        validate(password) {
            return validateWithIconColor(this, password, Rule.MAX_LENGTH_50);
        },
    },
    {
        text: "Minimum 8 characters long",
        icon: getIcon(false),
        color: getColor(false),
        validate(password) {
            return validateWithIconColor(this, password, Rule.MIN_LENGTH_8);
        },
    },
    {
        text: "At least one uppercase character",
        icon: getIcon(false),
        color: getColor(false),
        validate(password) {
            return validateWithIconColor(this, password, Rule.UPPER_CASE);
        },
    },
    {
        text: "At least one lowercase character",
        icon: getIcon(false),
        color: getColor(false),
        validate(password) {
            return validateWithIconColor(this, password, Rule.LOWER_CASE);
        },
    },
    {
        text: "At least one digit character",
        icon: getIcon(false),
        color: getColor(false),
        validate(password) {
            return validateWithIconColor(this, password, Rule.DIGIT);
        },
    },
    {
        text: "At least one special character",
        icon: getIcon(false),
        color: getColor(false),
        validate(password) {
            return validateWithIconColor(this, password, Rule.SPECIAL);
        },
    },
    {
        text: "No whitespace",
        icon: getIcon(false),
        color: getColor(false),
        validate(password) {
            return validateWithIconColor(this, password, Rule.NO_WHITESPACE);
        },
    },
]);

function validateWithIconColor(refRule, password, passwordRule) {
    const valid = validate(password, passwordRule);
    refRule.icon = getIcon(valid);
    refRule.color = getColor(valid);
    return valid;
}

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
