<template>
  <el-card>
    <el-form :model="articleForm" label-width="100px" label-position="left">
      <el-form-item label="文章标题" label-width="100px">
        <el-input v-model="articleForm.title" boxShadowStyle="none"></el-input
      ></el-form-item>
      <el-form-item label="文章描述">
        <el-input type="textarea" v-model="articleForm.description"></el-input
      ></el-form-item>
      <el-form-item label="文章内容" class="el-icon-edit">
        <el-upload
          action=""
          :http-request="articlefileHandler"
          :file-list="fileList"
          :on-change="handleFileChange"
          :before-upload="checkFile"
          :show-file-list="true"
        >
          <el-button size="small" type="info" style=""
            >从本地上传文章</el-button
          >
        </el-upload>
      </el-form-item>
      <mavonEditor
        v-model="articleForm.content"
        style="margin: 15px 0"
      ></mavonEditor>

      <el-form-item label="文章版图">
        <SingleUpload v-model="articleForm.headImage"></SingleUpload>
      </el-form-item>
      <el-form-item label="文章分类">
        <el-select v-model="articleForm.categoryId" placeholder="请选择">
          <el-option
            v-for="item in categoryList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="文章标签">
        <el-select v-model="articleForm.tagList" placeholder="请选择" multiple>
          <el-option
            v-for="(item, index) in tagList"
            :key="index"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否开启评论">
        <el-switch
          v-model="articleForm.isComment"
          active-color="#13ce66"
          inactive-color="#ff4949"
          :active-value="0"
          :inactive-value="1"
        >
        </el-switch>
      </el-form-item>
      <el-form-item label="是否置顶">
        <el-switch
          v-model="articleForm.isTop"
          active-color="#13ce66"
          inactive-color="#ff4949"
          :active-value="0"
          :inactive-value="1"
        >
        </el-switch>
      </el-form-item>

      <el-button type="primary" @click="submitArticle">发布</el-button>
    </el-form>
  </el-card>
</template>

<script>
import { mavonEditor } from "mavon-editor";
import SingleUpload from "@/components/upload/singleUpload.vue";
import "mavon-editor/dist/css/index.css";
import { mapState } from "vuex";
export default {
  data() {
    return {
      //要提交的表单
      articleFormNew: {
        id: "",
        title: "",
        description: "",
        headImage: "",
        content: "",
        userId: "",
        userNickname: "",
        userAvatar: "",
        categoryId: "",
        isComment: "",
        isTop: "",
        isExtra: "",
        status: "",
        tagList: [],
      },
      //回显的表单
      articleForm: {
        id: undefined,
        categoryId: undefined,
        content: undefined,
        description: undefined,
        headImage: undefined,
        isComment: 0,
        isExtra: undefined,
        isTop: undefined,
        status: 0,
        title: undefined,
        tagList: [],
      },
      categoryList: [],
      tagList: [],
      fileList: [],
    };
  },
  props: ["dialogTableVisible"],

  components: { mavonEditor, SingleUpload },
  computed: {
    ...mapState("user", ["id", "nickname", "avatar"]),
  },
  methods: {
    articlefileHandler(fileobj) {
      let file = fileobj.file;
      let fileName = file.name;
      const reader = new FileReader();
      reader.readAsText(file);
      reader.onload = (readRes) => {
        this.articleForm.content = readRes.target.result;
        this.articleForm.title = fileName.substring(
          0,
          fileName.lastIndexOf(".")
        );
      };
      reader.onerror = (err) => {
        console.log(err);
      };
      return;
    },

    checkFile(file) {
      // 截取文件名后缀
      let fileName = file.name;
      let pos = fileName.lastIndexOf(".");
      let lastName = fileName.substring(pos, fileName.length);
      if (
        lastName.toLowerCase() !== ".md" &&
        lastName.toLowerCase() != ".txt"
      ) {
        this.$message.error("文件必须为.txt或者.md类型");
        return false;
        // 清空文件列表
        // const newFileList = this.fileList.slice();
        // newFileList.splice(0, 1);
        // this.fileList = newFileList;
      }
      return true;
    },
    handleFileChange(file, fileLists) {
      this.fileList = fileLists;
      if (fileLists.length >= 2) {
        this.fileList.shift();
      }
    },
    async submitArticle() {
      if (!this.articleForm.id) {
        console.log("添加文章");
        console.log(this.articleForm);
        let result = await this.$API.article.reqSaveArticle(this.articleForm);
        this.$message.success("发表成功");
        this.articleForm = this.$options.data().articleForm;
      } else {
        console.log("修改文章");
        console.log(this.articleForm);
        let result = await this.$API.article.reqUpdateArticle(this.articleForm);
        this.$message.success("修改成功");
        this.articleForm = this.$options.data().articleForm;
      }
      this.$emit("updateSuccess");
    },

    /**
     *  从外部触发的事件，获取文章详情，用于修改文章
     */
    async getArticleDetail(article) {
      let result = await this.$API.article.reqGetArticleDetail(article.id);
      this.articleForm = result.data;
    },
  },
  mounted() {
    this.$store.dispatch("category/getCategoryList").then((res) => {
      this.categoryList = res;
    });
    this.$store.dispatch("tag/getTagList").then((res) => {
      this.tagList = res;
    });
  },
};
</script>

<style>
</style>
