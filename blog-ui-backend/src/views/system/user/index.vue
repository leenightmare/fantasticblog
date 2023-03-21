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
    <el-table :data="pageInfo.list" style="width: 100%" v-loading="loading" >
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="ID" prop="id" width="50" align="center"> </el-table-column>
      <el-table-column label="用户名" prop="username" width="150" align="center">
      </el-table-column>
      <el-table-column label="用户昵称" prop="nickname" width="150" align="center">
      </el-table-column>
      <el-table-column label="角色" width="300" align="center">
        <template slot-scope="scope">
          <el-tag
            :type="role.roleId == 1 ? 'warning' : 'success'"
            v-for="role in scope.row.roles"
            :key="role.roleId"
            >{{ role.roleName }}</el-tag
          >
        </template>
      </el-table-column>

      <el-table-column label="状态" prop="status" width="100" align="center">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-color="#13ce66"
            inactive-color="#ff4949"
            :inactive-value="1"
            :active-value="0"
            @change="statusHandler(scope.$index, scope.row)"
          >
          </el-switch>
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
            v-show="scope.row.id != 1"
            >删除</el-button
          >
          <el-button
            size="mini"
            type="primary"
            plain
            @click="handleRole(scope.$index, scope.row)"
            v-show="scope.row.id != 1"
            >分配角色</el-button
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

    <!-- 添加或修改用户对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      @close="cancel"
    >
      <el-form
        ref="userForm"
        :model="userForm"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item
          label="用户名称"
          prop="username"
          v-if="userForm.id == undefined"
        >
          <el-input v-model="userForm.username" placeholder="请输入用户名称" />
        </el-form-item>
        <el-form-item
          label="用户密码"
          prop="password"
          v-if="userForm.id == undefined"
        >
          <el-input
            v-model="userForm.password"
            placeholder="请输入密码"
            type="password"
            maxlength="20"
            show-password
          />
        </el-form-item>
        <el-form-item label="用户昵称" prop="nickname">
          <el-input v-model="userForm.nickname" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="用户邮箱" prop="email">
          <el-input v-model="userForm.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="用户头像" prop="avatar">
          <SingleUpload v-model="userForm.avatar"></SingleUpload>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio v-model="userForm.sex" :label="0">男</el-radio>
          <el-radio v-model="userForm.sex" :label="1">女</el-radio>
        </el-form-item>
        <el-form-item label="是否启用" prop="status">
          <el-switch
            v-model="userForm.status"
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

    <!-- 分配角色对话框 -->
    <el-dialog
      title="分配角色"
      :visible.sync="openRole"
      width="500px"
      append-to-body
      @close="cloeRloe"
    >
      <el-checkbox-group v-model="rolesForm.roleIds">
        <el-checkbox
          :label="role.roleId"
          v-for="role in roles"
          :key="role.id"
          >{{ role.roleName }}</el-checkbox
        >
      </el-checkbox-group>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handlerDivideRole">确 定</el-button>
        <el-button @click="cloeRloe">取 消</el-button>
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
      userForm: {
        id: undefined,
        username: undefined,
        nickname: undefined,
        avatar: undefined,
        password: undefined,
        email: undefined,
        createTime: undefined,
        sex: 0,
        status: 0,
        delFlag: undefined,
        password: undefined,
      },

      loading: true,
      title: "添加用户",
      // 是否显示弹出层
      open: false,
      openRole: false,
      //分配角色时的表单
      rolesForm: {
        userId: undefined,
        roleIds: [],
      },

      //获取的所有角色列表
      roles: [],

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
  components: {
    SingleUpload,
  },
  computed: {
    idSave() {
      return this.title.includes("添加");
    },
  },

  methods: {
    async getUserPage() {
      this.loading = true;
      const { pageNum, pageSize } = this.pageInfo;
      let result = await this.$API.user.reqUserPage({
        pageNum,
        pageSize,
      });
      this.pageInfo = result.data;
      this.loading = false;
    },

    //翻页
    pageTurn(cahngePageNum) {
      this.pageInfo.pageNum = cahngePageNum;
      this.getUserPage();
    },
    //改变每页显示条数
    sizeChange(size) {
      this.pageInfo.pageSize = size;
      this.getUserPage();
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
        let result = await this.$API.user.reqDeleteUser(row.id);
        this.$message("删除成功");
        this.getUserPage();
      });
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.title = "添加用户";
      this.open = true;
    },

    async handleEdit(index, row) {
      this.title = "修改用户信息";
      this.open = true;
      let result = await this.$API.user.reqUserDetailForEdit(row.id);
      this.userForm = result.data;
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
      this.userForm = {
        id: undefined,
        username: undefined,
        nickname: undefined,
        avatar: undefined,
        password: undefined,
        email: undefined,
        createTime: undefined,
        sex: 0,
        status: 0,
        delFlag: undefined,
      };
    },

    //提交表单
    submitForm() {
      this.$refs["userForm"].validate(async (valid) => {
        if (valid) {
          if (this.userForm.id != undefined) {
            let result = await this.$API.user.reqUpdateUser(this.userForm);
            this.$message.success("修改成功");
            this.open = false;
            this.getUserPage();
          } else {
            let result = await this.$API.user.reqSaveUser(this.userForm);
            this.$message.success("添加成功");
            this.open = false;
            this.getUserPage();
          }
        }
      });
    },

    /**
     *修改状态
     */
    async statusHandler(index, row) {
      const obj = {
        id: row.id,
        status: row.status,
      };
      let result = await this.$API.user.reqUpdateUser(obj);
      this.$message.success("状态修改成功");
    },

    /**
     *分配角色
     */
    async handleRole(index, row) {
      this.openRole = true;
      //获取用户带角色
      let result = await this.$API.user.reqAutheUser(row.id);
      this.rolesForm.roleIds = result.data.roles.map((r) => r.roleId);
      this.rolesForm.userId = result.data.user.id;
    },

    //获取角色列表
    async getRoles() {
      let result = await this.$API.role.getRolesList();
      this.roles = result.data;
    },

    //关闭角色分配对话
    cloeRloe() {
      this.openRole = false;
      this.rolesForm = {
        userId: undefined,
        roleIds: [],
      };
    },

    //提交分配角色
    async handlerDivideRole() {
      const { roleIds, userId } = this.rolesForm;

      await this.$API.role.getSaveRoleForUser(roleIds, userId);
      this.$message.success("操作成功");
      this.openRole = false;
    },
  },
  mounted() {
    this.getUserPage();
    //获取角色列表
    this.getRoles();
  },
};
</script>

<style>
</style>