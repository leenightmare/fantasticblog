<template>
  <!-- <div class="live-girl" @click="showDialog" :class="defalutUrl"> -->
  <div
    class="live-girl"
    @click="showDialog"
    :style="{ backgroundImage: 'url(' + bgi + ')' }"
  >
    <div class="dialog" v-show="show">
      {{ chat }}
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Girl",
  data() {
    return {
      show: false,
      chat: " 请好好感受一下吧，这是一颗充满希望的温暖之心。",
    };
  },
  computed: {
    ...mapState("blogconfig", ["blogConfig"]),
    ...mapState("chat", ["chatList"]),
    bgi() {
      return (
        (this.blogConfig && this.blogConfig.girlUrl) ||
        require("@/assets/images/girl.webp")
      );
    },
  },

  methods: {
    showDialog() {
      let index = Math.round(Math.random() * (this.chatList.length - 1));
      if (this.chatList[index]) {
        this.chat = this.chatList[index].content;
      }

      this.show = !this.show;
    },
  },
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/variable.less";

@keyframes spring {
  0% {
    transform: scaleY(0.95) skewX(-1deg);
  }
  50% {
    transform: scaleY(1) skewX(0);
  }
  100% {
    transform: scaleY(0.95) skewX(1deg);
  }
}

// .girlUrl {
//   background: url("~@/assets/images/girl.webp") no-repeat;
// }

.live-girl {
  z-index: 999;
  cursor: pointer;
  position: fixed;
  bottom: 0;
  right: 5px;
  //   width: (200rem / @baseFont);
  //   height: (300rem / @baseFont);
  width: (180rem / @baseFont);
  height: (220rem / @baseFont);

  // background: url("~@/assets/images/girl.webp") no-repeat;
  background-size: (300rem / @baseFont) (200rem / @baseFont);
  background-position: 50%;
  background-repeat: no-repeat;

  .dialog {
    // display: none;
    opacity: 0.7;
    position: absolute;
    top: 10%;
    left: -110%;
    width: (200rem / @baseFont);
    max-height: 10em;
    // height: 100px;
    background-color: rgb(38, 243, 19);
    // border: 1px solid yellowgreen;
    padding: (20rem / @baseFont) (15rem / @baseFont);
    line-height: 1.5;
    font-size: (17rem / @baseFont);
    // overflow: hidden;
    font-weight: 700;

    &::after {
      content: "";
      position: absolute;
      right: -1.5em;
      bottom: 50%;
      transform: translateY(50%);
      //   width: 40%;
      //   height: 40%;
      width: 1.5em;
      height: 1.5em;
      //   transform: rotate(45deg);
      background-color: rgb(38, 243, 19);
      //   border-top: 1px solid yellowgreen;
      //   border-right: 1px solid yellowgreen;

      clip-path: polygon(0 0, 100% 50%, 0 100%);
    }
  }
}
</style>