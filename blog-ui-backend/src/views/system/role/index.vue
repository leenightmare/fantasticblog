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
      <el-table-column label="ID" prop="roleId" width="width">
      </el-table-column>
      <el-table-column label="名称" prop="roleName" width="width">
      </el-table-column>
      <el-table-column label="权限字符" prop="roleKey" width="width">
      </el-table-column>
      <el-table-column label="显示顺序" prop="roleSort" width="width">
      </el-table-column>
      <el-table-column label="状态" prop="status" width="width">
      </el-table-column>
      <el-table-column label="操作" prop="label" width="width" align="center">
        <template slot-scope="scope" v-if="scope.row.roleId != 1">
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

    <!-- 添加或修改角色配置对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      @close="cancel"
    >
      <el-form
        ref="roleForm"
        :model="roleForm"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="roleForm.roleName" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item prop="roleKey">
          <span slot="label">
            <el-tooltip
              content="控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasRole('admin')`)"
              placement="top"
            >
              <i class="el-icon-question"></i>
            </el-tooltip>
            权限字符
          </span>
          <el-input v-model="roleForm.roleKey" placeholder="请输入权限字符" />
        </el-form-item>
        <el-form-item label="角色顺序" prop="roleSort">
          <el-input-number
            v-model="roleForm.roleSort"
            controls-position="right"
            :min="0"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="roleForm.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单权限">
          <el-tree
            class="tree-border"
            :data="menuOptions"
            show-checkbox
            ref="menu"
            node-key="menuId"
            empty-text="加载中，请稍候"
            :props="defaultProps"
            v-model="roleForm.menuIds"
            @check="selectHandler"
          ></el-tree>
        </el-form-item>
        <el-form-item label="备注">
          <el-input
            v-model="roleForm.remark"
            type="textarea"
            placeholder="请输入内容"
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
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  data() {
    return {
      pageInfo: {
        pageNum: 1,
        pageSize: 5,
        list: [{}, {}, {}],
        total: 10,
      },

      roleForm: {
        roleId: undefined,
        roleName: undefined,
        roleKey: undefined,
        roleSort: 0,
        status: "0",
        menuIds: [],
        remark: undefined,
      },
      menuOptions: [],
      defaultProps: {
        children: "children",
        label: "menuName",
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
  components: { Treeselect },
  methods: {
    selectHandler(node, checkedNodes) {
      // this.roleForm.menuIds = checkedNodes.checkedKeys;
      //   console.log(allChecked);
    },

    async getRolePage() {
      this.loading = true;
      const { pageNum, pageSize } = this.pageInfo;
      let result = await this.$API.role.reqRolePage({
        pageNum,
        pageSize,
      });
      this.pageInfo = result.data;
      this.loading = false;
    },

    async getTreeselect() {
      let result = await this.$API.menu.reqTreeselect();
      this.menuOptions = result.data;
    },

    /** 转换菜单数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.menuId,
        label: node.menuName,
        children: node.children,
      };
    },

    //翻页
    pageTurn(cahngePageNum) {
      this.pageInfo.pageNum = cahngePageNum;
      this.getRolePage();
    },
    //改变每页显示条数
    sizeChange(size) {
      this.pageInfo.pageSize = size;
      this.getRolePage();
    },
    /** 删除按钮 */
    handleDelete(index, row) {
      this.$confirm(
        `此操作将永久删除ID为【${row.roleId}】的数据, 是否继续?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(async () => {
        let result = await this.$API.role.reqDeleteRole(row.roleId);
        this.$message("删除成功");
        this.getRolePage();
      });
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.title = "添加分类";
      this.open = true;
    },

    async handleEdit(index, row) {
      this.open = true;
      this.title = "修改分类";

      let result = await this.$API.role.reqRoleInfo(row.roleId);
      this.roleForm = result.data;
      this.$refs.menu.setCheckedKeys(this.roleForm.menuIds);
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
      this.roleForm = {
        roleId: undefined,
        roleName: undefined,
        roleKey: undefined,
        roleSort: 0,
        status: "0",
        menuIds: [],
        remark: undefined,
      };
    },

    //提交表单
    submitForm() {
      this.$refs["roleForm"].validate(async (valid) => {
        if (valid) {
          let halfCheckedKeys = this.$refs.menu.getHalfCheckedKeys();
          let checkedKeys = this.$refs.menu.getCheckedKeys();
          let allChecked = halfCheckedKeys.concat(checkedKeys);
          this.roleForm.menuIds = allChecked;

          console.log(this.roleForm);
          if (this.roleForm.roleId != undefined) {
            let result = await this.$API.role.reqUpdateRole(this.roleForm);
            this.$message.success("修改成功");
            this.open = false;
            this.getRolePage();
          } else {
            let result = await this.$API.role.reqSaveRole(this.roleForm);
            this.$message.success("添加成功");
            this.open = false;
            this.getRolePage();
          }
        }
      });
    },
  },
  mounted() {
    this.getRolePage();
    this.getTreeselect();
  },
};
</script>

<style>
</style>