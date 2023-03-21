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
      <el-table-column type="selection" width="50" align="center" />
      <!-- <el-table-column label="ID" prop="id" width="50" align="center"> </el-table-column> -->
      <el-table-column
        label="公告标题"
        prop="noticeTitle"
        width="150"
        align="center"
      >
      </el-table-column>
      <el-table-column
        label="公告类型"
        prop="noticeType"
        width="150"
        align="center"
      >
        <template slot-scope="scope">
          <el-tag :type="scope.row.noticeType == 1 ? 'warning' : 'success'">{{
            scope.row.noticeType == 1 ? "通知" : "公告"
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="公告内容"
        prop="noticeContent"
        width="noticeContent"
        align="center"
      >
      </el-table-column>
      <el-table-column
        label="公告状态"
        prop="status"
        width="100"
        align="center"
      >
        <template slot-scope="scope">
          <el-tag :type="scope.row.status == 0 ? 'success' : 'danger'">{{
            scope.row.status == 0 ? "正常" : "关闭"
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        prop="createTime"
        width="width"
        align="center"
      >
      </el-table-column>
      <el-table-column label="操作" width="300" align="center">
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

    <!-- 添加或修改公告对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      @close="cancel"
    >
      <el-form
        ref="noticeForm"
        :model="noticeForm"
        :rules="rules"
        size="medium"
        label-width="100px"
      >
        <el-form-item label="公告标题" prop="noticeTitle">
          <el-input
            v-model="noticeForm.noticeTitle"
            placeholder="请输入公告标题"
            :style="{ width: '100%' }"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="公告内容" prop="noticeContent">
          <el-input
            v-model="noticeForm.noticeContent"
            type="textarea"
            placeholder="请输入公告内容"
            :autosize="{ minRows: 4, maxRows: 4 }"
            :style="{ width: '100%' }"
          ></el-input>
        </el-form-item>
        <el-form-item label="公告类型" prop="noticeType">
          <el-radio-group v-model="noticeForm.noticeType" size="medium">
            <el-radio label="1">通知</el-radio>
            <el-radio label="2">公告</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态">
          <el-switch
            v-model="noticeForm.status"
            active-color="#13ce66"
            inactive-color="#ff4949"
            inactive-value="1"
            active-value="0"
          >
          </el-switch>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="noticeForm.remark"
            type="textarea"
            placeholder="请输入备注"
            :autosize="{ minRows: 4, maxRows: 4 }"
            :style="{ width: '100%' }"
          ></el-input>
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
      //用户表单
      noticeForm: {
        noticeId: undefined,
        noticeTitle: undefined,
        noticeType: "1",
        noticeContent: undefined,
        status: undefined,
        createBy: undefined,
        createTime: undefined,
        remark: undefined,
      },

      loading: true,
      title: "添加公告",
      // 是否显示弹出层
      open: false,
      // 修改/添加用户的表单验证规则
      rules: {
        username: [
          { required: true, message: "用户名称不能为空", trigger: "blur" },
        ],
        password: [
          { required: true, message: "用户密码不能为空", trigger: "blur" },
        ],
        nickname: [
          { required: true, message: "用户昵称不能为空", trigger: "blur" },
        ],
      },
    };
  },
  computed: {
    idSave() {
      return this.title.includes("添加");
    },
  },

  methods: {
    async getNoticePage() {
      this.loading = true;
      const { pageNum, pageSize } = this.pageInfo;
      let result = await this.$API.notice.reqNoticeList({
        pageNum,
        pageSize,
      });
      this.pageInfo = result.data;
      this.loading = false;
    },

    //翻页
    pageTurn(cahngePageNum) {
      this.pageInfo.pageNum = cahngePageNum;
      this.getNoticePage();
    },
    //改变每页显示条数
    sizeChange(size) {
      this.pageInfo.pageSize = size;
      this.getNoticePage();
    },
    /** 删除按钮 */
    handleDelete(index, row) {
      this.$confirm(
        `此操作将永久删除ID为【${row.noticeId}】的数据, 是否继续?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        this.$API.notice.reqDeleteNotice(row.noticeId).then((res) => {
          this.$message.success("删除成功");
          this.getNoticePage();
        });
      });
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.title = "添加公告";
      this.open = true;
    },

    async handleEdit(index, row) {
      this.title = "修改公告";
      this.open = true;
      let result = await this.$API.notice.reqNoticeById(row.noticeId);
      this.noticeForm = result.data;
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
      this.noticeForm = {
        noticeId: undefined,
        noticeTitle: undefined,
        noticeType: "1",
        noticeContent: undefined,
        status: undefined,
        createBy: undefined,
        createTime: undefined,
        remark: undefined,
      };
    },

    //提交表单
    submitForm() {
      this.$refs["noticeForm"].validate(async (valid) => {
        console.log(this.noticeForm);
        if (valid) {
          if (this.noticeForm.noticeId != undefined) {
            let result = await this.$API.notice.reqUpdateNotice(
              this.noticeForm
            );
            this.$message.success("修改成功");
            this.open = false;
            this.getNoticePage();
          } else {
            let result = await this.$API.notice.reqSaveNotice(this.noticeForm);
            this.$message.success("添加成功");
            this.open = false;
            this.getNoticePage();
          }
        }
      });
    },
  },
  mounted() {
    this.getNoticePage();
  },
};
</script>

<style>
</style>