/**
 * HTTP参数提取工具
 */
public class HttpParamsUtil {
    /**
     * 读取请求的url中的参数信息
     * @param request 请求
     * @return Map格式的参数
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> getUrlParams(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String queryParams = "";
        if (request != null && !StringUtils.isBlank(request.getQueryString())) {
            queryParams = URLDecoder.decode(request.getQueryString(),  "utf-8");
        }
        if(queryParams.isEmpty()) return map;
        log.info(queryParams);
        String[] split = queryParams.split("&");
        for (String s : split) {
            log.info(s);
            String[] t = s.split("=");
            map.put(t[0], t[1]);
        }
        return map;
    }

    public static Map<String, String> getBodyParams(HttpServletRequest request) throws IOException {
        Map<String, String> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String s;
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        if (sb.length() == 0) return map;
        JSONObject jsonObject = JSONUtil.parseObj(sb.toString());
        map = jsonObject.toBean(Map.class);
        return map;
    }
}
