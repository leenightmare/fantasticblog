<template>
  <div class="dashboard-container">
    <div class="dashboard-text">功能开发中</div>
    <div class="block">
      <el-timeline>
        <el-timeline-item timestamp="2022/12/15" placement="top">
          <el-card>
            <h4>博客系统2.0更新</h4>
            <p>Admin提交于2022/12/15 21:06</p>
          </el-card>
        </el-timeline-item>
        <el-timeline-item timestamp="2022/10/10" placement="top">
          <el-card>
            <h4>博客系统正式发布1.0</h4>
            <p>Admin提交于2022/10/10 20:46</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>
    <div class="info">
      <h3>微信联系我</h3>
      <img src="~@/assets/pic/weixin.jpg" alt="" class="weixin" />
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "Dashboard",
  computed: {
    ...mapGetters(["name"]),
  },
};
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
    // height: 94vh;
    // width: 100%;
    // // background-image: url("https://img0.baidu.com/it/u=1098232326,4159441341&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500");
    // background-position: center;
    // background-repeat: no-repeat;
    // background-size: cover;
  }
  &-text {
    margin-top: 10px;
    display: flex;
    justify-content: center;
    font-size: 30px;
    line-height: 46px;
  }
  // &-bg {
  //   height: 100vh;
  //   background-color: pink;
  // }
}
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 480px;
}

.info {
  display: flex;
  justify-content: center;
  align-items: center;
}

.weixin {
  width: 240px;
  height: 240px;
}
</style>
