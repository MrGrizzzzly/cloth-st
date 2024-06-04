<script>
import axios from "axios";
import {decrypt, decryptJson, decryptJsonList, encrypt} from "@/js/config_auth.js";
import {ROOT_URL} from "@/main.js";
import Carousel from "@/pages/carousel/Carousel.vue";

export default {

  name: "Main",
  inject: ['auth'],

  data() {
    return {
      username: null,
      password: null,
      error: null,
      notes: [],
      carousel: [],
      ModelOpen: false,
      ModelOpenLogin: false,
      noteModel: this.ClearModal(),
      loginModel: this.ClearModalLogin(),
      loginButton: true,
      registerButton: true,
      logoutButton: false,
      personalAccountButton: false,
      addToCart: false,
    }
  },
  components: {
    Carousel
  },

  methods: {
    async getNotes() {
      try {
        const response = await axios.post(`${ROOT_URL}/note/all`, {
          token: encrypt(localStorage.getItem('token'), this.auth.K),
          sessionId: localStorage.getItem('sessionId'),
        });
        this.notes = decryptJsonList(response.data.notesList, this.auth.K);
      } catch (error) {
        console.log(error)
      }
    },

    async getCarousel(noteId) {
      try {
        const response = await axios.post(`${ROOT_URL}/carousel/${noteId}`, {
          token: encrypt(localStorage.getItem('token'), this.auth.K),
          sessionId: localStorage.getItem('sessionId'),
        });
        this.carousel = decryptJsonList(response.data.carouselNotesList, this.auth.K);

      } catch (error) {
        console.log(error)
      }
    },

    async getNote(noteId) {
      const respData = {
        token: encrypt(localStorage.getItem('token'), this.auth.K),
        sessionId: localStorage.getItem('sessionId'),
        id: encrypt(noteId.toString(), this.auth.K),
        quantity: encrypt("1", this.auth.K),
      }
      try {
        await axios.post(`${ROOT_URL}/usershoppingcart/new/${noteId}`, respData)
      } catch (error) {
      }
    },

    async editNote(noteId) {
      const note = this.notes.find((note) => note.id === noteId);
      this.noteModel = Object.assign({title: `${note.name_note}`}, note)
      this.noteModel.isEdit = true
      this.openModal()
      await this.getCarousel(noteId);

    },

    ClearModal() {
      return {
        isEdit: false,
        title: '',
        name_note: '',
        note: '',
        picture: '',
        composition: '',
        color: '',
        season: '',
        insulation: '',
        type_of_landing: '',
        country_of_manufacture: '',
        type_of_fastener: '',
        type_of_pockets: '',
        equipment: '',
        id: ''
      }
    },

    ClearModalLogin() {
      return {
        isLogin: false,
        username: '',
        password: '',
        error: ''
      }
    },

    closeModal() {
      this.ModelOpen = false;
      this.noteModel = this.ClearModal()
    },

    closeModalLogin() {
      this.ModelOpenLogin = false;
      this.loginModel = this.ClearModalLogin()
    },

    closeLoginButton() {
      this.loginButton = false;
    },

    closeRegisterButton() {
      this.registerButton = false;
    },

    closeLogoutButton() {
      this.logoutButton = false;
    },

    closePersonalAccountButton() {
      this.personalAccountButton = false;
    },

    closeAddToCartButton() {
      this.addToCart = false;
    },

    openModal() {
      this.ModelOpen = true;
    },

    openModalLogin() {
      this.ModelOpenLogin = true;
    },

    openLoginButton() {
      this.loginButton = true;
    },

    openRegisterButton() {
      this.registerButton = true;
    },

    openLogoutButton() {
      this.logoutButton = true;
    },

    openPersonalAccountButton() {
      this.personalAccountButton = true;
    },

    openAddToCartButton() {
      this.addToCart = true;
    },

    CheckLog(){
      this.closeLoginButton()
      this.closeRegisterButton()
      this.openLogoutButton()
      this.openPersonalAccountButton()
      this.openAddToCartButton()
    },


    async sendData() {
      this.closeModal()
      this.closeModalLogin()
      this.CheckLog()
    },

    async login() {
      this.openModalLogin()
    },

    async submit_user() {
      if (!this.username || !this.username.trim()) {
        this.error = "Логин не должен быть пустым";
        return;
      }

      if (!this.password || !this.password.trim()) {
        this.error = "Пароль не должен быть пустым";
        return;
      }
      try {
        const response = await axios.post(`${ROOT_URL}/auth/login`, {
          username: encrypt(this.username, this.auth.K),
          password: encrypt(this.password, this.auth.K),
          sessionId: localStorage.getItem('sessionId')
        });

        localStorage.setItem('token', decrypt(response.data.token, this.auth.K));

        this.error = null;
        this.$router.push('/');
        await this.sendData();
      } catch (error) {
        if (error.response && error.response.status === 404) {
          // Если ошибка 409 (Conflict), показываем сообщение о конфликте
          this.error = "Неверно введен логин или пароль";
        } else {
          // Если другая ошибка, выводим в консоль и обнуляем ошибку (если она уже была)
          console.error('Произошла ошибка:', error);
          this.error = null;
        }
      }
    },

    test() {
      if (localStorage.getItem('token') !== null ) {
        this.CheckLog()
      }
      else {
        this.CheckLogOut()
      }
    },

    CheckLogOut(){
      this.openLoginButton()
      this.openRegisterButton()
      this.closeLogoutButton()
      this.closePersonalAccountButton()
      this.closeAddToCartButton()
    },

    logOut() {
      localStorage.removeItem('token');
      localStorage.removeItem('role');
      this.CheckLogOut();
    },

  },
  async mounted() {
    await this.auth.SessionId();
    this.test();
    await this.auth.Check(this.$route.path);
    await this.getNotes();

  }
}
</script>

<template>
  <body>
  <main>
    <header>
    <div class="navbar navbar-dark bg-dark shadow-sm">
      <h2 class="text-white">Распродажа</h2>
      <div class="row" >
        <button v-show="loginButton" @click="login()" class="w-90 row btn btn-outline btn-primary">Войти</button>
        <button v-show="registerButton" @click="$router.push('/register')" class="w-90 btn row btn-outline btn-primary">Зарегистрируйтесь здесь</button>
        <button v-show="logoutButton" @click="logOut()" class="w-90 row btn btn-outline btn-primary">Выйти</button>
        <button v-show="personalAccountButton" @click="$router.push('/UserShoppingCart')" class="w-90 btn row btn-outline btn-primary">Личный кабинет</button>
      </div>
    </div>
    </header>
  <div>
  </div>
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3 " style=" position: relative; left: 20%; width: 60%; height: 100%">
      <div class="col" v-for="note in notes" :key="note.id">
            <div class="card shadow-sm" >
              <img width="100%" height="420" role="img" :src="note.picture" alt="" >
              <div class="card-body">
                <p class="card-text">{{ note.name_note }}</p>
                <p class="card-text">{{ note.note }} рублей</p>
                <div class="d-flex justify-content-between align-items-center">
                  <div class="btn-group">
                    <button @click="editNote(note.id)" type="button" name="action" class="btn btn-sm btn-outline-secondary">Подробнее</button>
                  </div>
                  <div class="btn-group">
                    <button @click="getNote(note.id)" v-show="addToCart" type="button" name="action" class="btn btn-sm btn-outline-secondary">Добавить в корзину</button>
                  </div>
                </div>
              </div>
            </div>
      </div>
    </div>
    <div v-show="ModelOpen" class="modal-overlay-my" @click="closeModal">
      <div class="modal-my card blue-grey darken-1 container" @click.stop   style=" width: 30%;">
        <div class="row">
          <div class="col s12">
            <carousel :slides="carousel"></carousel>
            <h4>Характеристики и описание</h4>
          </div>
        </div>
          <form>
            <h6>Основная информация</h6>
            <div class="row">
              <div class="input-field col s12">
                <p class="card-text">Состав: {{ noteModel.composition }}</p>
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <p class="card-text">Цвет: {{ noteModel.color }}</p>
              </div>
            </div>
            <h6>Дополнительная информация</h6>
            <div class="row">
              <div class="input-field col s12">
                <p class="card-text">Сезон: {{ noteModel.season }}</p>
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <p class="card-text">Утеплитель: {{ noteModel.insulation }}</p>
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <p class="card-text">Тип посадки: {{ noteModel.type_of_landing }}</p>
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <p class="card-text">Страна производства: {{ noteModel.country_of_manufacture }}</p>
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <p class="card-text">Вид застежки: {{ noteModel.type_of_fastener }}</p>
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <p class="card-text">Тип карманов: {{ noteModel.type_of_pockets }}</p>
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <p class="card-text">Комплектация: {{ noteModel.equipment }}</p>
              </div>
            </div>
        </form>
      </div>
    </div>
    <div v-show="ModelOpenLogin" class="modal-overlay-my" @click="closeModalLogin">
      <div class="modal-my w-25 card blue-grey darken-1 container" @click.stop>
      <h2 class="h3 mb-3 fw-normal">Вход</h2>
      <div class="form-floating">
        <input v-model="username" type="text" id="username" class="form-control" placeholder="name@example.com"/>
        <label>Имя пользователя:</label>
      </div>
      <div class="form-floating">
        <input v-model="password" type="password" id="password" class="form-control" placeholder="Password"/>
        <label>Пароль:</label>
      </div>
      <div v-show="error">
        {{ error }}
      </div>
      <div>
        <button type="button" @click="submit_user()" name="action" class="w-100 btn btn-lg btn-primary" >Вход</button>
      </div>
      </div>
    </div>
  </main>
  </body>
</template>

<style scoped>


.modal-overlay-my {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-my {
  background: white;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 70%;
  max-width: 90%;
}

</style>