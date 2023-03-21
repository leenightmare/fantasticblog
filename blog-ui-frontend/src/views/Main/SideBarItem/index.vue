<template>
  <div class="sidebar">
    <div class="title">{{ title }}</div>
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
.sidebar {
  background-color: #fff;
  padding: 10px;
  margin-bottom: 20px;
  .title {
    text-align: center;
    font-size: 20px;
    padding-top: (15rem / @baseFont);
    letter-spacing: 1px;
  }
  .content {
    padding: 10px;
    margin-top: 10px;
    font-size: 15px;
    line-height: 2;
  }
}
</style>