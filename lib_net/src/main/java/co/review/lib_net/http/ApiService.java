package co.review.lib_net.http;

import android.support.v4.util.ArrayMap;
import co.review.lib_net.base.BaseResponse;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 创建时间: 2019/11/19 11:57 <br>
 * 作者: qiudengjiao <br>
 * 描述: 方法调用
 */
public interface ApiService {

  /**
   * post表单请求
   * 服务端json格式数据请求
   * 处理普通接口网络请求
   *
   * @param interfaceName 接口名称
   * @param maps          请求体参数键值对
   * @return Rx Ovserable
   * @author fjg
   */
  @FormUrlEncoded
  @POST("{interface}")
  Observable<BaseResponse> post(
      @Path(value = "interface", encoded = true) String interfaceName,
      @FieldMap ArrayMap<String, String> maps);
  /**
   * post 多媒体请求
   * 服务端json格式数据请求
   * 处理普通接口网络请求
   * @param interfaceName 接口名称
   * @param t
   * @return Rx Ovserable
   * @author fjg
   */
  @POST("{interface}")
  Observable<BaseResponse> post(
      @Path(value = "interface", encoded = true) String interfaceName,
      @Body BaseResponse t);
  /**
   * get请求
   * 服务端json格式数据请求
   * 处理普通接口网络请求
   *
   * @param interfaceName 接口名称
   * @param maps          请求体参数键值对
   * @return Rx Ovserable
   * @author fjg
   */
  @GET("{interface}")
  Observable<BaseResponse> get(
      @Path(value = "interface", encoded = true) String interfaceName,
      @QueryMap ArrayMap<String, String> maps);

  /**
   * 多媒体文件下载
   *
   * @param interfaceName 接口名称
   * @param maps          请求体参数键值对
   * @return Observable<ResponseBody>
   */
  @Streaming
  @FormUrlEncoded
  @POST("{interface}")
  Observable<ResponseBody> downloadFile(
      @Path(value = "interface", encoded = true) String interfaceName,
      @FieldMap ArrayMap<String, String> maps);

  /**
   * 通过url直接下载多媒体文件
   *
   * @param url 下载地址
   * @return Observable<ResponseBody>
   */
  @Streaming
  @GET
  Observable<ResponseBody> downloadFile(
      @Url String url);

  /**
   * 多媒体文件上传 构建body方式
   * <p>
   * RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
   * .addFormDataPart("name", name)
   * .addFormDataPart("name", psd)
   * .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image"), file))
   * .build();
   */
  @POST("{interface}")
  Observable<BaseResponse> uploadFile(
      @Path(value = "interface", encoded = true) String interfaceName,
      @Body RequestBody requestBody);
}
