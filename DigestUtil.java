
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 */
public class DigestUtil {
    private static MessageDigest md5Digest;
    static {
        try {
            md5Digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * md5加密工具类
     * @param source 待加密内容
     * @return md5加密结果(十六进制)
     */
    public static String md5(String source){
        md5Digest.update(source.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md5Digest.digest();
        return byteToHex(digest);
    }
    /**
     * md5加密工具类(带盐)
     * @param source 待加密内容
     * @param salt 盐
     * @return md5加密结果(十六进制)
     */
    public static String md5WithSalt(String source, String salt){
        return md5(source + salt);
    }

    /**
     * 二进制转十六进制
     * @param digest 二进制字节数组
     * @return 十六进制字符串
     */
    private static String byteToHex(byte[] digest){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toHexString(digest[i] & 0xFF));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String s = "age,13#name,张三#aawer";
        System.out.println(DigestUtil.md5(s));
    }
}
