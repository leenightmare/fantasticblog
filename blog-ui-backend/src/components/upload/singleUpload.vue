<template>
  <div>
    <el-upload
      action=""
      :http-request="uploadHandler"
      list-type="picture"
      :show-file-list="showFileList"
      :file-list="fileList"
      :on-remove="handleRemove"
      :on-preview="handlePreview"
    >
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip" v-show="tip">
        只能上传jpg/png文件，且不超过10MB
      </div>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="fileList[0].url" alt="" />
    </el-dialog>
  </div>
</template>
<script>
import { reqPreFileUploadURL, reqFileUploadToOSS } from "@/api/file";
export default {
  name: "singleUpload",
  props: {
    value: String,
    tip: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      dialogVisible: false,
    };
  },
  computed: {
    imageUrl() {
      return this.value;
    },
    imageName() {
      if (this.value != null && this.value !== "") {
        return this.value.substr(this.value.lastIndexOf("/") + 1);
      } else {
        return null;
      }
    },
    fileList() {
      return [
        {
          name: this.imageName,
          url: this.imageUrl,
        },
      ];
    },
    showFileList: {
      get: function () {
        return (
          this.value !== null && this.value !== "" && this.value !== undefined
        );
      },
      set: function (newValue) {},
    },
  },

  methods: {
    // 文件上传
    async uploadHandler(res) {
      let file = res.file;
      let fileName = file.name;
      const contentType = this.getContentType(this.getSuffixName(fileName));
      reqPreFileUploadURL(fileName).then((resolve, rej) => {
        let uploadUrl = resolve.uploadUrl;
        reqFileUploadToOSS({
          url: uploadUrl,
          method: "PUT",
          data: file,
          headers: {
            // "Content-Type": "multipart/form-data",
            "Content-Type": contentType,
          },
        }).then((resp) => {
          console.log(resp);
          let requestUrl = resp.config.url;
          let arr = requestUrl.split("?");
          let imgUrl = arr[0];

          this.showFileList = true;
          this.fileList.pop();
          this.fileList.push({
            name: fileName,
            url: imgUrl,
          });
          this.emitInput(this.fileList[0].url);
          this.$emit("successHandler", this.fileList[0].url);
        });
      });
    },
    emitInput(val) {
      this.$emit("input", val);
    },
    handleRemove(file, fileList) {
      this.emitInput("");
    },
    handlePreview(file) {
      this.dialogVisible = true;
    },

    getSuffixName(fileName) {
      let suffix = fileName.substr(fileName.lastIndexOf("."));
      return suffix;
    },
    getContentType(suffix) {
      if (suffix == ".jpg") {
        return "image/jpg";
      } else if (suffix == ".png") {
        return "image/png";
      } else if (suffix == ".jpeg") {
        return "image/jpeg";
      } else if (suffix == ".webp") {
        return "image/webp";
      } else {
        return "multipart/form-data";
      }
    },
  },
};
</script>
<style>
</style>


