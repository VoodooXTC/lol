package joss.jacobo.lol.lcs.api;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import joss.jacobo.lol.lcs.api.model.Config;
import joss.jacobo.lol.lcs.api.model.Players.Player;
import joss.jacobo.lol.lcs.api.model.Standings.Standings;
import joss.jacobo.lol.lcs.model.TweetsModel;
import joss.jacobo.lol.lcs.provider.matches.MatchesColumns;
import joss.jacobo.lol.lcs.provider.matches.MatchesContentValues;
import joss.jacobo.lol.lcs.provider.players.PlayersColumns;
import joss.jacobo.lol.lcs.provider.players.PlayersContentValues;
import joss.jacobo.lol.lcs.provider.standings.StandingsColumns;
import joss.jacobo.lol.lcs.provider.standings.StandingsContentValues;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsColumns;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsContentValues;
import joss.jacobo.lol.lcs.provider.teams.TeamsColumns;
import joss.jacobo.lol.lcs.provider.teams.TeamsContentValues;
import joss.jacobo.lol.lcs.provider.tournaments.TournamentsColumns;
import joss.jacobo.lol.lcs.provider.tournaments.TournamentsContentValues;
import joss.jacobo.lol.lcs.provider.tweets.TweetsColumns;
import joss.jacobo.lol.lcs.provider.tweets.TweetsContentValues;
import joss.jacobo.lol.lcs.provider.tweets.TweetsSelection;
import joss.jacobo.lol.lcs.utils.Twitter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import twitter4j.ResponseList;
import twitter4j.Status;

public class ApiService extends IntentService {
    private static final String TAG = "ApiService";

    public static final String API_TYPE = "api_type";
    public static final String TWITTER_HANDLE = "twitter_handle";
    public static final String TEAM_ID = "team_id";

    public static final int TYPE_INITIAL_CONFIG = 0;
    public static final int TYPE_LATEST_STANDINGS = 1;
    public static final int TYPE_GET_TWEETS = 2;
    public static final int TYPE_GET_PLAYERS = 3;

    RestService service;

    public ApiService() {
        super("Api Service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint("http://lcs.voodootvdb.com")
            .build();
        service = restAdapter.create(RestService.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        switch(intent.getExtras().getInt(API_TYPE)){
            case TYPE_INITIAL_CONFIG:
                service.getInitialConfig(new Callback<Config>() {
                    @Override
                    public void success(Config config, Response response) {
                        updateInitialConfig(getContentResolver(), config);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                break;

            case TYPE_LATEST_STANDINGS:
                service.getLatestStandings(new Callback<Standings>() {
                    @Override
                    public void success(Standings standings, Response response) {
                        updateLatestStandings(getContentResolver(), standings);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                break;

            case TYPE_GET_TWEETS:
                final String twitterHandle = intent.getStringExtra(TWITTER_HANDLE);
                Twitter.getUsersTimeline(twitterHandle, new Twitter.TwitterCallback() {
                    @Override
                    public void onSuccess(ResponseList<Status> statuses) {
                        updateTweets(getContentResolver(), twitterHandle, statuses);
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e(TAG, errorMessage);
                    }
                });
                break;

            case TYPE_GET_PLAYERS:
                service.getPlayers(intent.getIntExtra(TEAM_ID, -1), new Callback<List<Player>>() {
                    @Override
                    public void success(List<Player> players, Response response) {
                        updatePlayers(getContentResolver(), players);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                break;
        }
    }

    /**
     * API Calls
     */
    public static void getInitialConfig(Context context){
        Intent intent = new Intent(context, ApiService.class);
        intent.putExtra(ApiService.API_TYPE, ApiService.TYPE_INITIAL_CONFIG);
        context.startService(intent);
    }

    public static void getLatestStandings(Context context){
        Intent intent = new Intent(context, ApiService.class);
        intent.putExtra(ApiService.API_TYPE, ApiService.TYPE_LATEST_STANDINGS);
        context.startService(intent);
    }

    public static void getTweets(Context context, String twitterHandle){
        Intent intent = new Intent(context, ApiService.class);
        intent.putExtra(ApiService.API_TYPE, ApiService.TYPE_GET_TWEETS);
        intent.putExtra(ApiService.TWITTER_HANDLE, twitterHandle);
        context.startService(intent);
    }

    public static void getPlayers(Context context, int teamId){
        Intent intent = new Intent(context, ApiService.class);
        intent.putExtra(ApiService.API_TYPE, ApiService.TYPE_GET_PLAYERS);
        intent.putExtra(ApiService.TEAM_ID, teamId);
        context.startService(intent);
    }

    /**
     * DB updates
     */
    public static void updateInitialConfig(ContentResolver contentResolver, Config initalConfig) {

        contentResolver.delete(TournamentsColumns.CONTENT_URI, null, null);
        ContentValues[] tournamentCV = TournamentsContentValues.getContentValues(initalConfig.getTournaments());
        contentResolver.bulkInsert(TournamentsColumns.CONTENT_URI, tournamentCV);

        contentResolver.delete(TeamDetailsColumns.CONTENT_URI, null, null);
        ContentValues[] teamDetailsCV = TeamDetailsContentValues.getContentValues(initalConfig.getTeamDetails());
        contentResolver.bulkInsert(TeamDetailsColumns.CONTENT_URI, teamDetailsCV);

        contentResolver.delete(TeamsColumns.CONTENT_URI, null, null);
        ContentValues[] teamsCV= TeamsContentValues.getContentValues(initalConfig.getTeams());
        contentResolver.bulkInsert(TeamsColumns.CONTENT_URI, teamsCV);

        contentResolver.delete(MatchesColumns.CONTENT_URI, null, null);
        ContentValues[] matchesCV = MatchesContentValues.getContentValues(initalConfig.getMatches());
        contentResolver.bulkInsert(MatchesColumns.CONTENT_URI, matchesCV);
    }

    public static void updateLatestStandings(ContentResolver contentResolver, Standings standings) {
        contentResolver.delete(StandingsColumns.CONTENT_URI, null, null);
        ContentValues[] standingsCV = StandingsContentValues.getContentValues(standings.getList());
        contentResolver.bulkInsert(StandingsColumns.CONTENT_URI, standingsCV);
    }

    private void updateTweets(ContentResolver contentResolver, String twitterHandle, ResponseList<Status> statuses) {

        TweetsSelection where = new TweetsSelection();
        where.twitterHandle(twitterHandle);

        contentResolver.delete(TweetsColumns.CONTENT_URI, where.sel(), where.args());
        ContentValues[] tweetsValues = TweetsContentValues.getContentValues(TweetsModel.getList(statuses));
        contentResolver.bulkInsert(TweetsColumns.CONTENT_URI, tweetsValues);
    }

    private void updatePlayers(ContentResolver contentResolver, List<Player> players) {
        ContentValues[] playersValues = PlayersContentValues.getContentValues(Player.getList(players));
        contentResolver.bulkInsert(PlayersColumns.CONTENT_URI, playersValues);
    }

}