<template>
  <div>
    <el-table :data="pageInfo.list" style="width: 100%" v-loading="loading">
      <el-table-column prop="prop" type="selection"> </el-table-column>
      <el-table-column prop="id" label="ID" width="width"> </el-table-column>
      <el-table-column prop="userNickname" label="用户" width="width">
      </el-table-column>
      <!-- <el-table-column prop="replyUserNickname" label="回复对象" width="width">
      </el-table-column> -->
      <el-table-column prop="content" label="内容" width="width">
      </el-table-column>
      <el-table-column prop="createTime" label="时间" width="width">
      </el-table-column>
      <el-table-column prop="prop" label="操作" width="width">
        <template slot-scope="scope">
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
          description="还没有留言哦~"
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
    };
  },
  methods: {
    async getCommentMessage() {
      this.loading = true;
      const { pageNum, pageSize } = this.pageInfo;
      //TODO
      let result = await this.$API.comment.reqCommentMessage({
        pageNum,
        pageSize,
      });

      this.pageInfo = result.data;
      this.loading = false;
    },

    pageTurn(pageNum) {
      this.pageInfo.pageNum = pageNum;
      this.getCommentMessage();
    },
    sizeChange(pageSize) {
      this.pageInfo.pageSize = pageSize;
      this.getCommentMessage();
    },
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
        this.getCommentMessage();
      });
    },
  },
  mounted() {
    this.getCommentMessage();
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