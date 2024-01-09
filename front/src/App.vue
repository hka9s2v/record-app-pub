<template>
  <v-app>
    <v-app-bar
      app
      color="primary"
      dark
    >
      <div class="d-flex align-center">
        <span class="mr-2">ToDoApp</span>
      </div>
      <v-spacer></v-spacer>
        <div v-show="isExistToken" @click="logout">
          <img src="@/assets/logout.png"/>
        </div>
    </v-app-bar>

    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>
<style>
  .mr-2{
    font-size: 22px
  }
</style>
<script>
export default {
  name: 'App',
  computed: {
    isExistToken() {
      return this.$store.getters.getToken
    }
  },
  methods: {
    logout() {
      this.$store.dispatch('removeToken')
      this.$router.push('/login')
    }
  }
};
</script>
