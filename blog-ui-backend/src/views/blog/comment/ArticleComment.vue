<template>
  <div>
    <div class="option">
      <h3>全部评论（{{ pageInfo.total }}）</h3>
      <el-select
        v-model="articleId"
        placeholder="全部文章"
        filterable
        remote
        :remote-method="remoteGetTitle"
        @change="handlerSelectTitle"
        @keyup.enter.native="remoteGetTitle2"
      >
        <el-option
          v-for="item in conArticles"
          :key="item.id"
          :label="item.title"
          :value="item.id"
        >
        </el-option>
        <template slot="empty"> 没有找到</template>
      </el-select>
    </div>

    <el-table :data="pageInfo.list" style="width: 100%" v-loading="loading">
      <el-table-column prop="prop" type="selection"> </el-table-column>
      <el-table-column prop="articleTitle" label="文章" width="width">
      </el-table-column>
      <el-table-column prop="userNickname" label="用户" width="width">
      </el-table-column>

      <el-table-column prop="replyUserNickname" label="回复对象" width="width">
      </el-table-column>
      <el-table-column prop="content" label="内容" width="width">
      </el-table-column>
      <el-table-column prop="createTime" label="时间" width="width">
      </el-table-column>
      <el-table-column prop="prop" label="操作" width="width">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleReply(scope.$index, scope.row)"
            >回复</el-button
          >
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
      <template slot="empty">
        <el-empty
          :image-size="300"
          image="https://s1.hdslb.com/bfs/static/studio/creativecenter-platform/img/empty.9e92c422.png"
          description="还没有评论哦~"
        >
        </el-empty>
      </template>
    </el-table>
    <el-pagination
      :current-page="pageInfo.pageNum"
      :page-sizes="[5, 10, 15]"
      :page-size="pageInfo.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pageInfo.total"
      @current-change="pageTurn"
      @size-change="sizeChange"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: "ArticleComment",
  data() {
    return {
      pageInfo: {
        pageNum: 1,
        pageSize: 5,
        list: [{}, {}, {}],
        total: 10,
      },
      loading: true,
      articleId: -1,
      title: "",
      articles: [],
    };
  },
  computed: {
    conArticles() {
      const arr = [{ id: -1, title: "全部文章" }];
      return arr.concat(this.articles);
    },
  },
  methods: {
    async getArticleComment() {
      this.loading = true;
      const { pageNum, pageSize } = this.pageInfo;
      const articleId = this.articleId;
      //TODO
      let result = await this.$API.comment.reqCommentList({
        pageNum,
        pageSize,
        articleId,
      });

      this.pageInfo = result.data;
      this.loading = false;
    },

    pageTurn(pageNum) {
      this.pageInfo.pageNum = pageNum;
      this.getArticleComment();
    },
    sizeChange(pageSize) {
      this.pageInfo.pageSize = pageSize;
      this.getArticleComment();
    },
    handleReply() {},
    handleDelete(index, row) {
      this.$confirm(
        `此操作将永久删除ID为【${row.id}】的评论, 是否继续?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(async () => {
        //TODO
        let result = await this.$API.comment.reqDeleteComment(row.id);
        this.$message.success("删除成功");
        this.getArticleComment();
      });
    },
    async getArticleTitle() {
      let result = await this.$API.article.reqArticleTitle(this.title);
      this.articles = result.data;
      this.title = "";
    },
    handlerSelectTitle(value) {
      this.articleId = value;
      this.getArticleComment();
    },
    remoteGetTitle(query) {
      this.title = query;
    },
    remoteGetTitle2() {
      this.getArticleTitle();
    },
  },
  mounted() {
    this.getArticleComment();
    this.getArticleTitle();
  },
};
</script>

<style>
.option {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>