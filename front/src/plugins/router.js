import Vue from 'vue'
import Router from 'vue-router'
import TaskCalendar from '../components/TaskCalendar.vue'
import Login from '../components/LoginPage.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/home',
      name: 'TaskCalendar',
      component: TaskCalendar
    },
    {
      path: '*',
      name: 'Login',
      component: Login
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    }
  ]
})
