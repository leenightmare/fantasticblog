# 项目记录

## 一、markdown文本转化为html节点

> md格式文本转化为html元素，再使用v-html在页面中显示。有两个插件，使用其中一个即可



* marked

```js
npm i marked
import {marked} from "marked"
let html  = marked("## 这是标题二");
```



* showdown

```js
npm i showdown
import showdown from "showdown"
let converter = new showdown.Converter();
let html = converter.makeHtml("## 这是标题二")
```



## 二、代码高亮

```js
npm i prismjs
npm i babel-plugin-prismjs

//在babel.config.js添加：然后重启项目
plugins: [
    [
      "prismjs",
      {
        languages: [
          "html",
          "java",
          "javascript",
          "xml"
        ],
        plugins: [
          "line-numbers",
          "show-language",
          "copy-to-clipboard"
        ],
        theme: "tomorrow",
        css: true
      }
    ]
  ]


import prism from "prismjs";
<!-- 记得要异步执行，不然可能会失效。setTimeOut()好像也可以 -->
this.$nextTick(()=>{
    prism.highlightAll();
})
```



## 三、防止XSS

> https://jsxss.com/zh/index.html

```js
import xss from "xss";
const option = {
    // stripIgnoreTagBody: true, // 不在白名单中的标签以及标签里面的内容直接删除
    whiteList: {
        h1: ["style"],
        h2: ["style"],
        h3: ["style"],
        h4: ["style"],
        h5: ["style"],
        h6: ["style"],
        hr: ["style"],
        span: ["style"],
        strong: ["style"],
        b: ["style"],
        i: ["style"],
        br: [],
        p: ["style"],
        pre: ["style"],
        code: ["style"],
        a: ["style", "target", "href", "title", "rel"],
        img: ["style", "src", "title"],
        div: ["style"],
        table: ["style", "width", "border"],
        tr: ["style"],
        td: ["style", "width", "colspan"],
        th: ["style", "width", "colspan"],
        tbody: ["style"],
        ul: ["style"],
        li: ["style"],
        ol: ["style"],
        dl: ["style"],
        dt: ["style"],
        em: ["style"],
        cite: ["style"],
        section: ["style"],
        header: ["style"],
        footer: ["style"],
        blockquote: ["style"],
        audio: ["autoplay", "controls", "loop", "preload", "src"],
        video: [
            "autoplay",
            "controls",
            "loop",
            "preload",
            "src",
            "height",
            "width",
        ],
    },
    css: {
        // 因为上面白名单中允许了标签的style属性，所以需要防止攻击者使用此途径进行攻击
        whiteList: {
            "color": true,
            "background-color": true,
            "width": true,
            "height": true,
            "max-width": true,
            "max-height": true,
            "min-width": true,
            "min-height": true,
            "font-size": true,
        },
    },
};

let xssFilter = new xss.FilterXSS(option);
let content = xssFilter.process("<script>alert(1)</script>");
```



## 四、自定义组件或原生表单使用Element-ui表单校验

参考文章：http://events.jianshu.io/p/9f034e3fe62d

## 代码审查
eslint
prettier
stylelint
husky
commit-lint
lint-staged




