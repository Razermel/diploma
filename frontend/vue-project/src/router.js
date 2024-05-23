import { createRouter, createWebHistory } from 'vue-router'
import Login from './components/Login.vue'
import Main from './components/Main.vue'
import Invoice from './components/Invoice.vue'
import CreateInvoice from './components/CreateInvoice.vue';
import ListInvoice from './components/ListInvoice.vue';

const routes = [
  { path: '/', component: Login },
  { path: '/invoice/:id/html', component: Invoice},
  { path: '/create', component: CreateInvoice},
  { path: '/main', component: Main },
  { path: '/invoices', component: ListInvoice },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
