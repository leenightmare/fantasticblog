<template>
  <div class="article-item">
    <el-skeleton animated v-show="loading" class="article-content">
      <template slot="template">
        <div style="display: flex; justify-content: space-between">
          <div style="width: 100%">
            <el-skeleton-item variant="h1" style="width: 80%" />
            <el-skeleton-item variant="p" style="width: 90%; height: 30%" />
            <el-skeleton-item variant="rect" style="width: 60%" />
          </div>
          <el-skeleton-item
            variant="image"
            style="width: 250px; height: 200px"
          />
        </div>
      </template>
    </el-skeleton>
    <div class="article-content" v-show="articleInfo.id">
      <div class="article-detail">
        <h3 class="title">
          <router-link :to="`/article/${articleInfo.id}`">
            {{ articleInfo.title }}
          </router-link>
        </h3>
        <p class="text">
          {{ articleInfo.description }}
        </p>
        <!-- <div class="classfiy"></div> -->
        <div class="info">
          <div class="left">
            <div class="left-info author">
              <img :src="articleInfo.userAvatar" alt="" class="userAvatar" />
              <span> {{ articleInfo.userNickname }}</span>
            </div>
            <div class="left-info time iconfont icon-date">
              {{ articleInfo.createTime }}
            </div>
            <div class="left-info view iconfont icon-view">
              {{ articleInfo.visitCount }}
            </div>
            <div class="left-info message iconfont icon-comment1">
              {{ articleInfo.commentCount }}
            </div>
          </div>
          <div class="tag">
            <!-- <el-tag
              class="item"
              type="warning"
              size="medium"
              v-for="(tag, index) in articleInfo.tagList"
              :key="index"
              v-show="index <= 1"
            >
              <router-link :to="`/classfiy/tag/${tag}`">
                {{ tag }}</router-link
              ></el-tag
            > -->
            <el-tag
              class="item"
              type="info"
              size="medium"
              v-show="articleInfo.categoryId"
            >
              <router-link :to="`/classfiy/category/${articleInfo.categoryId}`">
                {{ articleInfo.categoryName }}</router-link
              ></el-tag
            >
          </div>
        </div>
      </div>
      <div class="article-img" v-show="articleInfo.headImage">
        <a href="#" class="w100 h100">
          <img :src="articleInfo.headImage" alt="" />
        </a>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ArticleItem",
  props: ["articleInfo"],
  data() {
    return {};
  },
  computed: {
    loading() {
      return !this.articleInfo.id;
    },
  },
  mounted() {},
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/variable.less";
.article-item {
  display: flex;
  align-items: center;
  border: 1px solid rgba(34, 36, 38, 0.15);
  box-shadow: 0 1px 2px 0 rgb(34, 36, 38);
  padding: (10rem / @baseFont) 0;
  // background-color: var(--bg-white);
  background-color: #fff;
  min-height: 200px;
  // backdrop-filter: blur(10px);

  .article-content {
    padding: 0 (30rem / @baseFont);
    min-height: 230px;
    display: flex;
    flex: 1;

    .article-img {
      padding: 10px;
      width: 250px;
      max-height: 250px;

      img {
        height: 100%;
        width: 100%;
        border-radius: 20px;
      }
    }

    .article-detail {
      justify-content: space-evenly;
      flex-direction: column;
      display: flex;
      padding: 0 10px;
      font-size: 14px;
      flex: 1;

      .title {
        margin-bottom: (14rem / @baseFont);
        font-size: 20px;
        a {
          color: #333;
        }
      }

      .text {
        margin-bottom: (20rem / @baseFont);
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 3;
        word-break: break-all;
        display: -webkit-box;
        overflow: hidden;
        color: gray;
        font-size: 16px;
        line-height: 2;
      }

      .info {
        justify-content: space-between;
        align-items: center;
        display: flex;

        .left {
          display: flex;
          align-items: center;
          .left-info {
            // margin: 0 5px;
            margin-right: 5px;
          }
          .author {
            display: flex;
            align-items: center;
            .userAvatar {
              width: 30px;
              height: 30px;
              border-radius: 50%;
              margin-right: 5px;
            }
          }
        }
        .tag {
          .item {
            margin-left: 5px;
            font-size: 14px;
          }
        }
      }
    }
  }
}
</style>