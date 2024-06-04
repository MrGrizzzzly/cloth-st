<script>
export default {
  name: "Error",
  inject: ['auth'],
}
</script>

<template>
  <body>
  <main class="modal modal-signin position-static d-block bg-secondary py-5" tabindex="-1" role="main" id="modalSignin">
    <div>
      <ul>
        <li><h2>У вас нету прав доступа к этой странице</h2></li>
      </ul>
      <div>
        <button @click="$router.push('/')" class="w-100 btn btn-lg btn-primary">Вернутся на главную страницу</button>
      </div>
    </div>
  </main>
  </body>
</template>

<style scoped>

</style>