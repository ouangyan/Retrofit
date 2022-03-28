package com.yan.retrofit;

import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class RxjavaRetrofitTest {

    @Test
    public void test(){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("name","zhangsan");
        String token = UUID.randomUUID().toString();
        RestRetrofit.getInstance().getSyn2(token,"/user/query",paramMap)
            .map(new Function<ResponseBody, Object>() {
                @Override
                public Object apply(ResponseBody responseBody) throws Throwable {
                    return null;
                }
            })
            .observeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object o) throws Throwable {

                }
            });
    }

    @Test
    public void test2(){
        RestRetrofit.getInstance().getAsyn(null, null,null,
                new CallbackSuccess() {
                    @Override
                    public void callback(JSONObject jsonObject) {

                    }
                }, new CallbackFail() {
                    @Override
                    public void callback(BusinessException exception) {

                    }
                }
        );
    }
}
