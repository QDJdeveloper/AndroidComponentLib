package co.review.lib_net.subscribers;

import android.support.annotation.NonNull;
import co.review.lib_net.http.NetConst;
import co.review.lib_net.rx.ExceptionHandle;
import co.review.lib_net.rx.ResponseErrorListener;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 消费者处理
 * @param <T>
 */
public abstract class HandlerSubscriber<T> implements Observer<T> {
    private ResponseErrorListener responseErrorListener;

    public HandlerSubscriber(ResponseErrorListener responseErrorListener) {
        this.responseErrorListener = responseErrorListener;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }


    @Override
    public void onComplete() {

    }

    public abstract void onError(ExceptionHandle.ResponseThrowable e);

    @Override
    public void onError(@NonNull Throwable e) {
        if (e instanceof ExceptionHandle.ResponseThrowable) {
            if (!responseErrorListener.handleResponseError(((ExceptionHandle.ResponseThrowable) e).code, ((ExceptionHandle.ResponseThrowable) e).codeMessage)) {
                onError((ExceptionHandle.ResponseThrowable) e);
            }
        } else {//有可能是rx NullPointExceptionE等未知错误
            onError(new ExceptionHandle.ResponseThrowable(ExceptionHandle.ERROR.UNKNOWN, NetConst.TIP));
        }
    }
}

