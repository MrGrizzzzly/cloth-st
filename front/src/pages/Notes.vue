<script>
import axios from "axios";
import {decryptJson, decryptJsonList, encrypt} from "@/js/config_auth.js";
import {ROOT_URL} from "@/main.js";


export default {


  name: "Notes",
  inject: ['auth'],

  data() {
    return {
      notes: [],
      ModelOpen: false,
      noteModel: this.ClearModal(),
      errors: {
        name_note: null,
        note: null,
      }
    }
  },

  methods: {
    async getNotes() {
      try {
        const response = await axios.post(`${ROOT_URL}/note`, {
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

    async newNote() {
      this.noteModel.title = "Новая заметка"
      this.noteModel.isEdit = false
      this.errors = this.ClearErrors()
      this.ModelOpen = true;
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
        this.errors.name_note = "Заглавие не должно быть пустым";
        return;
      }

      if (!this.noteModel.note || !this.noteModel.note.trim()) {
        this.errors.note = "Заметка не должна быть пустой";
        return;
      }

      if (Object.values(this.errors).some(error => error !== null)) {
        return;
      }
      await this.sendData();
    },

    ClearModal() {
      return {
        isEdit: false,
        title: '',
        name_note: '',
        note: '',
        id: ''
      }
    },

    ClearErrors() {
      return {
        name_note: null,
        note: null,
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
        }
        await axios.post(`${ROOT_URL}/note/edit`, respData)
        const note = this.notes.find((note) => note.id === this.noteModel.id);
        const noteCopy = {...this.noteModel};
        Object.keys(noteCopy).forEach(key => {
          if (key in note) {
            note[key] = noteCopy[key];
          }
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
      }
      try {
        const response = await axios.post(`${ROOT_URL}/note/new`, respData)
        this.notes.push(decryptJson(response.data, this.auth.K))
      } catch (error) {
      }
    },

    logOut() {
      localStorage.removeItem('token');
      sessionStorage.removeItem('loginForm');
    },
  },
  async mounted() {
    await this.auth.SessionId();
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
      <h2 class="text-white">Ваши Товары</h2>
    </div>
    </header>
  <ul>
    <li><h2>Интернет-магазин одежды</h2></li>
    <li><router-link @click="logOut()" to="/login" class="btn btn-secondary my-2">Выйти</router-link></li>
  </ul>
  <div>
  </div>
    <div>
      <button type="button" @click="newNote()" class="btn btn-primary my-2">Создать заметку</button>
    </div>
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
          <div class="col" v-for="note in notes" :key="note.id">
            <div class="card shadow-sm">
              <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Эскиз" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#55595c"></rect><text x="50%" y="50%" fill="#eceeef" dy=".3em">Эскиз</text></svg>
              <div class="card-body">
                <p class="card-text">{{ note.name_note }}</p>
                <div class="d-flex justify-content-between align-items-center">
                  <div class="btn-group">
                    <button @click="editNote(note.id)" type="button" name="action" class="btn btn-sm btn-outline-secondary">Редактировать</button>
                    <button @click="delNote(note.id)" type="button" name="action" class="btn btn-sm btn-outline-secondary">Удалить</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
    </div>
    <div v-show="ModelOpen" class="modal-overlay-my" @click="closeModal">
      <div class="modal-my card blue-grey darken-1 container" @click.stop>
        <div class="row">
          <div class="col s12">
            <h4 v-text="noteModel.title"></h4>
          </div>
        </div>
          <form>
            <div class="row">
              <div class="input-field col s12">
                <label for="name_note">Заглавие:</label>
                <input type="text" id="name_note" name="name_note" v-model="noteModel.name_note" required>
              </div>
              <div v-show="errors.name_note" class="new badge red col">
                {{ errors.name_note }}
              </div>
            </div>

            <div class="row">
              <div class="input-field col s12">
                <label for="note">Содержание:</label>
                <textarea rows="10" cols="35" id="note" name="note" v-model="noteModel.note" required></textarea>
              </div>
              <div v-show="errors.note" class="new badge red col">
                {{ errors.note }}
              </div>
            </div>
            <div>
              <button @click="Confirm" type="button" name="action" class="btn btn-primary my-2">Сохранить</button>
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

</style>