<script xmlns="http://www.w3.org/1999/html">
import axios from "axios";
import {ROOT_URL} from "@/main.js";
import {decrypt, encrypt} from "@/js/config_auth.js";

export default {
  inject: ["auth"],
  name: "Reg",
  data() {
    return {
      username: null,
      password: null,
      replay_password: null,
      error: null
    }
  },
  methods: {
    async submit_user() {
      if (!this.username || !this.username.trim()) {
        this.error = "Имя пользователя не должно быть пустым";
        return;
      }

      if (this.username.length > 150) {
        this.error = "Имя пользователя не должно быть более 150 символов";
        return;
      }

      if (!this.password || !this.password.trim()) {
        this.error = "Пароль не должен быть пустым";
        return;
      }

      if (!isNaN((this.password))) {
        this.error = "Пароль не может состоять только из цифр";
        return;
      }

      if (this.password.length < 8) {
        this.error = "Пароль должен содержать как минимум 8 символов";
        return;
      }

      if (this.username[0] === ' ' || this.password[0] === ' ') {
        this.error = "Логин и пароль не могут начинаться с пробела";
        return;
      }

      if (!this.replay_password || !this.replay_password.trim()) {
        this.error = "Подтвердие пароль";
        return;
      }

      try {
        const response = await axios.post(`${ROOT_URL}/auth/register`, {
          username: encrypt(this.username, this.auth.K),
          password: encrypt(this.password, this.auth.K),
          sessionId: localStorage.getItem('sessionId')
        });
        console.log(decrypt(response.data.token, this.auth.K))
        localStorage.setItem('token', decrypt(response.data.token, this.auth.K));
        this.error = null;
        this.$router.push('/');
        await this.auth.Check(this.$route.path);
      } catch (error) {
        if (error.response && error.response.status === 422) {
          // Если ошибка 409 (Conflict), показываем сообщение о конфликте
          this.error = "Такой логин уже существует";
        } else {
          // Если другая ошибка, выводим в консоль и обнуляем ошибку (если она уже была)
          console.error('Произошла ошибка:', error);
          this.error = null;
        }
      }
    }

  },
  async mounted() {
    await this.auth.SessionId();
    await this.auth.Check(this.$route.path);
  }
}
</script>

<template>
  <body>
      <main class="modal modal-signin position-static d-block bg-secondary py-5" tabindex="-1" role="main" id="modalSignin">
          <div>
            <ul>
            <li><h2>Интернет-магазин одежды</h2></li>
            </ul>

            <div class="modal-dialog" role="document">
              <div class="modal-content rounded-5 shadow">
                <div class="modal-header p-5 pb-4 border-bottom-0">
                  <div>
                    <h2 class="h3 mb-3 fw-normal" style="text-align:center" >Регистрация</h2>
                    <div>
                      <label>Имя пользователя:</label>
                      <input v-model="username" type="text" id="username" class="form-control" />
                      <label>Обязательное поле. Не более 150 символов. Только буквы, цифры и символы @/./+/-/_.</label>
                    </div>
                    <div>
                      <label>Пароль:</label>
                      <input v-model="password" type="password" id="password" class="form-control" />
                    </div>
                    <ul>
                    <li>Пароль не должен быть слишком похож на другую вашу личную информацию.</li>
                    <li>Ваш пароль должен содержать как минимум 8 символов.</li>
                    <li>Пароль не должен быть слишком простым и распространенным.</li>
                    <li>Пароль не может состоять только из цифр.</li>
                    </ul>
                    <div>
                      <label>Подтверждение пароля:</label>
                      <input v-model="replay_password" type="password" id="replay_password" class="form-control" />
                      <label>Для подтверждения введите, пожалуйста, пароль ещё раз.</label>
                    </div>
                  <div v-show="error">
                    {{ error }}
                  </div>
                  <div>
                    <button type="button" @click="submit_user()" name="action" class="w-100 btn btn-lg btn-primary">Регистрация</button>
                    <button @click="$router.push('/login')" class="w-100 btn btn-lg btn-primary">Войти</button>
                  </div>
                </div>
                      </div>
                    </div>
                  </div>
          </div>
      </main>
  </body>
</template>

