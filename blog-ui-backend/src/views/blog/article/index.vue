<template>
  <el-card>
    <el-button
      type="danger"
      size="small"
      @click="deleteBatch"
      :disabled="!isbatchDelete"
      >批量删除</el-button
    >
    <el-table
      :data="pageInfo.list"
      style="width: 100%"
      ref="table"
      v-loading="loading"
      @select="handlerSelect"
      @select-all="handlerSelect"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column label="ID" prop="id"> </el-table-column>
      <el-table-column label="标题" prop="title"> </el-table-column>
      <el-table-column label="描述" prop="description"> </el-table-column>
      <el-table-column label="版图">
        <template slot-scope="{ row }">
          <img
            :src="row.headImage"
            alt=""
            style="width: 100px; height: 100px"
          />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime"> </el-table-column>
      <el-table-column>
        <template slot="header" slot-scope="scope">
          <el-input
            v-model="keyword"
            size="small"
            placeholder="输入关键字搜索"
            @keyup.enter.native="handlerSearch"
          />
        </template>
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)"
            >编辑</el-button
          >
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
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

    <el-dialog
      title="编辑文章"
      :visible.sync="dialogTableVisible"
      width="50%"
      :fullscreen="true"
    >
      <addOrUpdateArticle
        ref="articleDetail"
        @updateSuccess="dialogTableVisible = false"
      ></addOrUpdateArticle>
    </el-dialog>
  </el-card>
</template>

<script>
import addOrUpdateArticle from "./addOrUpdateArticle";
export default {
  data() {
    return {
      pageInfo: {
        pageNum: 1,
        pageSize: 5,
        list: [],
        total: 10,
      },
      keyword: undefined,
      dialogTableVisible: false,
      loading: false,
      isbatchDelete: false,
    };
  },
  components: {
    addOrUpdateArticle,
  },
  computed: {},
  methods: {
    // 获取文章例表
    async getArticleList(params) {
      this.loading = true;
      const { pageNum, pageSize } = this.pageInfo;
      const keyword = this.keyword;

      let result = await this.$API.article.reqGetArticleList({
        pageNum,
        pageSize,
        keyword,
      });
      if (result.code == 200) {
        this.pageInfo = result.data;
        this.loading = false;
      }
    },
    //翻页
    pageTurn(cahngePageNum) {
      this.pageInfo.pageNum = cahngePageNum;
      this.getArticleList();
    },
    //改变每页显示条数
    sizeChange(size) {
      this.pageInfo.pageSize = size;
      this.getArticleList();
    },

    /**
     *搜索
     */
    handlerSearch() {
      this.getArticleList();
    },

    handleEdit(index, row) {
      this.dialogTableVisible = true;
      this.$nextTick(() => {
        this.$refs.articleDetail.getArticleDetail(row);
      });
    },

    handleDelete(index, row) {
      this.$confirm(
        `此操作将永久删除ID为【${row.id}】的文章, 是否继续?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        this.$API.article.reqdeleteArticle(row.id).then((res) => {
          this.$message.success("删除成功");
          this.getArticleList();
        });
      });
    },

    deleteBatch() {
      const arr = this.$refs.table.selection.map((a) => a.id);
      const ids = arr.join(",");
      this.$confirm(`此操作将永久删除ID为【${ids}】的文章, 是否继续?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(async () => {
        await this.$API.article.reqdeleteArticle(ids);
        this.$message.success("删除成功");
        this.getArticleList();
      });
    },
    handlerSelect(selection, row) {
      if (selection.length >= 1) {
        this.isbatchDelete = true;
      } else {
        this.isbatchDelete = false;
      }
    },
  },
  mounted() {
    const { pageNum, pageSize } = this.pageInfo;
    this.getArticleList({ pageNum, pageSize });
  },
};
</script>