import { createRouter, createWebHashHistory } from "vue-router";

import Main from "@/pages/Main.vue";
import Notes from "@/pages/Notes.vue";
import Log from "@/pages/Log.vue";
import Reg from "@/pages/Reg.vue";
import ErrorRight from "@/pages/Error.vue";
import UserShoppingCart from "@/pages/UserShoppingCart.vue";

const rages = [
    { path: '/', component: Main},
    { path: '/login', component: Log},
    { path: '/register', component: Reg},
    { path: '/error', component: ErrorRight},
    { path: '/EditNotes', component: Notes},
    { path: '/UserShoppingCart', component: UserShoppingCart},
];

export default createRouter({
    history: createWebHashHistory(),
    routes: rages,
});