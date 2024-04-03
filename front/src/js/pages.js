import { createRouter, createWebHashHistory } from "vue-router";

import Notes from "@/pages/Notes.vue";
import Log from "@/pages/Log.vue";
import Reg from "@/pages/Reg.vue";


const rages = [
    { path: '/', component: Notes},
    { path: '/login', component: Log},
    { path: '/register', component: Reg},
];

export default createRouter({
    history: createWebHashHistory(),
    routes: rages,
});