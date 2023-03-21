package cc.lijad.fantasticblog.utils;

/**
 * @author 李坚达
 * @create 2022/12/17 22:14
 */
public class FileUtils {

    /**
     * 获取后缀名
     *
     * @param fileName
     * @return
     */
    public static String getFileSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static void main(String[] args) {
        String reg = "^(http?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$";
        String url = "http://www.baidu.com";
        System.out.println(url.matches(reg));
    }
}
