function parseEpochMillis(date) {
    return new Date(date);
}

function formatLocale(date, locale) {
    return parseEpochMillis(date).toLocaleString(locale);
}

export { parseEpochMillis, formatLocale };
