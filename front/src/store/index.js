import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex)

const store = new Vuex.Store({
  // plugins: [createPersistedState()],
  state:{
    token: "",
    loginUserId: "",
  },
  mutations: {
    saveToken (state,token) {
      state.token = token
    },
    removeToken(state){
      state.token = ""
    },
    saveLoginUserId (state, loginUserId) {
      state.loginUserId = loginUserId
    },
    removeLoginUserId(state){
      state.loginUserId = ""
    }
  },
  actions: {
    saveToken({commit},token){
      commit("saveToken",token)
    },
    removeToken({commit}){
      commit("removeToken")
    },
    saveLoginUserId({commit},loginUserId){
      commit("saveLoginUserId",loginUserId)
    },
    removeLoginUserId({commit}){
      commit("removeLoginUserId")
    }
  },
  getters: {
    getToken(state){
      return state.token
    }
  }
})

export default store
