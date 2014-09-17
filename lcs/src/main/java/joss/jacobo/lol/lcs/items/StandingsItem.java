package joss.jacobo.lol.lcs.items;

import joss.jacobo.lol.lcs.interfaces.StandingsType;
import joss.jacobo.lol.lcs.provider.standings.StandingsCursor;

/**
 * Created by Joss on 7/22/2014
 */
public class StandingsItem extends OverviewItem implements StandingsType{

    public String league;
    public int week;
    public int position;
    public String teamName;
    public int wins;
    public int losses;
    public int delta;
    public String teamLogoUrl;
    public String teamAbrev;
    public String group;

    public boolean showDivider = true;
    public int type = StandingsType.TYPE_ITEM;

    public StandingsItem(int type, String league, int week, int position, String teamName, int wins, int losses){
        this.type = type;
        this.league = league;
        this.week = week;
        this.position = position;
        this.teamName = teamName;
        this.wins = wins;
        this.losses = losses;
    }

    public StandingsItem(StandingsCursor standingsCursor) {
        this.league = standingsCursor.getTournamentAbrev();
        this.week = standingsCursor.getStandingWeek();
        this.position = standingsCursor.getStandingPosition();
        this.wins = standingsCursor.getWins();
        this.losses = standingsCursor.getLosses();
        this.delta = standingsCursor.getDelta();
        this.teamAbrev = standingsCursor.getTeamAbrev();
        this.teamName = standingsCursor.getTeamName();
        this.group = standingsCursor.getTournamentGroup();
    }

    public StandingsItem(String group, int type){
        this.group = group;
        this.type = type;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String getSeparatorText() {
        return group;
    }
}
