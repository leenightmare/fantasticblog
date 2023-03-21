module.exports = {
  presets: [
    '@vue/cli-plugin-babel/preset'
  ],


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
}
