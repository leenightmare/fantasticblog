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
      <el-table-column label="ID" prop="id" width="50" align="center">
      </el-table-column>
      <el-table-column label="内容" prop="content" width="" align="center">
      </el-table-column>
      <el-table-column label="类型" prop="type" width="150" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.type == 0 ? 'success' : 'warning'">{{
            scope.row.type == 0 ? "闲聊" : "说说"
          }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="状态" prop="status" width="100" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status == 0 ? 'success' : 'danger'">{{
            scope.row.status == 0 ? "正常" : "关闭"
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="链接" prop="url" width="" align="center">
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
        ref="chatForm"
        :model="chatForm"
        :rules="rules"
        size="medium"
        label-width="100px"
      >
        <el-form-item label="内容" prop="noticeContent">
          <el-input
            v-model="chatForm.content"
            type="textarea"
            placeholder="请输入内容"
            :autosize="{ minRows: 4, maxRows: 4 }"
            :style="{ width: '100%' }"
          ></el-input>
        </el-form-item>

        <el-form-item label="类型" prop="noticeType">
          <el-radio-group v-model="chatForm.type" size="medium">
            <el-radio :label="0">闲聊</el-radio>
            <el-radio :label="1">说说</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态">
          <el-switch
            v-model="chatForm.status"
            active-color="#13ce66"
            inactive-color="#ff4949"
            :inactive-value="1"
            :active-value="0"
          >
          </el-switch>
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
import SingleUpload from "@/components/upload/singleUpload.vue";
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
      chatForm: {
        id: undefined,
        type: undefined,
        content: undefined,
        url: undefined,
        status: undefined,
        createTime: undefined,
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
  components: { SingleUpload },
  computed: {
    idSave() {
      return this.title.includes("添加");
    },
  },

  methods: {
    async getChatPage() {
      this.loading = true;
      const { pageNum, pageSize } = this.pageInfo;
      let result = await this.$API.chat.reqChatList({
        pageNum,
        pageSize,
      });
      this.pageInfo = result.data;
      this.loading = false;
    },

    //翻页
    pageTurn(cahngePageNum) {
      this.pageInfo.pageNum = cahngePageNum;
      this.getChatPage();
    },
    //改变每页显示条数
    sizeChange(size) {
      this.pageInfo.pageSize = size;
      this.getChatPage();
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
      ).then(() => {
        this.$API.chat.reqDeleteChat(row.id).then((res) => {
          this.$message.success("删除成功");
          this.getChatPage();
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
      let result = await this.$API.chat.reqChatById(row.id);
      this.chatForm = result.data;
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
      this.chatForm = {
        id: undefined,
        type: undefined,
        content: undefined,
        url: undefined,
        status: undefined,
        createTime: undefined,
      };
    },

    //提交表单
    submitForm() {
      this.$refs["chatForm"].validate(async (valid) => {
        console.log(this.chatForm);
        if (valid) {
          if (this.chatForm.id != undefined) {
            let result = await this.$API.chat.reqUpdateChat(this.chatForm);
            this.$message.success("修改成功");
            this.open = false;
            this.getChatPage();
          } else {
            let result = await this.$API.chat.reqSaveChat(this.chatForm);
            this.$message.success("添加成功");
            this.open = false;
            this.getChatPage();
          }
        }
      });
    },
  },
  mounted() {
    this.getChatPage();
  },
};
</script>

<style>
</style>