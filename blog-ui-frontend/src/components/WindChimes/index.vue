<template>
  <div class="windchimes-box">
    <div class="windchimes-position">
      <div class="top"></div>
      <div
        class="chimes"
        :style="{ backgroundImage: 'url(' + bgi + ')' }"
      ></div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "WindChimes",
  computed: {
    ...mapState("blogconfig", ["blogConfig"]),
    bgi() {
      return (
        (this.blogConfig && this.blogConfig.pendant) ||
        require("@/assets/images/ling3.png")
      );
    },
  },
};
</script>

<style lang="less" scoped>
@keyframes spring2 {
  0% {
    transform: translateX(-50%) scaleX(0.95) scaleY(0.95) skewX(-5deg)
      skewY(5deg);
  }
  // 50% {
  //   transform: translateX(-50%) scaleX(1) scaleY(1) skew(0deg);
  // }
  100% {
    transform: translateX(-50%) scaleX(0.95) scaleY(0.95) skewX(4deg)
      skewY(-4deg);
  }
}

.windchimes-box {
  position: absolute;
  //   right: 70px;

  width: 70px;
  height: 70px;
  .windchimes-position {
    position: relative;
    .top {
      width: 70px;
      height: 70px;
      // background-color: yellowgreen;
      background-color: #484944;
      // backdrop-filter: blur(10px);
      clip-path: polygon(0 50%, 50% 0, 100% 50%, 50% 100%);
    }
    .chimes {
      cursor: pointer;
      animation: spring2 0.4s ease-in-out infinite alternate;
      transform-origin: center top;
      animation-duration: 2.566667s;

      content: "";
      position: absolute;
      width: 100px;
      height: 325px;
      top: 68px;
      left: 55%;
      // transform: translateX(-50%);
      // transform: translateX(-50%) scaleX(0.95) scaleY(0.95) skewX(1deg) skewY(1deg);

      // background: url("~@/assets/images/ling3.png");
      // background-color: orange;
    }
  }
}
</style>