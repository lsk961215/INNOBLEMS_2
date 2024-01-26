import { createRouter, createWebHistory } from "vue-router";
import mainPage from '../views/mainPage.vue'
import testPage from '../views/testPage.vue'
import userList from '../views/userList.vue'
import projectList from '../views/projectList.vue'
import codeList from '../views/codeList.vue'
import addUser from '../views/addUser.vue'
import userDetail from '../views/userDetail.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: mainPage},
        { path: '/test', component: testPage},
        { path: '/userList', component: userList},
        { path: '/projectList', component: projectList},
        { path: '/codeList', component: codeList},
        { path: '/goAddUser', component: addUser},
        { path: '/userDetail', component: userDetail},
    ],
})

export default router;
