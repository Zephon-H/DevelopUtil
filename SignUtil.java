
import java.util.Map;
import java.util.TreeMap;

/**
 * 签名工具类
 */
public class SignUtil {
    private static final String SECRET_KEY = "secret-key";
    private static final String SIGN = "sign";
    public static String generateSign(Map<String, String> map){
        TreeMap<String, String> treeMap = new TreeMap<>(map);
        if (map.containsKey(SIGN)) {
            treeMap.remove(SIGN);
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            sb.append(entry.getKey()).append(",").append(entry.getValue()).append("#");
        }
        return DigestUtil.md5WithSalt(sb.toString(), SECRET_KEY);
    }
    public static boolean checkSign(Map<String, String> map){
        if(!map.containsKey(SIGN)) return false;
        String sourceSign = map.get(SIGN);
        String generateSign = generateSign(map);
        System.out.println("sign: "+generateSign);
        return sourceSign.equals(generateSign);
    }
}
