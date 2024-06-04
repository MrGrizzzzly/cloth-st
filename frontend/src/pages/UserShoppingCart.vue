<script>
import axios from "axios";
import {decryptJsonList, encrypt} from "@/js/config_auth.js";
import {ROOT_URL} from "@/main.js";
export default {


  name: "UserShoppingCart",
  inject: ['auth'],

  data() {
    return {
      notes: [],
      cost: 0,
      counter: new Array(0),
      ModelOpen: false,
      ModelOpen2: false,
      noteModel: this.ClearModal(),
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
        const response = await axios.post(`${ROOT_URL}/usershoppingcart/byUser`, {
          token: encrypt(localStorage.getItem('token'), this.auth.K),
          sessionId: localStorage.getItem('sessionId'),
        });

        this.notes = decryptJsonList(response.data.notesList, this.auth.K);
        this.counter = new Array(this.notes.length).fill(1)
        console.log(this.counter)
      } catch (error) {
        console.log(error)
      }
    },

    async editNote(noteId) {
      const note = this.notes.find((note) => note.id === noteId);
      this.noteModel = Object.assign({title: `${note.name_note}`}, note)
      this.noteModel.isEdit = true
      this.openModal()
    },

    async delNote(noteId, cost, quantity) {
      try {
        await axios.post(`${ROOT_URL}/usershoppingcart/del/${noteId}`, {
          token: encrypt(localStorage.getItem('token'), this.auth.K),
          sessionId: localStorage.getItem('sessionId'),
          id: encrypt(noteId, this.auth.K),
        });
        this.notes = this.notes.filter((note) => {return note.id !== noteId})
      } catch (error) {
        console.log(error);
      }
      await this.reduceCost(quantity*cost)
    },

    async getCost(){
      for (let i = 0; i < this.notes.length; i++) {
        this.cost = this.cost + parseInt(this.notes[i].note)
      }
    },

    async increaseCost(cost){
        this.cost = this.cost + parseInt(cost)
    },

    async reduceCost(cost){
      this.cost = this.cost - parseInt(cost)
    },

    async increment(cost, i){
      this.counter.splice(i, 1, this.counter[i]+1)
      await this.increaseCost(cost)
    },

    async decrement(cost, i){
      if (this.counter[i] !== 1) {
        this.counter.splice(i, 1, this.counter[i] - 1)
        await this.reduceCost(cost)
      }
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

    async sendData() {
      if (this.noteModel.isEdit) {
        await this.sendEditRequest()
      } else {
        await this.sendAddRequest()
      }
      this.closeModal()
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
    await this.getNotes();
    await this.getCost();
  }
}
</script>

<template>
  <body>
  <main>
    <header>
    <div class="navbar navbar-dark bg-dark shadow-sm">
      <div class="row" >
      <h2 class="text-white">Ваша корзина</h2>
      <p class="text-white">Общая стоимость товаров в корзине: {{ this.cost }}</p>
      </div>
      <div class="row" >
        <router-link @click="logOut()" to="/login" class="w-90 row btn btn-outline btn-primary">Выйти</router-link>
        <router-link @click="$router.push('/')" to="/login" class="w-90 row btn btn-outline btn-primary">На главную страницу</router-link>
      </div>
    </div>
    </header>
  <div>
  </div>
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3 " style=" position: relative; left: 20%; width: 60%; height: 100%">
          <div class="col" v-for="(note, index) in notes" :key="note.id">
            <div class="card shadow-sm">
              <img width="100%" height="420" role="img" :src="note.picture" alt="" >
              <div class="card-body">
                <p class="card-text">Название джинсов: {{ note.name_note }}</p>
                <div class="counter">
                <div class="card-text">Количество в корзине:  </div>
                <div class="counter__button counter__button_minus" @click="decrement(note.note, index)">-</div>
                <div class="counter__input"> <input v-model="counter[index]" disabled></div>
                <div class="counter__button counter__button_plus"  @click="increment(note.note, index)">+</div>
                </div>
                <p class="card-text">{{ note.note * counter[index] }} рублей</p>
                <div class="d-flex justify-content-between align-items-center">
                  <div class="btn-group">
                    <button @click="editNote(note.id)" type="button" name="action" class="btn btn-sm btn-outline-secondary">Подробнее</button>
                    <button type="button" name="action" class="btn btn-sm btn-outline-secondary">Купить</button>
                    <button @click="delNote(note.id, note.note, counter[index])" type="button" name="action" class="btn btn-sm btn-outline-secondary">Удалить</button>
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

.counter {
  display: -ms-inline-flexbox;
  display: inline-flex;
  -webkit-column-gap: 7px;
  column-gap: 7px;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

.counter__button {
  -webkit-box-flex: 0;
  -ms-flex: 0 0 30px;
  flex: 0 0 30px;
  height: 30px;
  border: 1px solid #000;
  border-radius: 50%;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  justify-content: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  cursor: pointer;
}

.counter__button_minus.disabled {
  border: 1px solid #c4c4c4;
  color: #c4c4c4;
  cursor: default;
}

.counter__input {
  -webkit-box-flex: 0;
  -ms-flex: 0 0 30px;
  flex: 0 0 30px;
}

.counter__input input {
  width: 100%;

  border: none;
  text-align: center;
}
</style>