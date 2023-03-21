<template>
  <div class="container main">
    <div id="box" style="display: flex">
      <div style="flex: 8">
        <ArticleItem
          v-for="article in pageInfo.list"
          :articleInfo="article"
          :key="article.id"
        ></ArticleItem>
      </div>
      <SideBar style="flex: 2"></SideBar>
    </div>
  </div>
  <!-- <div class="container main">
    <ClassfiySelect
      :itemList="categoryList"
      title="分类"
      @selectItem="handlerSelectItem"
    ></ClassfiySelect>
    <ClassfiySelect
      :itemList="tagList"
      title="标签"
      @selectItem="handlerSelectItem"
    ></ClassfiySelect>
    <div class="articl">
      <ArticleItem
        v-for="article in pageInfo.records"
        :key="article.id"
        :articleInfo="article"
        class="animate__animated animate__fadeIn"
      ></ArticleItem>
    </div>
    
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page="pageInfo.pageNo"
      :page-size="pageInfo.pageSize"
      layout="prev, pager, next"
      :total="pageInfo.totalSize"
      class="pager"
      v-show="pageInfo.records.length"
    >
    </el-pagination>
  </div> -->
</template>

<script>
export default {
  name: "Classfiy",
  data() {
    return {
      pageInfo: {
        pageNum: 1,
        pageSize: 10,
        pages: 1,
        total: 5,
        list: [{}, {}, {}, {}, {}],
      },
    };
  },
  computed: {
    searchInfo() {
      return this.$route.params;
    },
  },
  methods: {
    getClassfiyDataHandler() {
      let type = this.searchInfo.type;
      let key = this.searchInfo.key;
      if (!type || !key) return this.$message.warning("请求出错");
      let searchBody = {};
      if (type == "category") {
        searchBody = { categoryId: key };
      } else if (type == "tag") {
        searchBody = { tags: key };
      }
      this.getClassfiyData(searchBody);
    },

    getClassfiyData(searchBody) {
      this.$API.article
        .pageMiniArticleByKeyWord(searchBody)
        .then((result) => {
          if (result.code == 200) {
            this.pageInfo = result.data;
          } else {
            this.$message.error(result.msg);
          }
        })
        .catch((err) => {
          this.$message.error(err);
        });
    },
  },
  watch: {
    searchInfo(newValue, oldValue) {
      this.getClassfiyDataHandler();
    },
  },
  mounted() {
    this.getClassfiyDataHandler();
  },
};
</script>

<style lang="less" scoped>
.main {
  margin: 100px auto;
  min-height: calc(100vh - var(--footerHeight));
  .pager {
    text-align: center;
  }
}
</style>