import specialCharsRegex from "@/util/validator/special-characters";

const regs = {
    maxLength50: {
        test(password) {
            return password.length <= 50;
        },
    },
    minLength8: {
        test(password) {
            return password.length >= 8;
        },
    },
    upperCase: /.*[A-Z].*/,
    lowerCase: /.*[a-z].*/,
    digit: /.*[0-9].*/,
    special: specialCharsRegex,
    noWhitespace: /^\S+$/,
};

const Rule = Object.freeze({
    MAX_LENGTH_50: "maxLength50",
    MIN_LENGTH_8: "minLength8",
    UPPER_CASE: "upperCase",
    LOWER_CASE: "lowerCase",
    DIGIT: "digit",
    SPECIAL: "special",
    NO_WHITESPACE: "noWhitespace",
});

function validate(password, rule) {
    if (!password) {
        return false;
    }

    return regs[rule].test(password);
}

export { Rule, validate };
