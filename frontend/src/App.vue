<script>
import Config_auth from "@/js/config_auth.js";
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

export default {
  data() {
    return {
      auth: new Config_auth()
    }
  },
  provide() {
    return {
      auth: this.auth
    }
  }
}
</script>


<template><router-view></router-view></template>

<style></style>
