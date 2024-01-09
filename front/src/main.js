import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from './plugins/router'
import store from './store/index'

Vue.config.productionTip = false
console.log(process.env)

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
