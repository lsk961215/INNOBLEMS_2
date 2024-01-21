import { createRouter, createWebHistory } from "vue-router";
import mainPage from '../views/mainPage.vue'
import testPage from '../views/testPage.vue'
import userList from '../views/userList.vue'
import codeList from '../views/codeList.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: mainPage},
        { path: '/test', component: testPage},
        { path: '/userList', component: userList},
        { path: '/codeList', component: codeList},
    ],
})

export default router;
