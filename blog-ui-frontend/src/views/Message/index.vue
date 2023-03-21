<template>
  <div class="container main">
    <div class="message-box">
      <div class="title">留言板</div>
      <div class="message-page">
        <div class="pre-page page-button" @click="prePage">上页</div>
        <div class="next-page page-button" @click="nextPage">下页</div>
      </div>

      <div class="inner">
        <MessageItem
          class="item-box animate__animated animate__fadeIn"
          v-for="message in messageInfo.list"
          :key="message.id"
          :messageItem="message"
        ></MessageItem>
      </div>
    </div>
    <CommentBox
      :articleId="0"
      id="commentBox"
      @submitCallback="callbackHandler"
    ></CommentBox>

  </div>
</template>

<script>
import MessageItem from "./MessageItem";
export default {
  name: "Message",
  components: {
    MessageItem,
  },
  data() {
    return {
      messageInfo: {
        pageNum: 1,
        pageSize: 8,
        total: 0,
        // pages: 0,
        list: [],
      },
    };
  },
  computed:{
    pages(){
      const {total,pageSize} = this.messageInfo;
      return Math.ceil(total / pageSize);
    }
  },

  methods: {
    // 获取留言列表
    async getPageMessageComment() {
      let { pageNum, pageSize } = this.messageInfo;
      let result = await this.$API.comment.reqPageMessageComment({
        pageNum,
        pageSize,
      });
      if (result.code == 200) {
        this.messageInfo = result.data;
        // this.messageInfo.list = result.data.list;
        // this.messageInfo.total = result.data.total;
        // this.messageInfo.pages = result.data.pages;
      } else {
        Promise.reject(new Error("获取留言列表失败"));
      }
    },
    callbackHandler(result) {
      if (result.code == 200) {
        this.$message.success("评论成功");
        this.getPageMessageComment();
      }
    },
    prePage() {
      // console.log(this.messageInfo.pageNum);
      if (this.messageInfo.pageNum <= 1) return;
      this.messageInfo.pageNum = this.messageInfo.pageNum - 1;
      this.getPageMessageComment();
    },
    nextPage() {
      if (this.messageInfo.pageNum >= this.pages) return;
      this.messageInfo.pageNum = this.messageInfo.pageNum + 1;
      this.getPageMessageComment();
    },
  },
  mounted() {
    this.getPageMessageComment();
  },
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/variable.less";
#commentBox {
  padding: 0;
  margin: 0 60px;
  background-color: transparent;
  /deep/ .comment-text {
    background-color: #d6c8ba;
  }
  /deep/ .coll-box {
    background-color: #d6c8ba;
  }
  /deep/ .collectmessage {
    background-color: #d6c8ba;
    opacity: 1;
  }
  /deep/ .send {
    background-color: #f3b35e;
  }
}
.main {
  // background-color: #fff;
  margin: 100px auto;
  // padding: 0 20px;

  .message-box {
    position: relative;
    overflow: hidden;
    background: url("~@/assets/images/bg_message4.png") no-repeat;
    background-size: contain;
    background-position: 50% 0%;
    max-width: (1280rem / @baseFont);
    height: (720rem / @baseFont);
    margin: 0 auto;

    .title {
      text-align: center;
      padding-top: (35rem / @baseFont);
      // border: 2px solid #f3b35e;
      color: #f3b35e;
      font-family: "STLiti";
    }

    .message-page {
      .page-button {
        cursor: pointer;
        // width: 60px;
        // height: 220px;
        width: (60rem / @baseFont);
        height: (220rem / @baseFont);
        // background-color: orange;
        color: #ea9d37;
        font-family: "STLiti";
        writing-mode: vertical-rl;
        text-align: center;
        &:active {
          color: #d69239;
        }
      }

      .pre-page {
        position: absolute;
        top: 26.5%;
        left: 1%;
        padding: (25rem / @baseFont) 0;
      }

      .next-page {
        position: absolute;
        top: 26.5%;
        right: 1%;
        padding: (25rem / @baseFont) 0;
        padding-left: (5rem / @baseFont);
        padding-right: (5rem / @baseFont);
        // background-color: #fff;
      }
    }

    .inner {
      position: absolute;
      top: 16.38%; // (118 / 720) * 100%
      // top: calc((118 + ((720 - 658) / 2)) / 720 * 100%);
      // top: 20.5%;
      left: 9%; // (105 / 1170) * 100%
      width: (960rem / @baseFont);
      height: (475rem / @baseFont);
      display: flex;
      justify-content: space-evenly;
      flex-wrap: wrap;
      // background-color: #f2f2f2;
      .item-box:nth-child(n + 5) {
        margin-top: -10px;
      }
    }
  }

  .action {
    // background-color: pink;
    margin: 0 60px;
    .action-text {
      display: flex;
      margin-bottom: 20px;
      .content-text {
        flex: 1;
        height: 50px;
        margin-bottom: 0;

        // border: 2px solid green;
        // border-right: none;
        // box-shadow: 10px 10px 5px #d6c8ba;
        // opacity: 0.7;
        input {
          font-size: 20px;
          outline: none;
          // background: url("https://mapaler.gitee.io/dajiadehuanxiangxiang-skin/Resources/char/charcute/ATH000101.webp") no-repeat;
          background: url("~@/assets/images/reimu.webp") no-repeat;
          background-size: 50px;
          background-position: 100% 50%;
          background-color: #d6c8ba;
          border: 1px solid #eee;
          padding: 10px 25px;
          width: 100%;
          height: 100%;
        }

        /deep/ .el-form-item__content {
          width: 100%;
          height: 100%;
        }
      }
      .send {
        cursor: pointer;
        margin-left: 10px;
        font-size: 20px;
        line-height: 50px;
        padding: 0 15px;
        background-color: #f3b35e;
        // width: 100px;
        // font-family: "KaiTi";
        text-align: center;
        font-weight: 700;
      }
    }
    .action-userinfo {
      display: flex;
      margin-top: 10px;

      .input-item {
        flex: 1;
        // margin: 0 10px;
        // border: 1px solid #eee;
        // opacity: 0.7;
        input {
          font-size: 20px;
          border: 1px dotted #d69239;
          outline: none;
          background-color: #d6c8ba;
          padding: 10px 25px;
          width: 100%;
          height: 100%;
        }

        /deep/ .el-form-item__content {
          width: 100%;
          height: 100%;
        }
      }

      .username {
        // border-right: 1px solid #eee;
      }
    }
  }
}
</style>