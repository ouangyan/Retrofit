package com.yan.retrofit;

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class RestRetrofit {
    private static RestRetrofit instance = new RestRetrofit();
    private Retrofit retrofitApi;
    private ApiService apiService;

    public static RestRetrofit getInstance(){
        return instance;
    }

    public RestRetrofit(){
        retrofitApi = new Retrofit
                .Builder()
                .baseUrl("http://192.168.1.58:81") // 服务器地址
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 适配器  支持Rxjava链式调用
                .build();

        apiService = retrofitApi.create(ApiService.class);
    }

    /**
     * 同步调用获取数据接口
     * @param token
     * @param paramMap
     * @return
     * @throws Exception
     */
    public JSONObject getSyn(String token,String url,Map<String,Object> paramMap) throws Exception {
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("token",token);
        Call<ResponseBody> call = apiService.get(headerMap,url,paramMap);
        Response<ResponseBody> response = call.execute();
        JSONObject jsonObject = new JSONObject(response.body().string());
        return jsonObject;
    }

    /**
     * 支持Rxjava链式调用的同步调用获取数据接口
     * @param token
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Flowable<ResponseBody> getSyn2(String token,String url,Map<String,Object> paramMap) {
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("token",token);
        Flowable<ResponseBody> flowable = apiService.get2(headerMap,url,paramMap);
        return flowable;
    }

    /**
     * 同步调用提交数据接口
     * @param token
     * @param url
     * @param paramMap
     * @return
     * @throws Exception
     */
    public JSONObject pushSyn(String token,String url,Map<String,Object> paramMap) throws Exception {
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("token",token);
        Call<ResponseBody> call = apiService.push(headerMap,url,paramMap);
        Response<ResponseBody> response = call.execute();
        JSONObject jsonObject = new JSONObject(response.body().string());
        return jsonObject;
    }

    /**
     * 异步调用获取数据接口
     * @param token
     * @param paramMap
     * @param callbackSuccess
     * @param callbackFail
     */
    public void getAsyn(String token,String url,Map<String,Object> paramMap,CallbackSuccess callbackSuccess,CallbackFail callbackFail){
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("token",token);
        Call<ResponseBody> call = apiService.get(headerMap,url,paramMap);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    callbackSuccess.callback(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                BusinessException exception = new BusinessException(t.getMessage(),t);
                callbackFail.callback(exception);
            }
        });
    }

    /**
     * 异步调用提交数据接口
     * @param token
     * @param url
     * @param paramMap
     * @param callbackSuccess
     * @param callbackFail
     */
    public void pushAsyn(String token,String url,Map<String,Object> paramMap,CallbackSuccess callbackSuccess,CallbackFail callbackFail){
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("token",token);
        Call<ResponseBody> call = apiService.push(headerMap,url,paramMap);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    callbackSuccess.callback(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                BusinessException exception = new BusinessException(t.getMessage(),t);
                callbackFail.callback(exception);
            }
        });
    }

    public void upload(String token, String url, MultipartBody.Part file,CallbackSuccess callbackSuccess,CallbackFail callbackFail){
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("token",token);
        apiService.upload(headerMap,url,file).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    callbackSuccess.callback(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                BusinessException exception = new BusinessException(t.getMessage(),t);
                callbackFail.callback(exception);
            }
        });
    }

    public Response<ResponseBody> download(String url) throws Exception{
        Response<ResponseBody> responseBody = apiService.download(url).execute();
        return responseBody;
    }
}
