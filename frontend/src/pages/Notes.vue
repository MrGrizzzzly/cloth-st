<script>
import axios from "axios";
import {decrypt, decryptJson, decryptJsonList, encrypt} from "@/js/config_auth.js";
import {ROOT_URL} from "@/main.js";

export default {


  name: "Notes",
  inject: ['auth'],

  data() {
    return {
      notes: [],
      ModelOpen: false,
      ModelOpen2: false,
      noteModel: this.ClearModal(),
      carouselModel: this.ClearModal2(),
      errors: {
        name_note: null,
        note: null,
        picture: null,
        composition: null,
        color: null,
        season: null,
        insulation: null,
        type_of_landing: null,
        country_of_manufacture: null,
        type_of_fastener: null,
        type_of_pockets: null,
        equipment: null,
      }
    }
  },


  methods: {
    async getNotes() {
      try {
        const response = await axios.post(`${ROOT_URL}/note/byUser`, {
          token: encrypt(localStorage.getItem('token'), this.auth.K),
          sessionId: localStorage.getItem('sessionId'),
        });
        this.notes = decryptJsonList(response.data.notesList, this.auth.K);
      } catch (error) {
        console.log(error)
      }
    },

    async editNote(noteId) {
      const note = this.notes.find((note) => note.id === noteId);
      this.noteModel = Object.assign({title: `Edit ${note.name_note}`}, note)
      this.noteModel.isEdit = true
      this.openModal()
    },

    async create_carusel(noteId) {
      this.note_id = noteId
      this.openModal2()
    },

    async newNote() {
      this.noteModel.title = "Новая заметка"
      this.noteModel.isEdit = false
      this.errors = this.ClearErrors()
      this.openModal()
    },

    async delNote(noteId) {
      try {
        await axios.post(`${ROOT_URL}/note/del`, {
          token: encrypt(localStorage.getItem('token'), this.auth.K),
          sessionId: localStorage.getItem('sessionId'),
          id: encrypt(noteId, this.auth.K),
        });
        this.notes = this.notes.filter((note) => {return note.id !== noteId})
      } catch (error) {
        console.log(error);
      }
    },

    async Confirm() {
      this.errors = this.ClearErrors()

      if (!this.noteModel.name_note || !this.noteModel.name_note.trim()) {
        this.errors.name_note = "Название";
        return;
      }

      if (!this.noteModel.note || !this.noteModel.note.trim()) {
        this.errors.note = "Стоимость";
        return;
      }

      if (Object.values(this.errors).some(error => error !== null)) {
        return;
      }
      await this.sendData();
    },

    async Confirm2() {
      await this.sendAddRequest2()
      this.closeModal2()
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

    ClearModal2() {
      return {
        carousel_picture: '',
        note_id: ''
      }
    },

    ClearErrors2() {
      return {
        carousel_picture: null,
      }
    },

    ClearErrors() {
      return {
        name_note: null,
        note: null,
        picture: null,
        composition: null,
        color: null,
        season: null,
        insulation: null,
        type_of_landing: null,
        country_of_manufacture: null,
        type_of_fastener: null,
        type_of_pockets: null,
        equipment: null,
      }
    },

    closeModal() {
      this.ModelOpen = false;
      this.noteModel = this.ClearModal()
    },

    openModal() {
      this.errors = this.ClearErrors()
      this.ModelOpen = true;
    },

    closeModal2() {
      this.ModelOpen2 = false;
      this.carouselModel = this.ClearModal2()
    },

    openModal2() {
      this.errors = this.ClearErrors2()
      this.ModelOpen2 = true;
    },

    async sendData() {
      if (this.noteModel.isEdit) {
        await this.sendEditRequest()
      } else {
        await this.sendAddRequest()
      }
      this.closeModal()
    },

    async sendEditRequest() {
      try {
        const respData = {
          token: encrypt(localStorage.getItem('token'), this.auth.K),
          sessionId: localStorage.getItem('sessionId'),
          id: encrypt(this.noteModel.id.toString(), this.auth.K),
          name_note: encrypt(this.noteModel.name_note, this.auth.K),
          note: encrypt(this.noteModel.note, this.auth.K),
          picture: encrypt(this.noteModel.picture, this.auth.K),
          composition: encrypt(this.noteModel.composition, this.auth.K),
          color: encrypt(this.noteModel.color, this.auth.K),
          season: encrypt(this.noteModel.season, this.auth.K),
          insulation: encrypt(this.noteModel.insulation, this.auth.K),
          type_of_landing: encrypt(this.noteModel.type_of_landing, this.auth.K),
          country_of_manufacture: encrypt(this.noteModel.country_of_manufacture, this.auth.K),
          type_of_fastener: encrypt(this.noteModel.type_of_fastener, this.auth.K),
          type_of_pockets: encrypt(this.noteModel.type_of_pockets, this.auth.K),
          equipment: encrypt(this.noteModel.equipment, this.auth.K),
        }
        await axios.post(`${ROOT_URL}/note/edit`, respData)

        const note = this.notes.find((note) => note.id === this.noteModel.id);
        const noteCopy = {...this.noteModel};
        Object.keys(noteCopy).forEach(key => {
          if (key in note)
            note[key] = noteCopy[key];
        });
      } catch (error) {
      }
    },

    async sendAddRequest() {
      const respData = {
        token: encrypt(localStorage.getItem('token'), this.auth.K),
        sessionId: localStorage.getItem('sessionId'),
        name_note: encrypt(this.noteModel.name_note, this.auth.K),
        note: encrypt(this.noteModel.note, this.auth.K),
        picture: encrypt(this.noteModel.picture, this.auth.K),
        composition: encrypt(this.noteModel.composition, this.auth.K),
        color: encrypt(this.noteModel.color, this.auth.K),
        season: encrypt(this.noteModel.season, this.auth.K),
        insulation: encrypt(this.noteModel.insulation, this.auth.K),
        type_of_landing: encrypt(this.noteModel.type_of_landing, this.auth.K),
        country_of_manufacture: encrypt(this.noteModel.country_of_manufacture, this.auth.K),
        type_of_fastener: encrypt(this.noteModel.type_of_fastener, this.auth.K),
        type_of_pockets: encrypt(this.noteModel.type_of_pockets, this.auth.K),
        equipment: encrypt(this.noteModel.equipment, this.auth.K),
      }
      try {
        const response = await axios.post(`${ROOT_URL}/note/new`, respData)
        this.notes.push(decryptJson(response.data, this.auth.K))
      } catch (error) {
      }
    },

    async sendAddRequest2() {
      const respData = {
        token: encrypt(localStorage.getItem('token'), this.auth.K),
        sessionId: localStorage.getItem('sessionId'),
        picture: encrypt(this.carouselModel.carousel_picture, this.auth.K),
      }
      try {
        console.log(respData)
        await axios.post(`${ROOT_URL}/carousel/new/${this.note_id}`, respData)
      } catch (error) {
      }
    },

    logOut() {
      localStorage.removeItem('token');
      localStorage.removeItem('role');
      sessionStorage.removeItem('loginForm');
    },


  },
  async mounted() {

    await this.auth.SessionId();
    await this.auth.Check(this.$route.path);
    await this.auth.Check_role();
    await this.getNotes();

  }
}
</script>

<template>
  <body>
  <main>
    <header>
    <div class="navbar navbar-dark bg-dark shadow-sm">
      <h2 class="text-white">Ваши Товары</h2>
      <div class="row" >
        <router-link @click="$router.push('/')" to="/login" class="w-90 row btn btn-outline btn-primary">На главную страницу</router-link>
        <router-link @click="logOut()" to="/login" class="w-90 row btn btn-outline btn-primary">Выйти</router-link>
      </div>
    </div>
    </header>
  <div>
  </div>
    <div>
      <button type="button" @click="newNote()" class="btn btn-primary my-2">Добавить товар</button>
    </div>
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3 " style=" position: relative; left: 20%; width: 60%; height: 100%">
          <div class="col" v-for="note in notes" :key="note.id" >
            <div class="card shadow-sm">
              <img width="100%" height="420" role="img" :src="note.picture" alt="" >
              <div class="card-body">
                <p class="card-text">{{ note.name_note }}</p>
                <div class="d-flex justify-content-between align-items-center">
                  <div class="btn-group">
                    <button @click="editNote(note.id)" type="button" name="action" class="btn btn-sm btn-outline-secondary">Редактировать</button>
                    <button @click="create_carusel(note.id)" type="button" name="action" class="btn btn-sm btn-outline-secondary">Добавить картинку</button>
                    <button @click="delNote(note.id)" type="button" name="action" class="btn btn-sm btn-outline-secondary">Удалить</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
    </div>
    <div v-show="ModelOpen" class="modal-overlay-my" @click="closeModal">
      <div class="modal-my card blue-grey darken-1 container" @click.stop  style=" width: 30%;">
        <div class="row" >
          <div class="col s12">
            <h4 v-text="noteModel.title"></h4>
          </div>
        </div>
          <form>
            <div class="row">
              <div class="input-field col s12">
                <label for="name_note">Название:</label>
                <input type="text" id="name_note" name="name_note" v-model="noteModel.name_note" required>
              </div>
              <div v-show="errors.name_note" class="new badge red col">
                {{ errors.name_note }}
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <label for="note">Стоимость: </label>
                <input id="note" name="note" v-model="noteModel.note" required>
              </div>
              <div v-show="errors.note" class="new badge red col">
                {{ errors.note }}
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <label for="note">Гл. рисунок: </label>
                <input id="note" name="note" v-model="noteModel.picture" required>
              </div>
              <div v-show="errors.picture" class="new badge red col">
                {{ errors.picture }}
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <label for="note">Состав: </label>
                <input id="note" name="note" v-model="noteModel.composition" required>
              </div>
              <div v-show="errors.composition" class="new badge red col">
                {{ errors.composition }}
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <label for="note">Цвет: </label>
                <input id="note" name="note" v-model="noteModel.color" required>
              </div>
              <div v-show="errors.color" class="new badge red col">
                {{ errors.color }}
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <label for="note">Сезон: </label>
                <input id="note" name="note" v-model="noteModel.season" required>
              </div>
              <div v-show="errors.season" class="new badge red col">
                {{ errors.season }}
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <label for="note">Утеплитель: </label>
                <input id="note" name="note" v-model="noteModel.insulation" required>
              </div>
              <div v-show="errors.insulation" class="new badge red col">
                {{ errors.insulation }}
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <label for="note">Тип посадки: </label>
                <input id="note" name="note" v-model="noteModel.type_of_landing" required>
              </div>
              <div v-show="errors.type_of_landing" class="new badge red col">
                {{ errors.type_of_landing }}
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <label for="note">Страна производства: </label>
                <input id="note" name="note" v-model="noteModel.country_of_manufacture" required>
              </div>
              <div v-show="errors.country_of_manufacture" class="new badge red col">
                {{ errors.country_of_manufacture }}
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <label for="note">Вид застежки: </label>
                <input id="note" name="note" v-model="noteModel.type_of_fastener" required>
              </div>
              <div v-show="errors.type_of_fastener" class="new badge red col">
                {{ errors.type_of_fastener }}
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <label for="note">Тип карманов: </label>
                <input id="note" name="note" v-model="noteModel.type_of_pockets" required>
              </div>
              <div v-show="errors.type_of_pockets" class="new badge red col">
                {{ errors.type_of_pockets }}
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <label for="note">Комплектация: </label>
                <input id="note" name="note" v-model="noteModel.equipment" required>
              </div>
              <div v-show="errors.equipment" class="new badge red col">
                {{ errors.equipment }}
              </div>
            </div>
            <div>
              <button @click="Confirm" type="button" name="action" class="btn btn-primary my-2">Сохранить</button>
            </div>
        </form>
      </div>
    </div>
    <div v-show="ModelOpen2" class="modal-overlay-my2" @click="closeModal2">
      <div class="modal-my card blue-grey darken-1 container" @click.stop>
        <div class="row">
          <div class="col s12">
            <h4 v-text="noteModel.title"></h4>
          </div>
        </div>
        <form>
          <div class="row">
            <div class="input-field col s12">
              <label for="name_note">Путь к картинке:</label>
              <input type="text" id="carousel_picture" name="carousel_picture" v-model="carouselModel.carousel_picture" required>
            </div>
          </div>
          <div>
            <button @click="Confirm2" type="button" name="action" class="btn btn-primary my-2">Сохранить</button>
          </div>
        </form>
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

.modal-overlay-my2 {
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
</style>