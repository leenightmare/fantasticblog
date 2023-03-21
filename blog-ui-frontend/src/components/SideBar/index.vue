<template>
  <div class="sidebar" style="display: block">
    <SideBarItem title="站长信息" class="userinfo">
      <template slot="icon"><i class="iconfont icon-name"></i></template>
      <template slot="content">
        <div class="head">
          <img
            :src="userAvatar"
            style="width: 100%; height: 100%; border-radius: 50%"
          />
        </div>
        <div class="name">{{ username }}</div>
        <div class="link">
          <a :href="github" class="iconfont icon-github"></a>
          <a
            href="javascript:void(0)"
            class="iconfont icon-weixin"
            @mouseover="weixin = true"
            @mouseleave="weixin = false"
          >
            <img v-show="weixin" class="weixin" :src="wechatLink" alt="" />
          </a>
          <a href="javascript:void(0)" class="iconfont icon-QQ"></a>
          <a href="javascript:void(0)" class="iconfont icon-email"></a>
        </div>
        <!-- <div class="follow">Follow me</div> -->
      </template>
    </SideBarItem>
    <SideBarItem title="公告" class="note">
      <template slot="icon"><i class="el-icon-bell"></i></template>
      <template slot="content">
        <p class="content">
          <!-- {{博客持续更新中，技术完善后将会开源，如有问题，请在留言区评论，我会及时回复，承蒙关照。}}
           -->
          {{ noticeList[0] && noticeList[0].noticeContent }}
        </p>
      </template>
    </SideBarItem>
    <SideBarItem title="文章分类" class="classfiy">
      <template slot="icon"><i class="el-icon-s-cooperation"></i></template>
      <template slot="content">
        <div
          class="item"
          v-for="category in categoryArticleInfo"
          :key="category.categoryId"
        >
          <router-link :to="`/classfiy/category/${category.categoryId}`">
            <i>{{ category.categoryName }}</i>
            <i>{{ category.articleCount }}</i>
          </router-link>
        </div>
      </template>
    </SideBarItem>
    <SideBarItem title="标签列表" class="tag" v-if="false">
      <template slot="icon"><i class="el-icon-bell"></i></template>
      <template slot="content">
        <!-- <a v-for="(tag, index) in tagList" :key="index">{{ tag }}</a> -->
        <router-link
          :to="`/classfiy/tag/${tag.id}`"
          style="display: inline-flex"
          class="tag-item"
          type="primary"
          v-for="(tag, index) in tagList"
          :key="index"
          >{{ tag.name }}</router-link
        >
      </template>
    </SideBarItem>
  </div>
</template>

<script>
import { mapState } from "vuex";
import SideBarItem from "./SideBarItem";
export default {
  name: "SideBar",
  components: { SideBarItem },
  data() {
    return {
      weixin: false,
      categoryArticleInfo: [
        {
          categoryId: "",
          categoryName: "",
          articleCount: "",
        },
      ],
    };
  },
  methods: {
    async getCategoryArticle() {
      let result = await this.$API.article.reqCategoryArticle();
      if (result.code == 200) {
        this.categoryArticleInfo = result.data;
      } else {
        Promise.reject(new Error("获取分类文章数量失败"));
      }
    },
  },
  computed: {
    ...mapState("user", ["userInfo"]),
    ...mapState("tag", ["tagList"]),
    ...mapState("notice", ["noticeList"]),
    ...mapState("blogconfig", ["blogConfig"]),
    wechatLink() {
      return this.blogConfig.wechat || require("@/assets/images/weixin.jpg");
    },
    github() {
      return this.blogConfig.github || "https://github.com/leenightmare";
    },
    username() {
      return this.blogConfig.username || "少女祈祷中";
    },
    userAvatar() {
      return this.blogConfig.userAvatar || "";
    },
  },
  mounted() {
    this.getCategoryArticle();
  },
};
</script>

<style lang="less" scoped>
.sidebar {
  // flex: 2;
  padding: 15px;
  padding-top: 0;
  .userinfo {
    font-size: 15px;
    background-color: #fff;
    overflow: hidden;
    .head {
      width: 70px;
      height: 70px;
      border-radius: 50%;
      margin: 15px auto;
    }

    .name {
      text-align: center;
      font-size: 20px;
      color: rgb(22, 147, 189);
    }
    .link {
      position: relative;
      margin: 30px 0;
      display: flex;
      justify-content: space-evenly;
      text-align: center;
      a {
        font-size: 18px;
      }
      .weixin {
        position: absolute;
        bottom: 100%;
        left: 0;
        width: 200px;
        height: 200px;
      }
    }
    .follow {
      margin: 20px auto;
      width: 80%;
      height: 40px;
      background-color: skyblue;
      text-align: center;
      line-height: 40px;

      font-size: 20px;
      color: #fff;
    }
  }

  .classfiy {
    .item {
      padding: 5px 0;
      font-size: 16px;
      a {
        display: flex;
        justify-content: space-between;
        padding: 5px;
        i {
          font-style: normal;
        }
        &:hover {
          // background-color: orange;
          color: var(--topic-green);
        }
      }
    }
  }

  .note {
    .content {
      font-size: 15px;
      line-height: 2;
    }
  }

  .tag {
    font-size: 17px;

    // overflow: hidden;
    .tag-item {
      padding: 3px;
    }
  }
}
</style>