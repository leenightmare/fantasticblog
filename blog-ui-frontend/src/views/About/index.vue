<template>
  <div class="about">
    <div class="about-box">
      <div class="about-clip"></div>
      <div class="about-content">
        <div class="about-header">
          <img :src="userAvatar" alt="" />
        </div>
        <h2 class="about-name">{{ username }}</h2>
        <div class="about-contact">
          <a :href="github" target="_blank">
            <i class="iconfont icon-github"></i>
          </a>
          <a
            href="javascript:void(0)"
            @mouseover="weixin = true"
            @mouseleave="weixin = false"
          >
            <i class="iconfont icon-weixin" target="_blank"></i>
            <img v-show="weixin" class="weixin" :src="wechatLink" alt="" />
          </a>
          <a href="javascript:void(0)">
            <i class="iconfont icon-QQ" target="_blank"></i>
          </a>
        </div>
        <div class="about-text">
          <p v-for="(sentence, index) in sentences" :key="index">
            {{ sentence }}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "About",
  computed: {
    ...mapState("user", ["userInfo"]),
    ...mapState("blogconfig", ["blogConfig"]),
    sentences() {
      const arr =
        this.blogConfig.aboutDescription &&
        this.blogConfig.aboutDescription.split("\n");
      return arr;
    },
    wechatLink() {
      return this.blogConfig.wechat || require("@/assets/images/weixin.jpg");
    },
    github() {
      return this.blogConfig.github || "https://github.com/leenightmare";
    },
    username() {
      return this.blogConfig.username || "少女祈祷中";
    },
    userAvatar() {
      return this.blogConfig.userAvatar || "";
    },
  },
  data() {
    return {
      weixin: false,
    };
  },
};
</script>

<style lang="less" soped>
.about {
  width: calc(var(--maxWith) - 100px);
  margin: 100px auto;
  text-align: center;
  min-height: calc(100vh - var(--footerHeight));

  .about-box {
    border: 1px solid rgba(34, 36, 38, 0.15);
    // box-shadow: 0 1px 2px 0 rgb(34 36 38 / 15%); 这句在less中没用，需要在css中用
    box-shadow: 0 1px 2px 0 rgb(34, 36, 38);
    .about-clip {
      height: 50px;
      background-color: #eaeaea;
    }

    .about-content {
      font-size: 18px;
      padding: 10px 20px 20px 20px;
      background-color: var(--bg-white);
      backdrop-filter: blur(5px);

      .about-header {
        width: 100px;
        height: 100px;
        border-radius: 50%;
        // background-color: orange;
        margin: 0 auto;
        img {
          width: 100%;
          height: 100%;
          display: block;
          border-radius: 50%;
        }
      }
      .about-name {
        margin: 10px 0;
      }

      .about-contact {
        // display: flex;
        // justify-content: center;
        position: relative;
        display: inline-block;
        padding: 0 20px;
        border-bottom: 1px solid #333;
        a {
          display: inline-flex;
          justify-content: center;
          align-items: center;
          width: 40px;
          height: 40px;
          border-radius: 50%;
          background-color: #cdced0;
          margin: 10px;

          i {
            font-size: 20px;
          }
        }

        .weixin {
          position: absolute;
          bottom: 100%;
          width: 200px;
          height: 200px;
        }
      }

      .about-text {
        margin: 30px 0;

        p {
          line-height: 2.5;
          font-weight: 500;
          font-family: "NSimSun";
        }
      }
    }
  }
}
</style>