package cricketworldcup.worldcup.API_CALL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api_Connection {

    private static final String BASE_URL = "https://dev132-cricket-live-scores-v1.p.rapidapi.com";
    static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    static Retrofit retrofit = builder.build(); //this is the main Retrofit Object


    public static <S> S GetInstance(Class<S> ourInterfaceClass) {
        return retrofit.create(ourInterfaceClass); //we will call this from main activiy and
        //we can access the interface method now
    }
}
