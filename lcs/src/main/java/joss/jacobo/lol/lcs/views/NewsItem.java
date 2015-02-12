package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.model.NewsModel;

/**
 * Created by Joss on 7/31/2014
 */
public class NewsItem extends LinearLayout {

    Context context;
    ImageView image;
    TextView category;
    TextView title;
    TextView author;
    TextView description;

    public NewsItem(Context context) {
        this(context, null);
    }

    public NewsItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewsItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.li_news, this, true);
        image = (ImageView) findViewById(R.id.news_image);
        category = (TextView) findViewById(R.id.news_category);
        title = (TextView) findViewById(R.id.news_title);
        author = (TextView) findViewById(R.id.news_author);
        description = (TextView) findViewById(R.id.news_description);
    }

    public void setContent(NewsModel newsModel){
        Picasso.with(context).load(newsModel.image).into(image);
        category.setText(newsModel.category.toUpperCase());
        title.setText(Html.fromHtml(newsModel.title));
        author.setText(newsModel.author);
        description.setText(newsModel.description);
    }

    public int[] getImageIntrinsicMeasurements(){
        int[] position = new int[2];
        image.getLocationOnScreen(position);
        return position;
    }

    public int getImageWidth(){
        return image.getWidth();
    }

    public int getImageHeight(){
        return image.getHeight();
    }
}
