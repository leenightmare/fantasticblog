<template>
  <div class="item">
    <div class="top"></div>
    <div class="body" id="one">
      <div class="metadata">
        <el-avatar :src="messageItem.userAvatar" :size="30" class="userAvatar">
          <!-- <img  /> -->{{ messageItem.userNickname }}
        </el-avatar>

        <div class="username">{{ messageItem.userNickname }}</div>
      </div>
      <p class="content">
        {{ messageItem.content }}
      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: "MessageItem",
  props: ["messageItem"],
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/variable.less";
.item {
  position: relative;
  width: 210rem / @baseFont;
  height: 210rem / @baseFont;
  overflow: hidden;
  .top {
    position: relative;
    height: 20%;
    width: 100%;
    background-color: #d79711;

    clip-path: polygon(0 100%, 50% 0, 100% 100%);
    opacity: 0.8;

    &::before {
      content: "";
      position: absolute;
      top: 25%;
      left: 50%;
      transform: translateX(-50%);
      width: (15rem / @baseFont);
      height: (15rem / @baseFont);
      border-radius: 50%;
      background-color: #e8e3ca;
    }
  }

  .body {
    padding: (2rem / @baseFont) (20rem / @baseFont) (10rem / @baseFont)
      (20rem / @baseFont);
    width: 100%;
    height: 80%;

    // background: url("https://mapaler.gitee.io/dajiadehuanxiangxiang-skin/Resources/char/charcute/ATH000101.webp") no-repeat;
    background: url("~@/assets/images/reimu.webp") no-repeat;
    background-size: (40rem / @baseFont);
    background-position: 105% 100%;

    background-color: #d79711;
    opacity: 0.8;
    .metadata {
      display: flex;
      align-items: center;
      margin-bottom: (8rem / @baseFont);

      .userAvatar {
        width: (30rem / @baseFont);
        height: (30rem / @baseFont);
        border-radius: 50%;
        // margin-right: 2px;
        display: block;
      }

      .username {
        // position: absolute;
        flex: 1;
        padding-left: 10px;
        font-size: (15rem / @baseFont);
        font-weight: 600;
        width: 10em;
        overflow: hidden;
      }
    }

    .content {
      border-top: 1px solid #666;
      padding-top: (5rem / @baseFont);
      font-size: (14rem / @baseFont);
      line-height: 1.5;
      // font-weight: 700;
    }
  }
}
</style>