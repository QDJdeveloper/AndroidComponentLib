package co.review.lib_net.interceptor;

import android.support.v4.util.ArrayMap;
import java.io.IOException;
import java.util.Set;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * HeaderInterceptor  可以根据自己的项目具体设置
 */
public class HeaderInterceptor implements Interceptor {
    //@Override
    //public Response intercept(Chain chain) throws IOException {
    //    //  配置请求头
    //    String accessToken = "token";
    //    String tokenType = "tokenType";
    //    Request request = chain.request().newBuilder()
    //            .header("app_key", "appId")
    //            .header("Authorization", tokenType + " " + accessToken)
    //            .header("Content-Type", "application/json")
    //            .addHeader("Connection", "close")
    //            .addHeader("Accept-Encoding", "identity")
    //            .build();
    //    return chain.proceed(request);
    //}


    private ArrayMap<String, String> headers;

    public HeaderInterceptor(ArrayMap<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.header(headerKey, headers.get(headerKey)).build();
            }
        }
        return chain.proceed(builder.build());
    }


}
