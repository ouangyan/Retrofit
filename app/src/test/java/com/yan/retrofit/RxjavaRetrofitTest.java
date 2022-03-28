package com.yan.retrofit;

import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

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
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("name","zhangsan");
        String token = UUID.randomUUID().toString();
        RestRetrofit.getInstance().getAsyn(token, "/user/query",paramMap,
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

    @Test
    public void upload(){
        String token = UUID.randomUUID().toString();
        File file = new File("");
        MultipartBody.Part part = MultipartBody.Part.createFormData("",file.getName(), RequestBody.create(MediaType.parse("text/plain"), file));
        RestRetrofit.getInstance().upload(token, "/user/upload", part,
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

    /**
     *
     * 下载
     */
    @Test
    public void download(){
        try {
            String url = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2Ftp09%2F210611094Q512b-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651055303&t=cff7f38d97ba93c57eba22e3f601b7dc";
            Response<ResponseBody> response = RestRetrofit.getInstance().download(url);
            if(response.isSuccessful()) {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream("c:\\Users\\dexterleslie\\Desktop\\a.jpg");
                    byte[] buffer = new byte[4096];
                    int len;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fos.write(buffer,0,len);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if(fos!=null)
                            fos.close();
                        if(inputStream!=null)
                            inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
