import { createRouter, createWebHistory } from "vue-router";
import mainPage from '../views/mainPage.vue'
import userList from '../views/userList.vue'
import projectList from '../views/projectList.vue'
import codeList from '../views/codeList.vue'
import addUser from '../views/addUser.vue'
import addProject from '../views/addProject.vue'
import userDetail from '../views/userDetail.vue'
import projectDetail from '../views/projectDetail.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: mainPage},
        { path: '/userList', component: userList},
        { path: '/projectList', component: projectList},
        { path: '/codeList', component: codeList},
        { path: '/addUser', component: addUser},
        { path: '/userDetail', component: userDetail},
        { path: '/projectDetail', component: projectDetail},
        { path: '/addProject', component: addProject},
    ],
})

export default router;
