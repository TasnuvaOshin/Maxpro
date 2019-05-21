package cricketworldcup.worldcup.API_CALL;

import java.util.List;

import cricketworldcup.worldcup.Model.My_data_Set;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface Api_Client {
    @Headers({
            "Content-Type: application/json",
            "X-RapidAPI-Host: dev132-cricket-live-scores-v1.p.rapidapi.com",
            "X-RapidAPI-Key: 9e9858795fmsh2fd7f47510757c9p14d60bjsn8b9bbeaa4556"
    })

    @GET("/matches.php")
    Call<List<My_data_Set>> getTestData();
}
