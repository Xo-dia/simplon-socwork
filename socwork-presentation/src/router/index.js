import NotFoundView from "@/views/NotFoundView.vue";
import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes: [
		{
			path: "/",
			name: "account",
			component: () => import("../views/AccountCreateView.vue"),
		},
		{
			path: "/creer-compte",
			name: "account-create",
			component: () => import("../views/AccountCreateView.vue"),
		},
		{ path: "/:pathMatch(.*)*", name: "NotFound", component: NotFoundView },
	],
});

export default router;
