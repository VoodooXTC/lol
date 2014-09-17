package joss.jacobo.lol.lcs.interfaces;

/**
 * Created by jossayjacobo on 9/17/14
 */
public interface StandingsType {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_SEPARATOR = 1;
    public static final int TYPE_MAX = TYPE_SEPARATOR + 1;

    public int getType();
    public String getSeparatorText();

}
