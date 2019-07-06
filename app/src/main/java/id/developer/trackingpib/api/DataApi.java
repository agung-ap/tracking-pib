package id.developer.trackingpib.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DataApi {
    @GET("/detailpib.php")
    Call<ResponseBody> getDetailPib(@Query("no_pib") String noPib);

    @GET("/gettahap1.php")
    Call<ResponseBody> getTahap1();

    @GET("/gettahap2.php")
    Call<ResponseBody> getTahap2();

    @GET("/gettahap3.php")
    Call<ResponseBody> getTahap3();

    @GET("/gettahap4.php")
    Call<ResponseBody> getTahap4();

    @GET("/gettahap5.php")
    Call<ResponseBody> getTahap5();
}
