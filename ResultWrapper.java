@Data
@Builder
@ToString
public class ResultWrapper<T> {
    private Integer code;
    private String msg;
    private T data;

    public static ResultWrapper.ResultWrapperBuilder getSuccessfulResult(){
        return ResultWrapper.builder().code(200).msg("请求成功");
    }
    public static ResultWrapper.ResultWrapperBuilder getFailedResult(){
        return ResultWrapper.builder().code(104).msg("请求失败");
    }
}
