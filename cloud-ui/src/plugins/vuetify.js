/**
 * plugins/vuetify.js
 *
 * Framework documentation: https://vuetifyjs.com`
 */

// Styles
import "@mdi/font/css/materialdesignicons.css";
import "vuetify/styles";

import colors from "vuetify/util/colors";

// Composables
import { createVuetify } from "vuetify";

const customTheme = {
    dark: false,
    colors: {
        background: colors.grey.lighten4, // "#f5f5f5", // whitesmoke
        primary: colors.indigo.darken4, // #1A237E
    },
};

// https://vuetifyjs.com/en/introduction/why-vuetify/#feature-guides
export default createVuetify({
    theme: {
        defaultTheme: "customTheme",
        themes: {
            customTheme,
        },
    },
});
