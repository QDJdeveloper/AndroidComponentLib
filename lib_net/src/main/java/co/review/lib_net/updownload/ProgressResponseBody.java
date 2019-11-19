package co.review.lib_net.updownload;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 创建时间: 2019/11/19 16:05 <br>
 * 作者: qiudengjiao <br>
 * 描述: 包装的响应体，处理下载进度
 */
public class ProgressResponseBody extends ResponseBody {

  private ResponseBody responseBody;
  private DownProgressListener downProgressListener;
  private BufferedSource bufferedSource;

  public ProgressResponseBody(ResponseBody responseBody,
      DownProgressListener downProgressListener) {
    this.responseBody = responseBody;
    this.downProgressListener = downProgressListener;
  }

  @Override
  public MediaType contentType() {
    return responseBody.contentType();
  }

  @Override
  public long contentLength() {
    return responseBody.contentLength();
  }

  @Override
  public BufferedSource source() {
    if (bufferedSource == null) {
      bufferedSource = Okio.buffer(source(responseBody.source()));
    }
    return bufferedSource;
  }

  private Source source(Source source) {
    return new ForwardingSource(source) {
      long totalBytesRead = 0L;

      @Override
      public long read(Buffer sink, long byteCount) throws IOException {
        long bytesRead = super.read(sink, byteCount);

        totalBytesRead += bytesRead != -1 ? bytesRead : 0;

        if (null != downProgressListener) {
          downProgressListener.onProgress(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
        }
        return bytesRead;
      }
    };

  }
}
