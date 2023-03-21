<template>
  <div class="container detail-box">
    <div class="detail">
      <div class="title-split"></div>
      <div class="article">
        <el-skeleton :rows="1" animated :loading="loading" />
        <el-skeleton :rows="8" animated :loading="loading" />
        <h1 class="title w100">{{ articleInfo.title }}</h1>
        <div class="info w100">
          <img
            :src="articleInfo.userAvatar"
            alt=""
            class="userAvatar info-item"
          />
          <span class="userNickname info-item">{{
            articleInfo.userNickname
          }}</span>

          <!--  -->
          <span class="time info-item iconfont icon-date">{{
            articleInfo.createTime
          }}</span>

          <!--  -->
          <span class="view info-item iconfont icon-view">{{
            articleInfo.visitCount
          }}</span>

          <!-- -->
          <span
            style="display: block"
            class="view info-item iconfont icon-comment1"
            >{{ articleInfo.commentCount }}</span
          >
        </div>
        <img :src="articleInfo.headImage" style="width:100%" />
        <div
          id="article-content"
          class="content typo typo-selection animate__animated animate__fadeIn"
          v-html="articleInfo.content"
        ></div>
      </div>
      <CommentBox
        id="commentBox"
        ref="commentBox"
        :articleId="articleInfo.id"
        @submitCallback="callbackHandler"
      ></CommentBox>
      <div class="comment">
        <div class="title"><i class="iconfont icon-comment1"></i> 评论</div>
        <div class="box">
          <div
            class="item"
            v-for="parentComment in commentInfo"
            :key="parentComment.id"
          >
            <div class="avatar">
              <el-avatar :size="50" :src="parentComment.userAvatar">
                <!-- <img /> -->
                {{ parentComment.userNickname }}
              </el-avatar>
            </div>

            <div class="con">
              <div class="metadata">
                <a href="#" class="name">{{ parentComment.userNickname }}</a>
                <span class="time">{{ parentComment.createTime }}</span>
              </div>
              <div class="text">{{ parentComment.content }}</div>
              <div class="action">
                <span class="reply" @click="handlerReply(parentComment)"
                  >回复</span
                >
                <!-- <span class="delete">删除</span> -->
              </div>
              <!-- 子集评论 -->
              <div class="reply-box">
                <div v-for="child in parentComment.children" :key="child.id">
                  <div class="reply-avadtar">
                    <el-avatar :size="50" :src="child.userAvatar">
                      {{ child.userNickname }}
                    </el-avatar>
                    <!-- <img  /> -->
                  </div>
                  <div class="con">
                    <div class="metadata">
                      <a href="#" class="name">{{ child.userNickname }}</a>
                      <span
                        >回复 <a href="#"> @{{ child.replyUserNickname }}</a>
                      </span>
                      <span class="time"> {{ child.createTime }}</span>
                    </div>
                    <div class="text">{{ child.content }}</div>
                    <div class="action">
                      <span class="reply" @click="handlerReply(child)"
                        >回复</span
                      >
                      <!-- <span class="delete">删除</span> -->
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div style="clear: both"></div>
          </div>
        </div>
      </div>
    </div>
    <!-- <SideBar style="flex: 2"></SideBar> -->
    <div class="title-box">
      <!-- <header class="title-lable">目录</header> -->
      <div id="title-area"></div>
    </div>
  </div>
</template>

<script>
import { marked } from "marked";
import prism from "prismjs";
import * as tocbot from "tocbot";
export default {
  name: "Detail",
  data() {
    return {
      articleInfo: {
        id: 0,
        title: "",
        description: "",
        headImage: "",
        content: "",
        createTime: "",
        updateTime: "",
        commentCount: 0,
        visitCount: 0,
        userId: 0,
        userNickname: "",
        userAvatar: "",
        categoryId: 0,
        categoryName: "",
        isComment: 0,
        isTop: 0,
        isExtra: 0,
        status: 0,
        tags: "",
        tagList: [],
      },
      commentInfo: [],
      loading: true,
    };
  },
  methods: {
    // 获取文章详情
    async getDetailArticle() {
      this.loading = true;
      let articleId = this.$route.params.articleId;
      let result = await this.$API.article.reqDetailArticle(articleId);
      if (result.code == 200) {
        this.articleInfo = result.data;
        if (this.articleInfo.content) {
          this.articleInfo.content = marked(this.articleInfo.content);
        }
        this.$nextTick(() => {
          prism.highlightAll();
          this.titleInit();
        });
        this.loading = false;
      } else {
        Promise.reject(new Error("获取文章详情失败"));
      }
    },
    // 获取评论列表
    async getPageComment() {
      let articleId = this.$route.params.articleId;
      let result = await this.$API.comment.reqPageComment(articleId);
      if (result.code == 200) {
        this.commentInfo = result.data;
        console.log(result);
      }
    },

    titleInit() {
      tocbot.init({
        // Where to render the table of contents.
        tocSelector: "#title-area",
        // Where to grab the headings to build the table of contents.
        contentSelector: "#article-content",
        // Which headings to grab inside of the contentSelector element.
        headingSelector: "h1, h2",

        // For headings inside relative or absolute positioned containers within content.
        scrollSmooth: true,
        orderedList: false,
        hasInnerContainers: true,
      });
    },

    // 点击回复时，触发子组件方法。设置回复对象
    handlerReply(commentNode) {
      this.$refs.commentBox.handlerReply(commentNode);
    },
    // 点击评论后的回调
    callbackHandler(result) {
      // console.log(result);
      if (result.code == 200) {
        this.$message.success("评论成功");
        this.getPageComment();
      }
    },
  },
  mounted() {
    this.getDetailArticle();
    this.getPageComment();
  },
};
</script>

<style lang="less" scoped>
@import "~@/plugins/typo/typo.css";
@import "~@/assets/styles/variable.less";

#commentBox {
  padding: 30px 45px;
  background-color: #fff;
}

.detail-box {
  margin-top: calc(var(--headerHeight) + 20px);
  display: flex;
  align-items: flex-start;
  .detail {
    // position: relative;
    // max-width: 78%;
    box-shadow: 0px 0px 3px 0px gray;
    width: 78%;
    width: calc(100% - 1 * 240px);
    max-width: calc(100% - 1 * 220px);
    // max-width: 930px;

    flex: 8;

    .title-split {
      height: 50px;
      background-color: #c7c7c7;
    }
    .article {
      // background-color: var(--bg-white);
      background-color: #fff;
      padding: 40px;

      .title {
        padding: 10px 0;
        margin-bottom: 10px;

        font-size: 30px;
        // height: 60px;
        // text-align: center;

        // background-color: #eee;
      }

      .info {
        display: flex;

        font-size: 17px;
        padding-bottom: 10px;
        align-items: center;
        // border-bottom: 2px solid #eee;

        a {
          padding-right: 10px;
        }
        .info-item {
          margin: 2px;
        }

        .userAvatar {
          width: 30px;
          height: 30px;
          border-radius: 50%;
        }
      }

      .content {
        padding: 15px 0;
        font-size: 18px;
        text-align: justify;
        // background-color: transparent;
        min-height: 200px;
      }

      .divider {
        position: relative;
        text-align: center;
        font-size: 18px;
        font-weight: 600;
        &::before {
          content: "";
          position: absolute;
          left: 0;
          top: 50%;
          height: 2px;
          width: 46%;
          background-color: #c7c7c7;
        }
        &::after {
          content: "";
          position: absolute;
          right: 0;
          top: 50%;
          height: 2px;
          width: 46%;
          background-color: #c7c7c7;
        }
      }
    }

    .comment {
      // background-color: var(--bg-white);
      background-color: #fff;
      padding: 20px 45px;

      .title {
        font-size: 24px;
        padding: 10px 0;
      }

      .box {
        padding: 15px;
        // background-color: red;
        .item {
          // display: flex;
          // background-color: skyblue;
          border-top: 1px solid #c7c7c7;
          .avatar {
            float: left;
            margin-top: 20px;
            width: 50px;
            height: 50px;
            border-radius: 50%;
            // background-color: orange;
            img {
              display: block;
              width: 100%;
              height: 100%;
              border-radius: 50%;
            }
          }
          .con {
            font-size: 18px;
            margin-left: 3.5em;
            padding: 20px 0 14px 0;
            // letter-spacing: 1px;
            .metadata {
              font-size: 16px;
              .name {
                padding-right: 10px;
              }
              .time {
                color: gray;
                font-size: 15px;
              }
            }

            .text {
              margin: 0.5em 0 0.5em;
              font-size: 18px;
              word-wrap: break-word;
              color: rgba(0, 0, 0, 0.87);
              line-height: 1.3;
            }

            .action {
              color: rgba(0, 0, 0, 0.4);
              font-size: 16px;
              span {
                margin-right: 5px;
                cursor: pointer;
              }
            }

            .reply-box {
              padding-top: 10px;
              // background-color: pink;
              .reply-avadtar {
                float: left;
                width: 50px;
                height: 50px;
                margin-top: 20px;
                border-radius: 50%;
                // background-color: orange;
                margin-right: 10px;
                img {
                  display: block;
                  width: 100%;
                  height: 100%;
                  border-radius: 50%;
                }
              }
            }
          }
        }
      }
    }
  }
  .title-box {
    position: fixed;

    right: 160px;
    width: 200px;
    background-color: #fff;
    padding: 15px;
    margin: 15px;
    margin-top: 0;
    padding-top: 0;
    flex: 2;

    .title-lable {
      font-weight: 600;
      font-size: 18px;
    }

    #title-area {
      overflow: hidden;
      font-size: 15px;
      /deep/ a {
        color: gray;
      }
      /deep/ .toc-list-item {
        :hover {
          color: orange;
        }
      }
    }
  }
}
</style>