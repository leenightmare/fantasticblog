const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  // 关闭lint提示
  lintOnSave: false,
  devServer: {
    proxy: {
      "/api": {
        target: "http://localhost:9999",
        pathRewrite: { "^/api": "" }
      }
    }
  },
})
