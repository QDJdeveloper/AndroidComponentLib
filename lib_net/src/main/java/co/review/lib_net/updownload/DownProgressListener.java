package co.review.lib_net.updownload;

/**
 * 创建时间: 2019/11/19 12:15 <br>
 * 作者: qiudengjiao <br>
 * 描述: 进度监听
 */
public interface DownProgressListener {
  void onProgress(long bytesRead, long contentLength, boolean done);
}
