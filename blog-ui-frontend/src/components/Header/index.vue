<template>
  <div class="header">
    <nav class="container main">
      <!-- <div class="switch-box">
        <div class="switch"></div>
        <div class="ling"></div>
      </div> -->
      <WindChimes class="wind"></WindChimes>
      <div class="logo">幻想日志</div>
      <div class="item-list">
        <router-link to="/" class="iconfont icon-home">首页</router-link>
        <!-- <router-link to="/classfiy" class="iconfont icon-biaoqian"
          >标签</router-link
        > -->
        <router-link to="/timeline" class="iconfont icon-time1"
          >时间轴</router-link
        >
        <router-link to="/message" class="iconfont icon-comment"
          >留言板</router-link
        >
        <a
          href="http://lijad.cc:9528"
          class="iconfont icon-zhuanfa"
          target="_blank"
          >后台</a
        >
        <router-link to="/about" class="iconfont icon-info">关于我</router-link>
      </div>
      <div class="search">
        <input
          type="text"
          placeholder="搜索..."
          v-model="keyword"
          @keyup.enter="toSearch"
        />
        <i class="iconfont icon-search" @click="toSearch"></i>
      </div>
      <!-- <div class="login"><a href="#">登录</a></div> -->
    </nav>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      keyword: "",
    };
  },
  methods: {
    toSearch() {
      let keyword = this.keyword;
      this.$router.push({ name: "Search", query: { keyword } });
    },
  },
};
</script>

<style scoped lang="less">
@import "~@/assets/styles/variable.less";

@keyframes spring {
  0% {
    transform: translateX(-50%) scaleX(0.95) scaleY(0.95) skew(-4deg);
  }
  // 50% {
  //   transform: translateX(-50%) scaleX(1) scaleY(1) skew(0deg);
  // }
  100% {
    transform: translateX(-50%) scaleX(0.95) scaleY(0.95) skew(4deg);
  }
}

@media screen and (max-width: 1100px) {
  .switch-box {
    display: none;
  }
}

.header {
  position: fixed;
  // position: absolute;
  // position: sticky;
  top: 0;
  width: 100%;
  height: 55px;
  // line-height: 55px;
  z-index: 99;
  background-color: rgba(255, 255, 255, 0.15);
  box-shadow: 0px 0px 3px 0px gray;
  // background: url("~@/assets/bg.jpg");
  // background-color: #4c3f55;
  backdrop-filter: blur(10px);

  .wind {
    position: absolute;
    top: 8px;
    right: -85px;
  }

  .main {
    display: flex;
    align-items: center;
    position: relative;
    margin: 0 auto;
    height: 100%;

    .logo {
      font-size: 22px;
      font-weight: 700;
      letter-spacing: 2px;
      color: var(--topic-green);
      font-family: "STHupo";
    }

    .item-list {
      flex: 2;
      margin: 0 (5rem / @baseFont);

      a {
        display: block;
        float: left;
        text-align: center;
        padding: 0 (10rem / @baseFont);
        letter-spacing: 1px;
        font-size: 16px;
        height: 100%;
        color: #000;
        font-family: "LiSu";
        &:hover {
          // background-color: #eee;
        }
      }
    }

    .search {
      position: relative;
      margin-right: 20px;
      height: 30px;
      flex: 1;
      input {
        // border: 1px solid var(--topic-green);
        border: none;
        display: block;
        outline: none;
        width: 100%;
        height: 30px;
        // margin-top: 12px;
        padding: 10px;
        padding-left: 40px;
        border-radius: 35px;
        font-size: 16px;
        letter-spacing: 1px;
        // background-color: rgba(255, 255, 255, 0);
        background-color: transparent;
        &::-webkit-input-placeholder {
          color: #333;
        }
      }

      i {
        position: absolute;
        font-size: 18px;
        top: 5px;
        left: 15px;
      }
    }

    .login {
      text-align: center;
      // height: 100%;
      font-size: 18px;
      letter-spacing: 1px;
      a {
        // line-height: 60px;
        color: #333;
      }
    }
  }
}
</style>