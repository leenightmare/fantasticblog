<template>
  <el-card class="box-card" shadow="never">
    <div slot="header" class="year">{{ timeLineArticle.year }}</div>
    <el-timeline>
      <el-timeline-item
        type="info"
        v-for="article in timeLineArticle.article"
        :key="article.id"
        :timestamp="article.createTime"
        placement="top"
        size="large"
      >
        <h3>
          <router-link :to="`/article/${article.id}`" class="article-title">{{
            article.title
          }}</router-link>
        </h3>
      </el-timeline-item>
    </el-timeline>
  </el-card>
</template>

<script>
export default {
  data() {
    return {};
  },
  props: ["timeLineArticle"],
};
</script>

<style scoped lang="less">
.box-card {
  // box-shadow: none;
  border-radius: 0;
  border: 0;
  
  .year {
    font-size: 20px;
    font-weight: 600;
    color: #444;
    // font-family: Dosis, "PingFang SC", "Microsoft YaHei", Helvetica, Arial,
    //   Menlo, Monaco, monospace, sans-serif;
  }
  .article-title {
    color: #666;
  }
}
</style>