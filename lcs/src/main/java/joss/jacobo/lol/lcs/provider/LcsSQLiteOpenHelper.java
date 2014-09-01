package joss.jacobo.lol.lcs.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import joss.jacobo.lol.lcs.BuildConfig;
import joss.jacobo.lol.lcs.provider.matches.MatchesColumns;
import joss.jacobo.lol.lcs.provider.news.NewsColumns;
import joss.jacobo.lol.lcs.provider.players.PlayersColumns;
import joss.jacobo.lol.lcs.provider.replays.ReplaysColumns;
import joss.jacobo.lol.lcs.provider.standings.StandingsColumns;
import joss.jacobo.lol.lcs.provider.teams.TeamsColumns;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsColumns;
import joss.jacobo.lol.lcs.provider.tournaments.TournamentsColumns;
import joss.jacobo.lol.lcs.provider.tweets.TweetsColumns;

public class LcsSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = LcsSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "lcs.db";
    private static final int DATABASE_VERSION = 1;

    // @formatter:off
    private static final String SQL_CREATE_TABLE_MATCHES = "CREATE TABLE IF NOT EXISTS "
            + MatchesColumns.TABLE_NAME + " ( "
            + MatchesColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MatchesColumns.MATCH_ID + " INTEGER, "
            + MatchesColumns.TOURNAMENT_ID + " INTEGER, "
            + MatchesColumns.TOURNAMENT_ABREV + " TEXT, "
            + MatchesColumns.SPLIT + " TEXT, "
            + MatchesColumns.DATETIME + " TEXT, "
            + MatchesColumns.WEEK + " INTEGER, "
            + MatchesColumns.DAY + " INTEGER, "
            + MatchesColumns.DATE + " TEXT, "
            + MatchesColumns.TEAM1_ID + " INTEGER, "
            + MatchesColumns.TEAM2_ID + " INTEGER, "
            + MatchesColumns.TEAM1 + " TEXT, "
            + MatchesColumns.TEAM2 + " TEXT, "
            + MatchesColumns.TIME + " TEXT, "
            + MatchesColumns.RESULT1 + " INTEGER, "
            + MatchesColumns.RESULT2 + " INTEGER, "
            + MatchesColumns.PLAYED + " INTEGER, "
            + MatchesColumns.MATCH_NO + " INTEGER, "
            + MatchesColumns.MATCH_POSITION + " INTEGER "
            + ", CONSTRAINT MATCH_ID UNIQUE (MATCH_ID) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_TABLE_NEWS = "CREATE TABLE IF NOT EXISTS "
            + NewsColumns.TABLE_NAME + " ( "
            + NewsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NewsColumns.NEWS_ID + " INTEGER, "
            + NewsColumns.CATEGORY + " TEXT, "
            + NewsColumns.TITLE + " TEXT, "
            + NewsColumns.LINK + " TEXT, "
            + NewsColumns.IMAGE + " TEXT, "
            + NewsColumns.AUTHOR + " TEXT, "
            + NewsColumns.DESCRIPTION + " TEXT, "
            + NewsColumns.CONTENT + " TEXT, "
            + NewsColumns.LASTUPDATED + " INTEGER "
            + " );";

    private static final String SQL_CREATE_TABLE_PLAYERS = "CREATE TABLE IF NOT EXISTS "
            + PlayersColumns.TABLE_NAME + " ( "
            + PlayersColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PlayersColumns.PLAYER_ID + " INTEGER, "
            + PlayersColumns.NAME + " TEXT, "
            + PlayersColumns.IRL_FIRST_NAME + " TEXT, "
            + PlayersColumns.IRL_LAST_NAME + " TEXT, "
            + PlayersColumns.TEAM_ID + " INTEGER, "
            + PlayersColumns.PLAYER_POSITION + " TEXT, "
            + PlayersColumns.ACTIVE + " INTEGER, "
            + PlayersColumns.FAMOUS_QUOTE + " TEXT, "
            + PlayersColumns.DESCRIPTION + " TEXT, "
            + PlayersColumns.IMAGE + " TEXT, "
            + PlayersColumns.FACEBOOK_LINK + " TEXT, "
            + PlayersColumns.TWITTER_USERNAME + " TEXT, "
            + PlayersColumns.STREAMING_LINK + " TEXT "
            + ", CONSTRAINT PLAYER_ID UNIQUE (PLAYER_ID) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_TABLE_REPLAYS = "CREATE TABLE IF NOT EXISTS "
            + ReplaysColumns.TABLE_NAME + " ( "
            + ReplaysColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ReplaysColumns.KIND + " TEXT, "
            + ReplaysColumns.ETAG + " TEXT, "
            + ReplaysColumns.YOUTUBE_ID + " TEXT, "
            + ReplaysColumns.PUBLISHED_AT + " TEXT, "
            + ReplaysColumns.CHANNEL_ID + " TEXT, "
            + ReplaysColumns.TITLE + " TEXT, "
            + ReplaysColumns.DESCRIPTION + " TEXT, "
            + ReplaysColumns.THUMBNAILS + " TEXT, "
            + ReplaysColumns.CHANNEL_TITLE + " TEXT, "
            + ReplaysColumns.TYPE + " TEXT, "
            + ReplaysColumns.DURATION + " TEXT, "
            + ReplaysColumns.DIMENSION + " TEXT, "
            + ReplaysColumns.DEFINITION + " TEXT, "
            + ReplaysColumns.CAPTION + " TEXT, "
            + ReplaysColumns.LICENSEDCONTENT + " INTEGER, "
            + ReplaysColumns.VIEWCOUNT + " INTEGER, "
            + ReplaysColumns.LIKECOUNT + " INTEGER, "
            + ReplaysColumns.DISLIKECOUNT + " INTEGER, "
            + ReplaysColumns.FAVORITECOUNT + " INTEGER, "
            + ReplaysColumns.COMMENTCOUNT + " INTEGER "
            + ", CONSTRAINT UNIQUE_YOUTUBE_ID UNIQUE (YOUTUBE_ID) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_TABLE_STANDINGS = "CREATE TABLE IF NOT EXISTS "
            + StandingsColumns.TABLE_NAME + " ( "
            + StandingsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StandingsColumns.STANDING_ID + " INTEGER, "
            + StandingsColumns.TOURNAMENT_ABREV + " TEXT, "
            + StandingsColumns.STANDING_WEEK + " INTEGER, "
            + StandingsColumns.TEAM_NAME + " TEXT, "
            + StandingsColumns.TEAM_ABREV + " TEXT, "
            + StandingsColumns.WINS + " INTEGER, "
            + StandingsColumns.LOSSES + " INTEGER, "
            + StandingsColumns.DELTA + " INTEGER, "
            + StandingsColumns.STANDING_POSITION + " INTEGER "
            + " );";

    private static final String SQL_CREATE_TABLE_TEAMS = "CREATE TABLE IF NOT EXISTS "
            + TeamsColumns.TABLE_NAME + " ( "
            + TeamsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TeamsColumns.TEAM_ID + " INTEGER, "
            + TeamsColumns.TEAM_ABREV + " TEXT, "
            + TeamsColumns.TOURNAMENT_ID + " INTEGER, "
            + TeamsColumns.TOURNAMENT_ABREV + " TEXT, "
            + TeamsColumns.TEAM_NAME + " TEXT "
            + " );";

    private static final String SQL_CREATE_TABLE_TEAM_DETAILS = "CREATE TABLE IF NOT EXISTS "
            + TeamDetailsColumns.TABLE_NAME + " ( "
            + TeamDetailsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TeamDetailsColumns.TEAM_ID + " INTEGER, "
            + TeamDetailsColumns.ABREV + " TEXT, "
            + TeamDetailsColumns.NAME + " TEXT, "
            + TeamDetailsColumns.LOGO + " TEXT, "
            + TeamDetailsColumns.TEAM_PICTURE + " TEXT, "
            + TeamDetailsColumns.ABOUT_TEXT + " TEXT, "
            + TeamDetailsColumns.TWITTER_HANDLE + " TEXT "
            + ", CONSTRAINT TEAM_ID UNIQUE (TEAM_ID) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_TABLE_TOURNAMENTS = "CREATE TABLE IF NOT EXISTS "
            + TournamentsColumns.TABLE_NAME + " ( "
            + TournamentsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TournamentsColumns.TOURNAMENT_ID + " INTEGER, "
            + TournamentsColumns.ABREV + " TEXT, "
            + TournamentsColumns.NAME + " TEXT, "
            + TournamentsColumns.YEAR + " INTEGER, "
            + TournamentsColumns.SEASON + " TEXT, "
            + TournamentsColumns.ONGOING + " INTEGER "
            + ", CONSTRAINT TOURNAMENT_ID UNIQUE (TOURNAMENT_ID) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_TABLE_TWEETS = "CREATE TABLE IF NOT EXISTS "
            + TweetsColumns.TABLE_NAME + " ( "
            + TweetsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TweetsColumns.TWITTER_HANDLE + " TEXT, "
            + TweetsColumns.TWEET_ID + " INTEGER, "
            + TweetsColumns.CREATED_AT + " INTEGER, "
            + TweetsColumns.USER_DESCRIPTION + " TEXT, "
            + TweetsColumns.USER_NAME + " TEXT, "
            + TweetsColumns.USER_IMAGE_URL + " TEXT, "
            + TweetsColumns.SCREEN_NAME + " TEXT, "
            + TweetsColumns.TEXT + " TEXT "
            + ", CONSTRAINT TWEET_ID UNIQUE (TWEET_ID) ON CONFLICT REPLACE"
            + " );";

    // @formatter:on

    public static LcsSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */

    private static LcsSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new LcsSQLiteOpenHelper(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    private LcsSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    /*
     * Post Honeycomb.
     */

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static LcsSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new LcsSQLiteOpenHelper(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private LcsSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        db.execSQL(SQL_CREATE_TABLE_MATCHES);
        db.execSQL(SQL_CREATE_TABLE_NEWS);
        db.execSQL(SQL_CREATE_TABLE_PLAYERS);
        db.execSQL(SQL_CREATE_TABLE_REPLAYS);
        db.execSQL(SQL_CREATE_TABLE_STANDINGS);
        db.execSQL(SQL_CREATE_TABLE_TEAMS);
        db.execSQL(SQL_CREATE_TABLE_TEAM_DETAILS);
        db.execSQL(SQL_CREATE_TABLE_TOURNAMENTS);
        db.execSQL(SQL_CREATE_TABLE_TWEETS);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (BuildConfig.DEBUG) Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
    }
}
