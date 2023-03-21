<template>
  <div class="banner">
    <!-- <div class="word">我们找到一个虚而不实,却挥之不去的东西,此为——幻想？</div> -->
    <!-- <div>列车一定会驶向下一站，那么舞台呢？我们呢？</div> -->
    <div class="word" :style="{ width: wordWith + 'rem' }">
      {{ blogConfig.poem || defaultPoem }}
    </div>
    <div class="roll" @click="rollHandler"></div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Banner",
  data() {
    return {
      defaultPoem: "我们找到一个虚而不实,却挥之不去的东西,此为——幻想？",
    };
  },
  methods: {
    rollHandler() {
      window.scrollTo({
        top: "750",
        behavior: "smooth",
      });
    },
  },
  computed: {
    ...mapState("blogconfig", ["blogConfig"]),
    wordWith() {
      const poemLength = this.blogConfig.poem && this.blogConfig.poem.length;
      if (poemLength <= 0) {
        return this.defaultPoem.length;
      }
      return poemLength;
      // return this.blogConfig.poem && this.blogConfig.poem.length;
    },
  },
  mounted() {},
};
</script>

<style scoped lang="less">
@import "~@/assets/styles/variable.less";
.banner {
  position: relative;
  height: 100vh;
  width: 100%;

  /* background-color: rgba(17, 17, 17, 0.5); */
  /* opacity: 0.8; */
}
.banner::before {
  // content: "";
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  width: 100%;
  /* background: url("~@/assets/banner .jpg") 50% / cover no-repeat; */
  background: url("~@/assets/banner.jpg") 50% / cover no-repeat;
  background-position: 50% 0%;
  /* background-attachment: fixed; */
  /* opacity: 0.7; */
}

.word {
  position: absolute;
  bottom: 30%;
  left: 50%;
  /* width: 18rem; */
  transform: translateX(-50%);
  // border-right: 2px solid #fff;
  overflow: hidden;
  animation: grow 6s linear;
  white-space: nowrap;
  font-size: (50rem / @baseFont);
  font-family: "STLiti";
}
@keyframes grow {
  from {
    width: 0rem;
  }
}

.banner .roll {
  cursor: pointer;
  position: absolute;
  bottom: 5%;
  left: 50%;
  transform: translateX(-50%) rotate(-135deg);
  width: 5vh;
  height: 5vh;
  border-top: 1vh solid #fff;
  border-left: 1vh solid #fff;
  animation: arrow-load 1.5s infinite;
}
@keyframes arrow-load {
  0% {
    opacity: 1;
    /* transform: translateX(-50%) rotate(-135deg) translateY(70%); */
    transform: rotate(-135deg) translate(15px, 15px);
  }
  0% {
    opacity: 0;
  }

  100% {
    opacity: 1;
    transform: rotate(-135deg) translate(-15px, -15px);
  }
}
</style>