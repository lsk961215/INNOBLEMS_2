import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router/index.js'
import axios from 'axios';
import { VueDaumPostcode } from 'vue-daum-postcode';
import "v-calendar/dist/style.css";
import VCalendar from "v-calendar";

const app = createApp(App)
app.config.globalProperties.$axios = axios
app.config.globalProperties.$codeList = await axios.get('http://localhost:8080/getCodeList');
app.config.globalProperties.$grCDs = function () {
    const arr = this.$codeList.data
    const grCDs = arr.filter(item => item.mstCD == "GR01")
    return grCDs
}
app.use(router)
app.use(createPinia())
app.use(VueDaumPostcode)
app.use(VCalendar, {})
app.mount('#app')
 
