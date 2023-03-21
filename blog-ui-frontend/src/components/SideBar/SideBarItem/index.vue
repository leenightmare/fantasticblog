<template>
  <div class="sidebar">
    <div class="title">
      <slot name="icon"></slot>
      {{ title }}
    </div>
    <!-- <div class="divider"></div> -->
    <slot name="content"></slot>
  </div>
</template>

<script>
export default {
  name: "SideBarItem",
  props: ["title"],
};
</script>

<style lang="less" scoped>
@baseFont: 96;

/deep/ .el-divider--horizontal {
  margin: 10px 0;
}
.divider {
  display: block;
  height: 1px;
  width: 100%;
  margin: 15px 0;
  background-color: #dcdfe6;
  position: relative;
}

.sidebar {
  background-color: #fff;
  padding: 10px;
  margin-bottom: 20px;
  overflow: hidden;
  box-shadow: 0 0px 3px 0px gray;
  // border: 1px solid #000;
  .title {
    // text-align: center;
    font-size: 18px;
    // margin-top: 15px;
    padding: (20rem / @baseFont) 0;
    letter-spacing: 1px;
    color: #999;
  }
}
</style>