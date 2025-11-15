import { computed } from "vue";

function filterHeaders(headers, filter) {
    return computed(() => {
        return headers.filter(filter);
    });
}

function filterShowHeaders(headers) {
    return filterHeaders(headers, (h) => h.show?.() ?? true);
}

export { filterHeaders, filterShowHeaders };
