<template>
  <div class="comment-send">
    <div class="input-area">
      <el-form ref="commentUserInfo" :model="commentUserInfo" :rules="rules">
        <el-form-item prop="content" ref="commentAreaRef">
          <!-- el-form-item添加ref, 用来调用$emit -->
          <textarea
            ref="commentArea"
            class="comment-text"
            placeholder="说点什么"
            rows="10"
            v-model="commentUserInfo.content"
            @blur="handlerBlurContent"
          ></textarea>
        </el-form-item>
        <div class="coll-box">
          <el-form-item
            prop="userNickname"
            class="collectmessage"
            ref="userNicknameRef"
          >
            <input
              type="text"
              v-model="commentUserInfo.userNickname"
              placeholder="昵称"
              @blur="handlerBlurNickName"
            />
          </el-form-item>
          <el-form-item prop="qqNum" class="collectmessage" ref="qqNumRef">
            <input
              type="text"
              v-model="commentUserInfo.qqNum"
              placeholder="QQ"
              @blur="handlerBlurQQNum"
            />
          </el-form-item>
          <el-form-item
            prop="userEmail"
            class="collectmessage"
            ref="userEmailRef"
          >
            <input
              type="text"
              v-model="commentUserInfo.userEmail"
              placeholder="邮箱"
              @blur="handlerBlurUserEmail"
            />
          </el-form-item>
          <div
            class="send iconfont icon-zhuanfa"
            @click="handlerSubmitComment()"
          >
            发布评论
          </div>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import initjs from "@/utils/init";
// import filterXSS from "@/plugins/xss/index";
export default {
  name: "CommentBox",
  data() {
    return {
      commentUserInfo: {
        content: "",
        userNickname: "",
        qqNum: "",
        userEmail: "",
      },
      commentForm: {
        parentId: 0,
        userId: undefined,
        userEmail: undefined,
        userNickname: "",
        userAvatar: "",
        content: "",
        replyUserId: undefined,
        replyUserNickname: undefined,
        replyUserAvatar: undefined,
        articleId: undefined,
        createTime: undefined,
        status: 0,
      },

      // 表单验证规则
      rules: {
        content: [
          {
            required: true,
            message: "请输入评论",
            trigger: "change",
          },
          {
            min: 1,
            max: 100,
            trigger: "blur",
            message: "评论内容为1-100个字符",
          },
        ],
        userNickname: [
          {
            validator: (rule, value, callback) => {
              let reg = /^[a-z0-9_-]{3,10}$/;
              if (!reg.test(value)) {
                return callback(new Error("请输入正确的昵称"));
              } else {
                callback();
              }
            },
            trigger: "blur",
          },
        ],
        qqNum: [
          {
            validator: (rule, value, callback) => {
              let reg = /^[1-9][0-9]{4,9}$/gim;
              if (!reg.test(value)) {
                return callback(new Error("请输入正确的QQ号"));
              } else {
                callback();
              }
            },
            trigger: "blur",
          },
        ],
        userEmail: [
          {
            validator: (rule, value, callback) => {
              let reg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
              if (!reg.test(value)) {
                return callback(new Error("请输入正确的邮箱地址"));
              } else {
                callback();
              }
            },
            trigger: "blur",
          },
          // { required: true, message: "请输入邮箱，以便回复", trigger: "blur" },
        ],
      },
    };
  },
  props: ["articleId"],
  methods: {
    // 提交评论
    handlerSubmitComment() {
      // 转义字符，防止xss注入
      // this.commentForm.content = filterXSS.process(this.commentForm.content);
      this.commentForm.articleId = this.articleId;

      this.commentForm.content = this.commentUserInfo.content;
      this.commentForm.userNickname = this.commentUserInfo.userNickname;
      //构建获取QQ头像URL
      this.commentForm.userAvatar =
        "http://q1.qlogo.cn/g?b=qq&nk=" + this.commentUserInfo.qqNum + "&s=40";
      this.commentForm.userEmail = this.commentUserInfo.userEmail;
      // 如果没有回复的人的名字,说明是直接评论文章
      if (!this.commentForm.replyUserNickname) this.commentForm.parentId = 0;

      // this.commentUserInfo = this.$options.data().commentUserInfo;
      console.log(this.commentForm);

      this.$refs.commentUserInfo.validate((valid) => {
        if (valid) {
          this.$API.comment
            .reqSaveComment(this.commentForm)
            .then((result) => {
              this.$emit("submitCallback", result);
              if (result.code == 200) {
                // 用户信息保存到本地
                initjs.setLocalUserCommentInfo(this.commentUserInfo);
                // 重置表单
                this.commentForm = this.$options.data().commentForm;
                this.commentUserInfo.content = "";
                this.$refs.commentArea.placeholder = "说点什么";
              }
            })
            .catch((err) => {
              this.$message.error(err);
            });
        } else {
          this.$message.warning("表单验证失败，请检查填写是否正确");
        }
      });
    },

    // 点击回复按钮，确认回复的是哪个
    handlerReply(commentNode) {
      const commentBox = this.$refs.commentArea;
      commentBox.placeholder = "@" + commentNode.userNickname;
      commentBox.focus();
      // 如果父级ID为0，说明是回复一级评论
      if (commentNode.parentId == 0) {
        //父级ID等于点击的评论的ID
        this.commentForm.parentId = commentNode.id;
      } else {
        // 回复二级评论
        this.commentForm.parentId = commentNode.parentId;
      }
      //要回复的对象
      this.commentForm.replyUserId = commentNode.userId;
      this.commentForm.replyUserNickname = commentNode.userNickname;
      // this.commentForm.targetType = 2;
      this.commentForm.replyUserAvatar = commentNode.userAvatar;
    },

    handlerBlurContent(mes) {
      // 添加blur事件回调，为了emit这个'el.form.blur'事件！
      this.$refs.commentAreaRef.$emit("el.form.blur", mes);
    },
    handlerBlurNickName(mes) {
      this.$refs.userNicknameRef.$emit("el.form.blur", mes);
    },
    handlerBlurQQNum(mes) {
      this.$refs.qqNumRef.$emit("el.form.blur", mes);
    },
    handlerBlurUserEmail(mes) {
      this.$refs.userEmailRef.$emit("el.form.blur", mes);
    },
  },
  mounted() {
    //填充评论区用户信息
    initjs.initCommentInfo(this.commentUserInfo);
  },
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/variable.less";

.comment-send {
  // padding: 30px 45px;
  background-color: var(--bg-white);
  font-size: 16px;

  .input-area {
    // display: flex;

    .comment-text {
      -webkit-appearance: none;
      -webkit-transition: color 0.1s ease, border-color 0.1s ease;
      width: 100%;
      max-height: 10em;
      margin: 0;
      tap-highlight-color: rgba(255, 255, 255, 0);
      padding: 0.78571429em 1em;
      border: 1px solid rgba(34, 36, 38, 0.15);
      outline: 0;
      color: rgba(0, 0, 0, 0.87);
      box-shadow: 0 0 0 0 transparent inset;
      transition: color 0.1s ease, border-color 0.1s ease;
      font-size: 1em;
      line-height: 1.2857;
      resize: none;
      // border: none;
      border: 1px dotted black;
      // border-bottom: none;
      background-color: transparent;
    }

    .coll-box {
      margin-top: 10px;
      display: flex;

      .collectmessage {
        margin-bottom: 0;
        flex: 1;
        height: 50px;
        font-size: 18px;
        // border: 2px solid green;
        // border-right: none;
        // box-shadow: 10px 10px 5px #d6c8ba;
        opacity: 0.7;
        // background-color: #d6c8ba;
        /deep/ .el-form-item__content {
          width: 100%;
          height: 100%;
        }
        input {
          outline: none;
          padding: 10px 25px;
          width: 100%;
          height: 100%;
          // background: url("https://mapaler.gitee.io/dajiadehuanxiangxiang-skin/Resources/char/charcute/ATH000101.webp") no-repeat;
          background: url("~@/assets/images/reimu.webp") no-repeat;
          background-size: 50px;
          background-position: 100% 50%;
          border: 1px dotted black;
          opacity: 0.7;
          font-weight: 700;
          
        }
      }
      .send {
        cursor: pointer;
        // margin-left: 10px;
        font-size: 18px;
        line-height: 50px;
        padding: 0 15px;
        // background-color: #f3b35e;
        background-color: #7ec661;

        // width: 100px;
        // font-family: "KaiTi";
        text-align: center;
        font-weight: 700;
        // border-radius: 10px;
      }
    }
  }

  .comment-message {
    margin-top: 10px;
  }
}
</style>