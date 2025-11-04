/**
 * router/index.ts
 *
 */

// Composables
import { createRouter, createWebHistory } from "vue-router/auto";
import { Role } from "@/stores/auth.js";

const routes = [
    {
        path: "/",
        name: "Virtual Machines",
        component: () => import("@/pages/MachinesPage.vue"),
        meta: {
            requiredRole: [Role.SUPER_ADMIN, Role.ADMIN, Role.USER],
        },
    },
    {
        path: "/login",
        name: "Login",
        component: () => import("@/pages/LoginPage.vue"),
        meta: {
            requiredRole: [Role.ANONYMOUS],
        },
    },
    {
        path: "/organizations",
        name: "Organizations",
        component: () => import("@/pages/OrganizationsPage.vue"),
        meta: {
            requiredRole: [Role.SUPER_ADMIN],
        },
    },
    {
        path: "/organizations/:id",
        name: "Organization",
        component: () => import("@/pages/OrganizationPage.vue"),
        meta: {
            requiredRole: [Role.SUPER_ADMIN],
        },
    },
    {
        path: "/organization",
        name: "OrganizationAdmin",
        component: () => import("@/pages/OrganizationPage.vue"),
        meta: {
            requiredRole: [Role.ADMIN],
        },
    },
    {
        path: "/profile",
        name: "Profile",
        component: () => import("@/pages/ProfilePage.vue"),
        meta: {
            requiredRole: [Role.SUPER_ADMIN, Role.ADMIN, Role.USER],
        },
    },
    {
        path: "/users",
        name: "Users",
        component: () => import("@/pages/UsersPage.vue"),
        meta: {
            requiredRole: [Role.SUPER_ADMIN, Role.ADMIN],
        },
    },
    {
        path: "/not-found",
        alias: "/:pathMatch(.*)*",
        name: "Not Found",
        component: () => import("@/pages/NotFoundPage.vue"),
    },
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
});

// Workaround for https://github.com/vitejs/vite/issues/11804
router.onError((err, to) => {
    if (
        err?.message?.includes?.("Failed to fetch dynamically imported module")
    ) {
        if (!localStorage.getItem("vuetify:dynamic-reload")) {
            console.log("Reloading page to fix dynamic import error");
            localStorage.setItem("vuetify:dynamic-reload", "true");
            location.assign(to.fullPath);
        } else {
            console.error(
                "Dynamic import error, reloading page did not fix it",
                err
            );
        }
    } else {
        console.error(err);
    }
});

router.isReady().then(() => {
    localStorage.removeItem("vuetify:dynamic-reload");
});

router.beforeEach((to, from, next) => {
    const roles = to.meta.requiredRole;
    if (!roles || roles.length === 0) {
        next();
        return;
    }

    const authorized = roles.some(
        (role) => role === (localStorage.getItem("role") || Role.ANONYMOUS)
    );

    if (authorized) {
        next();
        return;
    }

    next({ path: "/not-found" });
    return;
});

export default router;
