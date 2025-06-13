/**
 * main.js
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */

// Plugins
import { registerPlugins } from "@/plugins";

// Components
import App from "./App.vue";

// Composables
import { createApp, inject } from "vue";
import { createPinia } from "pinia";

// Styles
import "unfonts.css";

const app = createApp(App);
const pinia = createPinia();
pinia.use(() => ({
    showErrorSnack: inject("showErrorSnack"),
}));

registerPlugins(app);

app.use(pinia);
app.mount("#app");
