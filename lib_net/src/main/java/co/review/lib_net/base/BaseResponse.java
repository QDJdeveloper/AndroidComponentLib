package co.review.lib_net.base;

import android.text.TextUtils;
import co.review.lib_net.http.NetConst;
import java.io.Serializable;


/**
 * 说明: 可根据自己的接口层返回自定义
 * "data": {   ---------------------------业务数据
 * },
 * "msg": "",  ---------------------------通讯层提示信息
 * "code": "0000"   ------------------------通讯层错误码  0000 成功  其他码代表失败
 */
public class BaseResponse<T> implements Serializable {

    private String code;
    private String msg;
    private String data;
    private T result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return TextUtils.equals(NetConst.SUCCESS, code);
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
