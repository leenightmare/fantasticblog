import Vue from 'vue'
import App from './App.vue'

// 导入路由
import router from '@/router/index.js';
// 导入vuex仓库
import store from '@/store/index.js';
// API
import API from "@/api/index";

// 完整导入ElementUI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'element-ui/lib/theme-chalk/display.css';

import 'animate.css';

import initbody from "@/utils/initbody";
initbody();

import { MessageBox, Message } from "element-ui"

import "@/assets/iconfont/iconfont.css"



import "@/assets/styles/normalize.less";  //初始化样式
import "@/assets/styles/responsive.less";    //响应式样式
import "@/assets/styles/blog.less"

// Header组件
import Header from "@/components/Header"
import Footer from "@/components/Footer"
import Banner from "@/components/Banner"
import ArticleItem from "@/components/ArticleItem"
import Girl from "@/components/Girl"
import ClassfiySelect from "@/components/ClassfiySelect"
import WindChimes from "@/components/WindChimes";

import SideBar from "@/components/SideBar/index.vue";
import CommentBox from "@/components/CommentBox";


Vue.use(ElementUI); //element ui

// Header组件
Vue.component(Header.name, Header);
Vue.component(Footer.name, Footer);
Vue.component(Banner.name, Banner);
Vue.component(ArticleItem.name, ArticleItem);
Vue.component(Girl.name, Girl);
Vue.component(ClassfiySelect.name, ClassfiySelect);
Vue.component(WindChimes.name, WindChimes);
Vue.component(SideBar.name, SideBar);
Vue.component(CommentBox.name, CommentBox);


Vue.prototype.$msgbox = MessageBox;
Vue.prototype.$message = Message;



Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router,
  store,
  beforeCreate() {
    Vue.prototype.$bus = this,
      Vue.prototype.$API = API
  },
}).$mount('#app')



