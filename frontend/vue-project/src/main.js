import './assets/main.css'

import { createApp } from 'vue'
import { autoAnimatePlugin } from '@formkit/auto-animate/vue'
import App from './App.vue'
import router from './router'
import Toast, { POSITION } from "vue-toastification"
import "vue-toastification/dist/index.css"

const options = {
  // Настройки по умолчанию для уведомлений
  position: POSITION.TOP_RIGHT,
  timeout: 5000,
  closeOnClick: true,
}

const app = createApp(App)

app.use(autoAnimatePlugin)
app.use(router)
app.use(Toast, options) 

app.mount('#app')
