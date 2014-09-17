package joss.jacobo.lol.lcs.provider;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import joss.jacobo.lol.lcs.BuildConfig;
import joss.jacobo.lol.lcs.provider.matches.MatchesColumns;
import joss.jacobo.lol.lcs.provider.news.NewsColumns;
import joss.jacobo.lol.lcs.provider.players.PlayersColumns;
import joss.jacobo.lol.lcs.provider.replays.ReplaysColumns;
import joss.jacobo.lol.lcs.provider.standings.StandingsColumns;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsColumns;
import joss.jacobo.lol.lcs.provider.teams.TeamsColumns;
import joss.jacobo.lol.lcs.provider.tournaments.TournamentsColumns;
import joss.jacobo.lol.lcs.provider.tweets.TweetsColumns;

public class LcsProvider extends ContentProvider {
    private static final String TAG = LcsProvider.class.getSimpleName();

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "joss.jacobo.lol.lcs.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    public static final String QUERY_NOTIFY = "QUERY_NOTIFY";
    public static final String QUERY_GROUP_BY = "QUERY_GROUP_BY";

    private static final int URI_TYPE_MATCHES = 0;
    private static final int URI_TYPE_MATCHES_ID = 1;

    private static final int URI_TYPE_NEWS = 2;
    private static final int URI_TYPE_NEWS_ID = 3;

    private static final int URI_TYPE_PLAYERS = 4;
    private static final int URI_TYPE_PLAYERS_ID = 5;

    private static final int URI_TYPE_REPLAYS = 6;
    private static final int URI_TYPE_REPLAYS_ID = 7;

    private static final int URI_TYPE_STANDINGS = 8;
    private static final int URI_TYPE_STANDINGS_ID = 9;

    private static final int URI_TYPE_TEAM_DETAILS = 10;
    private static final int URI_TYPE_TEAM_DETAILS_ID = 11;

    private static final int URI_TYPE_TEAMS = 12;
    private static final int URI_TYPE_TEAMS_ID = 13;

    private static final int URI_TYPE_TOURNAMENTS = 14;
    private static final int URI_TYPE_TOURNAMENTS_ID = 15;

    private static final int URI_TYPE_TWEETS = 16;
    private static final int URI_TYPE_TWEETS_ID = 17;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, MatchesColumns.TABLE_NAME, URI_TYPE_MATCHES);
        URI_MATCHER.addURI(AUTHORITY, MatchesColumns.TABLE_NAME + "/#", URI_TYPE_MATCHES_ID);
        URI_MATCHER.addURI(AUTHORITY, NewsColumns.TABLE_NAME, URI_TYPE_NEWS);
        URI_MATCHER.addURI(AUTHORITY, NewsColumns.TABLE_NAME + "/#", URI_TYPE_NEWS_ID);
        URI_MATCHER.addURI(AUTHORITY, PlayersColumns.TABLE_NAME, URI_TYPE_PLAYERS);
        URI_MATCHER.addURI(AUTHORITY, PlayersColumns.TABLE_NAME + "/#", URI_TYPE_PLAYERS_ID);
        URI_MATCHER.addURI(AUTHORITY, ReplaysColumns.TABLE_NAME, URI_TYPE_REPLAYS);
        URI_MATCHER.addURI(AUTHORITY, ReplaysColumns.TABLE_NAME + "/#", URI_TYPE_REPLAYS_ID);
        URI_MATCHER.addURI(AUTHORITY, StandingsColumns.TABLE_NAME, URI_TYPE_STANDINGS);
        URI_MATCHER.addURI(AUTHORITY, StandingsColumns.TABLE_NAME + "/#", URI_TYPE_STANDINGS_ID);
        URI_MATCHER.addURI(AUTHORITY, TeamDetailsColumns.TABLE_NAME, URI_TYPE_TEAM_DETAILS);
        URI_MATCHER.addURI(AUTHORITY, TeamDetailsColumns.TABLE_NAME + "/#", URI_TYPE_TEAM_DETAILS_ID);
        URI_MATCHER.addURI(AUTHORITY, TeamsColumns.TABLE_NAME, URI_TYPE_TEAMS);
        URI_MATCHER.addURI(AUTHORITY, TeamsColumns.TABLE_NAME + "/#", URI_TYPE_TEAMS_ID);
        URI_MATCHER.addURI(AUTHORITY, TournamentsColumns.TABLE_NAME, URI_TYPE_TOURNAMENTS);
        URI_MATCHER.addURI(AUTHORITY, TournamentsColumns.TABLE_NAME + "/#", URI_TYPE_TOURNAMENTS_ID);
        URI_MATCHER.addURI(AUTHORITY, TweetsColumns.TABLE_NAME, URI_TYPE_TWEETS);
        URI_MATCHER.addURI(AUTHORITY, TweetsColumns.TABLE_NAME + "/#", URI_TYPE_TWEETS_ID);
    }

    public LcsSQLiteOpenHelper mLcsSQLiteOpenHelper;

    @Override
    public boolean onCreate() {
        mLcsSQLiteOpenHelper = LcsSQLiteOpenHelper.newInstance(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_MATCHES:
                return TYPE_CURSOR_DIR + MatchesColumns.TABLE_NAME;
            case URI_TYPE_MATCHES_ID:
                return TYPE_CURSOR_ITEM + MatchesColumns.TABLE_NAME;

            case URI_TYPE_NEWS:
                return TYPE_CURSOR_DIR + NewsColumns.TABLE_NAME;
            case URI_TYPE_NEWS_ID:
                return TYPE_CURSOR_ITEM + NewsColumns.TABLE_NAME;

            case URI_TYPE_PLAYERS:
                return TYPE_CURSOR_DIR + PlayersColumns.TABLE_NAME;
            case URI_TYPE_PLAYERS_ID:
                return TYPE_CURSOR_ITEM + PlayersColumns.TABLE_NAME;

            case URI_TYPE_REPLAYS:
                return TYPE_CURSOR_DIR + ReplaysColumns.TABLE_NAME;
            case URI_TYPE_REPLAYS_ID:
                return TYPE_CURSOR_ITEM + ReplaysColumns.TABLE_NAME;

            case URI_TYPE_STANDINGS:
                return TYPE_CURSOR_DIR + StandingsColumns.TABLE_NAME;
            case URI_TYPE_STANDINGS_ID:
                return TYPE_CURSOR_ITEM + StandingsColumns.TABLE_NAME;

            case URI_TYPE_TEAM_DETAILS:
                return TYPE_CURSOR_DIR + TeamDetailsColumns.TABLE_NAME;
            case URI_TYPE_TEAM_DETAILS_ID:
                return TYPE_CURSOR_ITEM + TeamDetailsColumns.TABLE_NAME;

            case URI_TYPE_TEAMS:
                return TYPE_CURSOR_DIR + TeamsColumns.TABLE_NAME;
            case URI_TYPE_TEAMS_ID:
                return TYPE_CURSOR_ITEM + TeamsColumns.TABLE_NAME;

            case URI_TYPE_TOURNAMENTS:
                return TYPE_CURSOR_DIR + TournamentsColumns.TABLE_NAME;
            case URI_TYPE_TOURNAMENTS_ID:
                return TYPE_CURSOR_ITEM + TournamentsColumns.TABLE_NAME;

            case URI_TYPE_TWEETS:
                return TYPE_CURSOR_DIR + TweetsColumns.TABLE_NAME;
            case URI_TYPE_TWEETS_ID:
                return TYPE_CURSOR_ITEM + TweetsColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (BuildConfig.DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        final String table = uri.getLastPathSegment();
        final long rowId = mLcsSQLiteOpenHelper.getWritableDatabase().insert(table, null, values);
        String notify;
        if (rowId != -1 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return uri.buildUpon().appendEncodedPath(String.valueOf(rowId)).build();
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (BuildConfig.DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        final String table = uri.getLastPathSegment();
        final SQLiteDatabase db = mLcsSQLiteOpenHelper.getWritableDatabase();
        int res = 0;
        db.beginTransaction();
        try {
            for (final ContentValues v : values) {
                final long id = db.insert(table, null, v);
                db.yieldIfContendedSafely();
                if (id != -1) {
                    res++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return res;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        final QueryParams queryParams = getQueryParams(uri, selection);
        final int res = mLcsSQLiteOpenHelper.getWritableDatabase().update(queryParams.table, values, queryParams.selection, selectionArgs);
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return res;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (BuildConfig.DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        final QueryParams queryParams = getQueryParams(uri, selection);
        final int res = mLcsSQLiteOpenHelper.getWritableDatabase().delete(queryParams.table, queryParams.selection, selectionArgs);
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return res;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final String groupBy = uri.getQueryParameter(QUERY_GROUP_BY);
        if (BuildConfig.DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + groupBy);
        final QueryParams queryParams = getQueryParams(uri, selection);
        final Cursor res = mLcsSQLiteOpenHelper.getReadableDatabase().query(queryParams.table, projection, queryParams.selection, selectionArgs, groupBy,
                null, sortOrder == null ? queryParams.orderBy : sortOrder);
        res.setNotificationUri(getContext().getContentResolver(), uri);
        return res;
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        SQLiteDatabase db = mLcsSQLiteOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            int numOperations = operations.size();
            ContentProviderResult[] results = new ContentProviderResult[numOperations];
            int i = 0;
            for (ContentProviderOperation operation : operations) {
                results[i] = operation.apply(this, results, i);
                if (operation.isYieldAllowed()) {
                    db.yieldIfContendedSafely();
                }
                i++;
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

    private static class QueryParams {
        public String table;
        public String selection;
        public String orderBy;
    }

    private QueryParams getQueryParams(Uri uri, String selection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_MATCHES:
            case URI_TYPE_MATCHES_ID:
                res.table = MatchesColumns.TABLE_NAME;
                res.orderBy = MatchesColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_NEWS:
            case URI_TYPE_NEWS_ID:
                res.table = NewsColumns.TABLE_NAME;
                res.orderBy = NewsColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_PLAYERS:
            case URI_TYPE_PLAYERS_ID:
                res.table = PlayersColumns.TABLE_NAME;
                res.orderBy = PlayersColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_REPLAYS:
            case URI_TYPE_REPLAYS_ID:
                res.table = ReplaysColumns.TABLE_NAME;
                res.orderBy = ReplaysColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_STANDINGS:
            case URI_TYPE_STANDINGS_ID:
                res.table = StandingsColumns.TABLE_NAME;
                res.orderBy = StandingsColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_TEAM_DETAILS:
            case URI_TYPE_TEAM_DETAILS_ID:
                res.table = TeamDetailsColumns.TABLE_NAME;
                res.orderBy = TeamDetailsColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_TEAMS:
            case URI_TYPE_TEAMS_ID:
                res.table = TeamsColumns.TABLE_NAME;
                res.orderBy = TeamsColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_TOURNAMENTS:
            case URI_TYPE_TOURNAMENTS_ID:
                res.table = TournamentsColumns.TABLE_NAME;
                res.orderBy = TournamentsColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_TWEETS:
            case URI_TYPE_TWEETS_ID:
                res.table = TweetsColumns.TABLE_NAME;
                res.orderBy = TweetsColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_MATCHES_ID:
            case URI_TYPE_NEWS_ID:
            case URI_TYPE_PLAYERS_ID:
            case URI_TYPE_REPLAYS_ID:
            case URI_TYPE_STANDINGS_ID:
            case URI_TYPE_TEAM_DETAILS_ID:
            case URI_TYPE_TEAMS_ID:
            case URI_TYPE_TOURNAMENTS_ID:
            case URI_TYPE_TWEETS_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = BaseColumns._ID + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = BaseColumns._ID + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }

    public static Uri notify(Uri uri, boolean notify) {
        return uri.buildUpon().appendQueryParameter(QUERY_NOTIFY, String.valueOf(notify)).build();
    }

    public static Uri groupBy(Uri uri, String groupBy) {
        return uri.buildUpon().appendQueryParameter(QUERY_GROUP_BY, groupBy).build();
    }
}
