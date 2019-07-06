package id.developer.trackingpib.api;

import id.developer.trackingpib.model.request.PibRequest;
import id.developer.trackingpib.model.request.Tahap1Request;
import id.developer.trackingpib.model.request.Tahap2Request;
import id.developer.trackingpib.model.request.Tahap3Request;
import id.developer.trackingpib.model.request.Tahap4Request;
import id.developer.trackingpib.model.request.Tahap5Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PibApi {
    @Headers({
            "Content-Type:application/json"
    })
    @POST("/insertpib.php")
    Call<ResponseBody> insertPib(@Body PibRequest request);

    @Headers({
            "Content-Type:application/json"
    })
    @POST("/inserttahap1.php")
    Call<ResponseBody> insertTahap1(@Body Tahap1Request request);

    @Headers({
            "Content-Type:application/json"
    })
    @POST("/inserttahap2.php")
    Call<ResponseBody> insertTahap2(@Body Tahap2Request request);

    @Headers({
            "Content-Type:application/json"
    })
    @POST("/inserttahap3.php")
    Call<ResponseBody> insertTahap3(@Body Tahap3Request request);

    @Headers({
            "Content-Type:application/json"
    })
    @POST("/inserttahap4.php")
    Call<ResponseBody> insertTahap4(@Body Tahap4Request request);

    @Headers({
            "Content-Type:application/json"
    })
    @POST("/inserttahap5.php")
    Call<ResponseBody> insertTahap5(@Body Tahap5Request request);


}

