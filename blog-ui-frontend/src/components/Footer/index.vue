<template>
  <div class="footer">
    <div class="author">©2022</div>
    <a class="site" href="https://beian.miit.gov.cn/">粤ICP备2022055391号</a>
    <!-- <a class="site" href="https://beian.miit.gov.cn/">粤ICP备xxxxx号</a> -->
  </div>
</template>

<script>
export default {
  name: "Footer",
};
</script>

<style lang="less" scoped>
.footer {
  // margin-top: 30px;

  // position: fixed;
  // position: absolute;
  position: static;
  // position: relative;

  width: 100%;
  height: 100px;
  font-size: 16px;
  // background-color: rgba(255, 255, 255, 0.15);
  // backdrop-filter: blur(10px);

  text-align: center;
  padding-top: 15px;
  // color: #7ec661;
  color: #fff;
  .author {
    margin-bottom: 15px;
  }
  .site {
    display: block;
    color: #fff;
  }
}
</style>