import { reqBlogConfig } from "@/api/blogconfig";

export default () => {
    reqBlogConfig().then((result) => {
        const indexBg = result.data.indexBackground || require("@/assets/images/bg_1.png");
        if (indexBg) {
            let index = document.styleSheets[0].cssRules.length;
            document.styleSheets[0].insertRule(
                `body::before {background-image: url('${indexBg}')}`,
                index
            );
        }
        // console.log(document.styleSheets[0].cssRules);
    });
    // console.log(vue.$store.state.blogconfig.blogConfig);
    // const indexBg = this.blogConfig && this.blogConfig.indexBackground;
    // console.log(document.styleSheets[0].cssRules);
}
