<script>
import {defineComponent} from 'vue'

export default {
  emits: ['prev', 'next']
}
</script>

<template>
<button class="carousel-control left" @click="$emit('prev')">Prev</button>
<button class="carousel-control right" @click="$emit('next')">Next</button>
</template>

<style scoped>
.carousel-control{
  background-color: rgba(0, 0, 0, .5);
  border: none;
  display: inline-block;
  position: absolute;
  height: 40px;
  width: 50px;
  top: calc(50% - 20px);
  color: #f3f3f3;
  cursor: pointer;
}
.left{
  left: 0;
}
.right{
  right: 0;
}
</style>