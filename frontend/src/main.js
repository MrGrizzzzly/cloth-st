
export const ROOT_URL = 'http://localhost:8080/api';

import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from "@/js/pages.js";

let app = createApp(App)

app.use(router).mount('#app')


