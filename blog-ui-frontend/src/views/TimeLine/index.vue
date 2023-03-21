<template>
  <div class="timeline container">
    <div id="box">
      <div class="timeline-box">
        <el-skeleton :rows="10" animated :loading="loading" />
        <TimeLineItem
          v-for="(article, index) in timeLineInfo"
          :key="index"
          :timeLineArticle="article"
        ></TimeLineItem>
      </div>
      <SideBar class="sidebar"></SideBar>
    </div>
  </div>
</template>

<script>
import TimeLineItem from "./TimeLineItem";
export default {
  name: "TimeLine",
  components: { TimeLineItem },
  data() {
    return {
      loading: true,
      timeLineInfo: [],
    };
  },
  methods: {
    async getTimeLineArticle() {
      this.loading = true;
      let result = await this.$API.article.reqTimeLineArticle();
      console.log(result);
      if (result.code == 200) {
        this.timeLineInfo = result.data;
        this.loading = false;
      } else {
        Promise.reject(new Error("获取归档数据失败"));
      }
    },
  },
  mounted() {
    this.getTimeLineArticle();
  },
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/variable.less";

.timeline {
  margin: 100px auto;
  min-height: calc(100vh - var(--footerHeight));

  #box {
    display: flex;
    align-items: flex-start;

    .timeline-box {
      background-color: #fff;
      box-shadow: 0px 0px 3px 0px gray;
      flex: 8;
      border-radius: 5px;
    }
    .sidebar {
      flex: 2;
    }
  }
}
</style>