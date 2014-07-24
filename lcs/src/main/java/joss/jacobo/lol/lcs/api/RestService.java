package joss.jacobo.lol.lcs.api;

import java.util.List;

import joss.jacobo.lol.lcs.api.model.Config;
import joss.jacobo.lol.lcs.api.model.Standings.Standings;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface RestService{

    @GET("/config/initial/")
    void getInitialConfig(Callback<Config> callback);

    @GET("/standings/getLatestStanding/")
    void getLatestStandings(Callback<Standings> callback);

}