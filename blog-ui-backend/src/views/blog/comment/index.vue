<template>
  <el-card>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="文章评论" name="first">
        <ArticleComment></ArticleComment>
      </el-tab-pane>

      <el-tab-pane label="留言中心" name="second">
        <MessageComment></MessageComment>
      </el-tab-pane>
    </el-tabs>
  </el-card>
</template>

<script>
import ArticleComment from "./ArticleComment.vue";
import MessageComment from "./MessageComment.vue";
export default {
  data() {
    return {
      activeName: "first",
    };
  },
  components: {
    ArticleComment,
    MessageComment,
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
  },
};
</script>

<style>
</style>