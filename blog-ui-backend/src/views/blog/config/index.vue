<template>
  <el-card>
    <!-- <div slot="header">博客配置</div> -->
    <el-row :gutter="15">
      <el-form
        ref="blogConfigForm"
        :model="blogConfigForm"
        size="medium"
        label-width="100px"
        :rules="rules"
      >
        <el-col :span="18">
          <el-form-item label="首页诗句" prop="poem">
            <el-input
              v-model="blogConfigForm.poem"
              placeholder="请输入首页诗句"
              clearable
              :style="{ width: '100%' }"
            >
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="18">
          <el-form-item label="看板链接" prop="girlUrl" class="boxx">
            <el-input
              v-model.lazy="blogConfigForm.girlUrl"
              placeholder="请输入看板链接"
              clearable
              :style="{ width: '100%' }"
            ></el-input>
            <SingleUpload
              class="upload"
              @successHandler="girlUrlSuccessHandler"
              :tip="false"
            ></SingleUpload>
          </el-form-item>
        </el-col>
        <el-col :span="24" v-show="blogConfigForm.girlUrl">
          <el-form-item>
            <img class="pre-img" :src="blogConfigForm.girlUrl" alt="" />
          </el-form-item>
        </el-col>

        <el-col :span="18">
          <el-form-item label="挂件链接" prop="pendant" class="boxx">
            <el-input
              v-model="blogConfigForm.pendant"
              placeholder="请输入挂件链接"
              clearable
              :style="{ width: '100%' }"
            ></el-input>
            <SingleUpload
              class="upload"
              @successHandler="pendantSuccessHandler"
              :tip="false"
            ></SingleUpload>
          </el-form-item>
        </el-col>
        <el-col :span="18" v-show="blogConfigForm.pendant">
          <el-form-item>
            <img class="pre-img" :src="blogConfigForm.pendant" alt="" />
          </el-form-item>
        </el-col>
        <el-col :span="18">
          <el-form-item label="首页背景" prop="indexBackground" class="boxx">
            <el-input
              v-model="blogConfigForm.indexBackground"
              placeholder="请输入首页背景链接"
              clearable
              :style="{ width: '100%' }"
            ></el-input>
            <SingleUpload
              class="upload"
              @successHandler="indexBackgroundSuccessHandler"
              :tip="false"
            ></SingleUpload>
          </el-form-item>
        </el-col>
        <el-col :span="18" v-show="blogConfigForm.indexBackground">
          <el-form-item>
            <img class="pre-img" :src="blogConfigForm.indexBackground" alt="" />
          </el-form-item>
        </el-col>

        <el-col :span="18">
          <el-form-item label="微信二维码" prop="wechat">
            <el-input
              v-model="blogConfigForm.wechat"
              placeholder="请输入微信链接"
              clearable
              :style="{ width: '100%' }"
            ></el-input>
            <SingleUpload
              class="upload"
              @successHandler="wechatSuccessHandler"
              :tip="false"
            ></SingleUpload>
          </el-form-item>
        </el-col>
        <el-col :span="18" v-show="blogConfigForm.wechat">
          <el-form-item>
            <img class="pre-img" :src="blogConfigForm.wechat" alt="" />
          </el-form-item>
        </el-col>
        <el-col :span="18">
          <el-form-item label="头像链接" prop="userAvatar">
            <el-input
              v-model="blogConfigForm.userAvatar"
              placeholder="请输入微信链接"
              clearable
              :style="{ width: '100%' }"
            ></el-input>
            <SingleUpload
              class="upload"
              @successHandler="userAvatarSuccessHandler"
              :tip="false"
            ></SingleUpload>
          </el-form-item>
        </el-col>
        <el-col :span="18" v-show="blogConfigForm.userAvatar">
          <el-form-item>
            <img class="pre-img" :src="blogConfigForm.userAvatar" alt="" />
          </el-form-item>
        </el-col>
        <el-col :span="20">
          <el-form-item label="关于描述" prop="aboutDescription">
            <el-input
              v-model="blogConfigForm.aboutDescription"
              type="textarea"
              placeholder="关于描述"
              :autosize="{ minRows: 8, maxRows: 8 }"
              :style="{ width: '100%' }"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="18">
          <el-form-item label="站长名称" prop="username">
            <el-input
              v-model="blogConfigForm.username"
              placeholder="请输入站长名称"
              clearable
              :style="{ width: '100%' }"
            >
            </el-input>
          </el-form-item>
        </el-col>

        <el-col :span="18">
          <el-form-item label="邮箱" prop="email">
            <el-input
              v-model="blogConfigForm.email"
              placeholder="请输入邮箱"
              clearable
              :style="{ width: '100%' }"
            >
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="18">
          <el-form-item label="github" prop="github">
            <el-input
              v-model="blogConfigForm.github"
              placeholder="请输入github"
              clearable
              :style="{ width: '100%' }"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="18">
          <el-form-item label="gitee" prop="gitee">
            <el-input
              v-model="blogConfigForm.gitee"
              placeholder="请输入gitee"
              clearable
              :style="{ width: '100%' }"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="18">
          <el-form-item label="qq" prop="qq">
            <el-input
              v-model="blogConfigForm.qq"
              placeholder="请输入qq"
              clearable
              :style="{ width: '100%' }"
            >
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="18">
          <el-form-item size="large">
            <el-button type="primary" @click="submitForm">修改</el-button>
            <!-- <el-button @click="resetForm">重置</el-button> -->
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
  </el-card>
</template>
<script>
import SingleUpload from "@/components/upload/singleUpload.vue";
export default {
  components: { SingleUpload },
  props: [],
  data() {
    return {
      blogConfigForm: {
        id: undefined,
        poem: undefined,
        indexBackground: undefined,
        girlUrl: undefined,
        pendant: undefined,
        aboutDescription: undefined,
        wechat: undefined,
        email: undefined,
        github: undefined,
        gitee: undefined,
        qq: undefined,
        username: undefined,
        userAvatar: undefined,
      },
      rules: {
        poem: [
          {
            required: true,
            message: "请输入首页诗句",
            trigger: "blur",
          },
        ],
        indexBackground: [
          {
            required: true,
            message: "请输入首页背景链接",
            trigger: "blur",
          },
        ],
        girlUrl: [
          {
            required: true,
            message: "请输入看板链接",
            trigger: "blur",
          },
        ],
        pendant: [
          {
            required: true,
            message: "请输入挂件链接",
            trigger: "blur",
          },
        ],
        aboutDescription: [
          {
            required: true,
            message: "关于描述",
            trigger: "blur",
          },
        ],
        wechat: [
          {
            required: true,
            message: "请输入微信链接",
            trigger: "blur",
          },
        ],
        email: [
          {
            required: true,
            message: "请输入邮箱",
            trigger: "blur",
          },
        ],
        github: [
          {
            required: true,
            message: "请输入github",
            trigger: "blur",
          },
        ],
        gitee: [
          {
            required: true,
            message: "请输入gitee",
            trigger: "blur",
          },
        ],
        qq: [
          {
            required: true,
            message: "请输入qq",
            trigger: "blur",
          },
        ],
        userAvatar: [
          {
            required: true,
            message: "请输入头像链接",
            trigger: "blur",
          },
        ],
        username: [
          {
            required: true,
            message: "请输入用户名",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    getBlogConfig() {
      this.$API.blogconfig
        .reqBlogConfig()
        .then((res) => {
          this.blogConfigForm = res.data;
        })
        .catch((err) => {});
    },

    submitForm() {
      this.$refs["blogConfigForm"].validate((valid) => {
        if (!valid) return;
        // TODO 提交表单
        this.$API.blogconfig
          .reqUpdateBlogConfig(this.blogConfigForm)
          .then((result) => {
            this.$message.success("修改成功");
            this.getBlogConfig();
          })
          .catch((err) => {});
      });
    },
    resetForm() {
      this.$refs["blogConfigForm"].resetFields();
    },
    girlUrlSuccessHandler(url) {
      this.blogConfigForm.girlUrl = url;
    },
    pendantSuccessHandler(url) {
      this.blogConfigForm.pendant = url;
    },
    wechatSuccessHandler(url) {
      this.blogConfigForm.wechat = url;
    },
    indexBackgroundSuccessHandler(url) {
      this.blogConfigForm.indexBackground = url;
    },
    userAvatarSuccessHandler(url) {
      this.blogConfigForm.userAvatar = url;
    },
  },
  mounted() {
    this.getBlogConfig();
  },
};
</script>
<style>
.boxx {
  position: relative;
}
.upload {
  position: absolute;
  right: -15%;
  top: 0;
}
.pre-img {
  display: block;
  max-height: 500px;
  max-width: 500px;
}
</style>
