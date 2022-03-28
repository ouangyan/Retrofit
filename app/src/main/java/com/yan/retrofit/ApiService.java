package com.yan.retrofit;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiService {
    // 请求头参数  @Header 或者 @HeaderMap

    // @GET 参数注解@Query 或者 @QueryMap
    // @Url 动态url
    @GET
    Call<ResponseBody> get(@HeaderMap Map<String,String> headerMap,@Url String url, @FieldMap Map<String,Object> paramMap);

    /**
     * 支持Rxjava流式调用
     * @param headerMap
     * @param url
     * @param paramMap
     * @return
     */
    @GET
    Flowable<ResponseBody> get2(@HeaderMap Map<String,String> headerMap,@Url String url, @FieldMap Map<String,Object> paramMap);

    @POST
    @FormUrlEncoded
    Call<ResponseBody> push(@HeaderMap Map<String,String> headerMap,@Url String url, @FieldMap Map<String,Object> paramMap);

    @POST
    @FormUrlEncoded
    Flowable<ResponseBody> push2(@HeaderMap Map<String,String> headerMap,@Url String url, @FieldMap Map<String,Object> paramMap);

    @Multipart
    @POST
    Call<ResponseBody> upload();
}
