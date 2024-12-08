<template>
  <div class="swiper-demo">
    <div class="swiper-container">
      <div class="swiper-wrapper">
        <div class="swiper-slide" v-for="(image, index) in images" :key="index">
          <img
            :src="image"
            @click="selectImage(image)"
            :class="{ selected: image === selectedImage }"
            alt="Slide image"
          />
        </div>
      </div>
      <!-- 如果需要分页器 -->
      <div class="swiper-pagination"></div>

      <!-- 如果需要导航按钮 -->
      <div class="swiper-button-prev"></div>
      <div class="swiper-button-next"></div>

      
    </div>
  </div>
</template>
  
  <script>
import Swiper from "swiper";
import "swiper/css/swiper.min.css";
export default {
  data() {
    return {
      images: [
        require("../../../assets/user-photos/user1.jpg"),
        require("../../../assets/user-photos/user2.jpg"),
        require("../../../assets/user-photos/user3.jpg"),
        require("../../../assets/user-photos/user4.jpg"),
        require("../../../assets/user-photos/user5.jpg"),
        require("../../../assets/user-photos/user6.jpg"),
        require("../../../assets/user-photos/user7.jpg"),
        require("../../../assets/user-photos/user8.jpg"),
        require("../../../assets/user-photos/user9.jpg"),
        require("../../../assets/user-photos/user10.jpg"),
      ],
      selectedImage: null,
    };
  },
  mounted() {
    new Swiper(".swiper-container", {
      direction: "horizontal",
      slidesPerView: "auto", // 每个幻灯片都是独立的卡片
      centeredSlides: true, // 居中显示活动幻灯片
      spaceBetween: 30, // 幻灯片之间的间距
      pagination: {
        el: ".swiper-pagination",
      },
      navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
      },
    });
  },
  methods: {
    selectImage(image) {
      this.selectedImage = image; // 更新选中的图片
      this.$emit("select", image); // 触发事件，传递选中的图片
    },
  },
};
</script>
  
  <style scoped>
.swiper-container {
  width: 600px;
  height: 300px;
  margin: 0 auto;
}

.swiper-slide {
  width: 250px;
  height: 250px;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #fff;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
  border-radius: 10px;
  overflow: hidden;
  transition: transform 0.3s ease;
  margin: 10px;
}

.swiper-slide img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

.swiper-slide:hover {
  transform: scale(1.1);
}
.selected {
  border: 3px solid #409eff; /* 为选中的图片添加边框 */
}
</style>