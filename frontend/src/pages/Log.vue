<script>
import axios from "axios";
import {decrypt, encrypt} from "@/js/config_auth.js";
import {ROOT_URL} from "@/main.js";
export default {

  name: "Log",
  inject: ['auth'],
  formLogin: {
    username: null,
    password: null,
    error: null
  },
  data() {
    return {
      form:{}
    }
  },
  watch:{
    form: {
      handler(){
        this.udtStorage()
      },
      deep: true
    }
  },
  created() {
    let storedForm = this.getStorage()
    this.form = { ...this.$options.formLogin, ...storedForm}
  },
  methods: {
    async submit_user() {
      if (!this.form.username || !this.form.username.trim()) {
        this.form.error = "Логин не должен быть пустым";
        return;
      }

      if (!this.form.password || !this.form.password.trim()) {
        this.form.error = "Пароль не должен быть пустым";
        return;
      }
      try {
      const response = await axios.post(`${ROOT_URL}/auth/login`, {
        username: encrypt(this.form.username, this.auth.K),
        password: encrypt(this.form.password, this.auth.K),
        sessionId: localStorage.getItem('sessionId')
      });

      localStorage.setItem('token', decrypt(response.data.token, this.auth.K));

      this.form.error = null;
      this.$router.push('/');
      } catch (error) {
        if (error.response && error.response.status === 404) {
          // Если ошибка 409 (Conflict), показываем сообщение о конфликте
          this.form.error = "Неверно введен логин или пароль";
        } else {
          // Если другая ошибка, выводим в консоль и обнуляем ошибку (если она уже была)
          console.error('Произошла ошибка:', error);
          this.form.error = null;
        }
      }
    },
    getStorage(){
      return JSON.parse(sessionStorage.getItem("loginForm"))
    },

    setStorage(val){
      sessionStorage.setItem("loginForm", JSON.stringify(val))
    },

    udtStorage(){
      let storedForm = this.getStorage()
        if(!storedForm)
          storedForm = {}
        else
          storedForm = JSON.parse(JSON.stringify(this.form))


      this.setStorage(storedForm)
    }

  },
  async mounted() {
    await this.auth.SessionId();
    //await this.auth.Check(this.$route.path);
  }
}
</script>

<template>
  <main class="modal modal-signin position-static d-block bg-secondary py-5" tabindex="-1" role="main" id="modalSignin">
      <ul>
        <li><h2>Интернет-магазин одежды</h2></li>
      </ul>
      <div class="modal-dialog" role="document">
        <div class="modal-content rounded-5 shadow">
          <div class="modal-header p-5 pb-4 border-bottom-0">
      <div>
        <h2 class="h3 mb-3 fw-normal">Вход</h2>
          <div class="form-floating">
            <input v-model="form.username" type="text" id="username" class="form-control" placeholder="name@example.com"/>
            <label for="floatingInput">Имя пользователя:</label>
          </div>
          <div class="form-floating">
            <input v-model="form.password" type="password" id="password" class="form-control" placeholder="Password"/>
            <label for="floatingInput">Пароль:</label>
          </div>
          <div v-show="form.error">
            {{ form.error }}
          </div>
          <div>
            <button type="button" @click="submit_user()" name="action" class="w-100 btn btn-lg btn-primary" >Вход</button>
          </div>
          <div>
            <label>Не зарегистрированы?</label>
            <button @click="$router.push('/register')" class="w-100 btn btn-lg btn-primary" >Зарегистрируйтесь здесь</button>
          </div>
      </div>
          </div>
        </div>
      </div>
  </main>
</template>
