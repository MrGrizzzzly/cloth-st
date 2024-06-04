<script>
import CarouselItem from "@/pages/carousel/CarouselItem.vue";
import CarouselControls from "@/pages/carousel/CarouselControls.vue";

export default {
  props: ['slides'],
  components: {CarouselItem, CarouselControls},
  data() {
    return {
      currentSlide: 0,
      slideInterval: null,
      direction: "right"
    }
  },

  methods: {
    setCurrentSlide(index){
      this.currentSlide = index;
    },
    prev(){
      this.currentSlide = this.currentSlide > 0 ? this.currentSlide - 1 : this.slides.length - 1
      this.direction = "left"
    },
    next(){
      this.currentSlide = this.currentSlide < this.slides.length - 1 ? this.currentSlide + 1 : 0
      this.direction = "right"
    }
  },

  mounted() {
  //  this.slideInterval = setInterval(()=>{
  //    this.next()
  //  }, 3000);

  },

  beforeUnmount() {
    clearInterval(this.slideInterval)
  }

}
</script>

<template>
  <div class="carousel">
    <div class="carousel-inner">
      <carousel-item
        v-for="(slide, index) in slides"
        :slide="slide"
        :key="`item-${index}`"
        :current-slide="currentSlide"
        :index="index"
        :direction="direction"
      ></carousel-item>
      <carousel-controls @prev="prev" @next="next"></carousel-controls>
    </div>
  </div>
</template>

<style scoped>
.carousel{
  display: flex;
  justify-content: center;
}
.carousel-inner{
  position: relative;
  width: 246px;
  height: 328px;
  overflow: hidden;
}
</style>