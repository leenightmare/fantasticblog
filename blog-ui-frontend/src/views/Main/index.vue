<template>
  <div class="container main">
    <div class="article">
      <div class="top-banner">
        <div class="top-label iconfont icon-biaoqian1">博客</div>
        <div class="top-count">共{{ pageInfo.total }}篇笔记</div>
      </div>
      <div class="animate__animated animate__fadeIn">
        <ArticleItem
          v-for="article in pageInfo.list"
          :key="article.id"
          :articleInfo="article"
          class=""
        ></ArticleItem>
      </div>
      <el-pagination
        background
        @current-change="handleCurrentChange"
        layout="prev, pager, next"
        :total="pageInfo.total"
        :current-page="pageInfo.pageNum"
        :page-size="pageInfo.pageSize"
        style="text-align: center"
      >
      </el-pagination>
    </div>
    <SideBar></SideBar>
  </div>
</template>

<script>

export default {
  name: "Main",
  data() {
    return {
      pageInfo: {
        pageNum: 1,
        pageSize: 10,
        total: 0,
        list: [{}, {}, {}, {}],
      },
    };
  },
  methods: {
    // 获取博客列表在首页展示
    async getPageData() {
      let { pageNum, pageSize } = this.pageInfo;
      let result = await this.$API.article.reqMiniArticle({
        pageNum,
        pageSize,
      });
      if (result.code == 200) {
        this.pageInfo = result.data;
      } else {
        Promise.reject(new Error("获取博客分页数据失败"));
      }
    },

    // 改变页数
    handleCurrentChange(val) {
      this.pageInfo.pageNum = val;
      this.getPageData();
    },
  },
  mounted() {
    // 获取博客列表在首页展示
    this.getPageData();
  },
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/variable.less";

.main {
  display: flex;
  margin: (70rem / @baseFont) auto;
  margin-bottom: 80px;
  // max-width: 1100px;
  min-height: calc(100vh - var(--footerHeight));

  .article {
    flex: 8;
    padding: 15px 0;

    .top-banner {
      // height: 50px;
      opacity: 0.9;
      background-color: #fff;
      border: 1px solid rgba(34, 36, 38, 0.15);
      border-bottom: none;
      border-top: 5px solid var(--topic-green);
      box-shadow: 0 1px 2px 0 rgb(34, 36, 38);
      border-top-left-radius: 10px;
      border-top-right-radius: 10px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-size: 20px;
      padding: 20px 30px;
      // color: #7ec661;
      .top-label {
        font-size: 20px;
      }
      .top-count {
        // font-size: 20px;
      }
    }
  }
  .sidebar {
    flex: 2;
    padding: 15px;
    .userinfo {
      border-radius: 10px;
      margin-bottom: 20px;
      font-size: 15px;
      background-color: #fff;
      overflow: hidden;
      .head {
        width: 70px;
        height: 70px;
        border-radius: 50%;
        background-color: #f52360;
        margin: 15px auto;
      }

      .name {
        text-align: center;
        // margin-bottom: 10px;
        font-size: 20px;
      }
      .link {
        margin: 30px 0;
        display: flex;
        justify-content: space-evenly;
        text-align: center;
        a {
          font-size: 18px;
        }
      }

      .follow {
        margin: 20px auto;
        width: 80%;
        height: 40px;
        background-color: skyblue;
        text-align: center;
        line-height: 40px;

        font-size: 20px;
        color: #fff;
      }
    }

    .classfiy {
      border-radius: 10px;
      margin-bottom: 20px;
      background-color: #fff;
      padding: 0 15px;
      .title {
        text-align: center;
        padding-top: 15px;
        font-size: 20px;
        letter-spacing: 1px;
      }

      .item {
        margin-top: 10px;
        padding: 10px 0;
        font-size: 16px;
        a {
          display: flex;
          justify-content: space-between;
          // height: 20px;
          padding: 10px 10px;

          // background-color: pink;
          i {
            font-style: normal;
          }

          &:hover {
            background-color: pink;
          }
        }
      }
    }

    .note {
      border-radius: 10px;
      margin-bottom: 20px;
      background-color: #fff;
      padding: 0 15px;

      .title {
        text-align: center;
        padding-top: 15px;
        font-size: 20px;
        letter-spacing: 1px;
      }

      .content {
        // padding: 10px;
        // margin-top: 10px;
        // font-size: 15px;
        // line-height: 2;
      }
    }
  }
}
</style>