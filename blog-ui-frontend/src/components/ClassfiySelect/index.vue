<template>
  <div class="box">
    <div class="top-banner">
      <div class="top-label iconfont icon-biaoqian1">{{ title }}</div>
      <div class="top-count"></div>
    </div>
    <div class="classfiy-box">
      <div
        class="item"
        v-for="classfiyItem in itemList"
        :key="classfiyItem.id"
        @click="clickItem(classfiyItem)"
        :class="{ selected: classfiyItem.name == selectedItem }"
      >
        {{ classfiyItem.name }} {{ classfiyItem.articleCount }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ClassfiySelect",
  data() {
    return {
      selectedItem: "",
    };
  },
  props: ["itemList", "title"],
  methods: {
    clickItem(classfiyItem) {
      // console.log(classfiyItem);
      if (this.selectedItem == classfiyItem.name) return;
      let title = this.title;
      this.$emit("selectItem", { classfiyItem, title });
      this.selectedItem = classfiyItem.name;
    },
  },
};
</script>

<style lang="less" scoped>
.selected {
  border: 1px solid #333;
}
.top-banner {
  opacity: 0.9;
  background-color: #fff;
  border: 1px solid rgba(34, 36, 38, 0.15);
  border-bottom: none;
  box-shadow: 0 1px 2px 0 #222426;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 22px;
  padding: 20px;

  .top-label {
    font-size: 18px;
  }
}
.classfiy-box {
  padding: 10px 5px;
  background-color: #fff;
  display: flex;
  // justify-content: center;
  flex-wrap: wrap;
  margin-bottom: 10px;
  .item {
    cursor: pointer;

    font-size: 15px;
    display: inline-block;
    padding: 10px;
    text-align: center;
    margin: 5px;
    // border-radius: 20px;
  }
}
</style>