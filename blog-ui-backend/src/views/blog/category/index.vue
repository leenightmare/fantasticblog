<template>
  <el-card>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          @click="handleBatchDelete"
          >删除</el-button
        >
      </el-col>
    </el-row>
    <el-table :data="pageInfo.list" style="width: 100%" v-loading="loading">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" prop="id" width="width"> </el-table-column>
      <el-table-column label="名称" prop="name" width="width">
      </el-table-column>
      <el-table-column label="URL" prop="url" width="width"> </el-table-column>
      <el-table-column label="描述" prop="description" width="width">
      </el-table-column>
      <el-table-column label="状态" prop="delFlag" width="width">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.delFlag == 0">正常</el-tag>
          <el-tag v-else type="danger">停用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" prop="label" width="width" align="center">
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

    <!-- 添加或修改岗位对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      @close="cancel"
    >
      <el-form
        ref="categoryForm"
        :model="categoryForm"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="categoryForm.name" placeholder="请输入岗位名称" />
        </el-form-item>
        <el-form-item label="分类URL" prop="url">
          <el-input v-model="categoryForm.url" placeholder="请输入编码名称" />
        </el-form-item>
        <el-form-item label="分类描述" prop="description">
          <el-input
            v-model="categoryForm.description"
            placeholder="请输入分类描述"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
      pageInfo: {
        pageNum: 1,
        pageSize: 5,
        list: [{}, {}, {}],
        total: 10,
      },
      categoryForm: {
        id: undefined,
        name: undefined,
        url: undefined,
        description: undefined,
      },

      loading: true,
      title: "添加分类",
      // 是否显示弹出层
      open: false,
      rules: {
        name: [
          { required: true, message: "岗位名称不能为空", trigger: "blur" },
        ],
        url: [{ required: true, message: "岗位编码不能为空", trigger: "blur" }],
        description: [
          { required: true, message: "岗位顺序不能为空", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    async getCategoryPage() {
      this.loading = true;
      const { pageNum, pageSize } = this.pageInfo;
      let result = await this.$API.category.reqCategoryPage({
        pageNum,
        pageSize,
      });
      this.pageInfo = result.data;
      this.loading = false;
    },

    //翻页
    pageTurn(cahngePageNum) {
      this.pageInfo.pageNum = cahngePageNum;
      this.getCategoryPage();
    },
    //改变每页显示条数
    sizeChange(size) {
      this.pageInfo.pageSize = size;
      this.getCategoryPage();
    },
    /** 删除按钮 */
    handleDelete(index, row) {
      this.$confirm(
        `此操作将永久删除ID为【${row.id}】的数据, 是否继续?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(async () => {
        let result = await this.$API.category.reqDeleteCategory(row.id);
        this.$message("删除成功");
        this.getCategoryPage();
      });
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.title = "添加分类";
      this.open = true;
    },

    handleEdit(index, row) {
      this.open = true;
      this.title = "修改分类";
      this.categoryForm.id = row.id;
      this.categoryForm.name = row.name;
      this.categoryForm.url = row.url;
      this.categoryForm.description = row.description;
    },
    /** 批量按钮操作 */
    handleBatchDelete() {},

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.categoryForm = {
        id: undefined,
        name: undefined,
        url: undefined,
        description: undefined,
      };
      // this.resetForm("categoryForm");
    },

    //提交表单
    submitForm() {
      this.$refs["categoryForm"].validate(async (valid) => {
        if (valid) {
          if (this.categoryForm.id != undefined) {
            // updatePost(this.form).then((response) => {
            //   this.$modal.msgSuccess("修改成功");
            //   this.open = false;
            //   this.getList();
            // });
            // console.log(this.categoryForm);
            let result = await this.$API.category.reqUpdateCategory(
              this.categoryForm
            );
            this.$message.success("添加成功");
            this.open = false;
            this.getCategoryPage();
          } else {
            let result = await this.$API.category.reqSaveCategory(
              this.categoryForm
            );
            this.$message.success("修改成功");
            this.open = false;
            this.getCategoryPage();
          }
        }
      });
    },
  },
  mounted() {
    this.getCategoryPage();
  },
};
</script>

<style>
</style>