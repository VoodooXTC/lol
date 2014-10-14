package joss.jacobo.lol.lcs.activity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.model.NewsModel;
import joss.jacobo.lol.lcs.utils.Bootstrap;
import joss.jacobo.lol.lcs.views.CancelableAdView;

/**
 * Created by Joss on 7/31/2014
 */
public class NewsDetailsActivity extends BaseActivity {

    public static final String NEWS_MODEL = "news_model";
    public static final String NEWS_IMAGE_POSITION = "news_image_position";
    public static final String NEWS_IMAGE_WIDTH = "news_image_width";
    public static final String NEWS_IMAGE_HEIGHT = "news_image_height";

    @InjectView(R.id.top_container)
    RelativeLayout topContainer;
    @InjectView(R.id.new_webview_container)
    RelativeLayout webViewContainer;
    @InjectView(R.id.news_details_image)
    ImageView image;
    @InjectView(R.id.news_details_category)
    TextView category;
    @InjectView(R.id.news_details_title)
    TextView title;
    @InjectView(R.id.news_details_author)
    TextView author;
    @InjectView(R.id.news_details_webview)
    WebView webView;
    @InjectView(R.id.news_details_loading)
    LinearLayout loadingView;
    @InjectView(R.id.news_animation_image)
    ImageView animationImage;
    @InjectView(R.id.cancelableAds)
    CancelableAdView cancelableAdView;

    NewsModel newsModel;
    int[] imagePosition;
    int imageWidth;
    int imageHeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.inject(this);

        cancelableAdView.setVisibility(View.GONE);

        Bundle bundle = savedInstanceState != null ? savedInstanceState : getIntent().getExtras();
        Gson gson = new Gson();
        newsModel = gson.fromJson(bundle.getString(NEWS_MODEL), NewsModel.class);
        imagePosition = bundle.getIntArray(NEWS_IMAGE_POSITION);
        imageWidth = bundle.getInt(NEWS_IMAGE_WIDTH);
        imageHeight = bundle.getInt(NEWS_IMAGE_HEIGHT);

        animationImage.setX(imagePosition[0]);
        animationImage.setY(imagePosition[1] - getActionBarHeight() - getStatusBarHeight());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(imageWidth, imageHeight);
        animationImage.setLayoutParams(params);
        Picasso.with(this).load(newsModel.image).into(animationImage);

        showLoading();
        setContent(newsModel);

        setupActionBar();
        onSetActionBarTitle("News", null);

        image.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                image.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                animateImageIn(imagePosition, imageWidth, imageHeight);
            }
        });
    }

    private void animateImageIn(int[] imagePosition, int imageWidth, int imageHeight) {
        /**
         *  .x() take the initial x value as reference and never changes it while it expands the
         *  image, therefor placing the image a little farther to the left than expected.
         *  Adding the initial image position to the final destination to compensate this shift.
         */
        animationImage.animate()
                .x(imagePosition[0])
                .y(0)
                .scaleX(((float)image.getMeasuredWidth()/(float)imageWidth))
                .scaleY(((float)image.getMeasuredHeight())/(float)imageHeight)
                .setDuration(600)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animationImage.setVisibility(View.GONE);

                        Animation.AnimationListener listener = new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                setupWebView();
                                setWebViewContent();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        };
                        Animation fadeIn = AnimationUtils.loadAnimation(NewsDetailsActivity.this, R.anim.fade_in);
                        fadeIn.setAnimationListener(listener);

                        image.setVisibility(View.VISIBLE);
                        category.setVisibility(View.VISIBLE);
                        webViewContainer.setVisibility(View.VISIBLE);
                        title.setVisibility(View.VISIBLE);
                        author.setVisibility(View.VISIBLE);

                        category.startAnimation(fadeIn);
                        webViewContainer.startAnimation(AnimationUtils.loadAnimation(NewsDetailsActivity.this, R.anim.fade_in));
                        title.startAnimation(AnimationUtils.loadAnimation(NewsDetailsActivity.this, R.anim.fade_in));
                        author.startAnimation(AnimationUtils.loadAnimation(NewsDetailsActivity.this, R.anim.fade_in));
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();
    }

    private int getActionBarHeight() {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)){
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putAll(getIntent().getExtras());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
    }

    private void setWebViewContent() {
        webView.setWebViewClient(new LcsWebViewClient());
        webView.loadDataWithBaseURL(Bootstrap.BASE_URL, Bootstrap.wrap(newsModel.content), Bootstrap.MIME_TYPE, Bootstrap.ENCODING, null);
    }

    public void setContent(NewsModel newsModel) {
        Picasso.with(this).load(newsModel.image).into(image);
        category.setText(newsModel.category.toUpperCase());
        title.setText(Html.fromHtml(newsModel.title));
        author.setText(newsModel.author);
    }

    class LcsWebViewClient extends WebViewClient{

        @Override
        public void onPageStarted(WebView wevView, String url, Bitmap favicon) {
            showContent();
        }

        @Override
        public void onPageFinished(WebView webView, String url) {
            cancelableAdView.setVisibility(View.VISIBLE);
            cancelableAdView.initAds();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            return true;
        }

    }

    private void showContent() {
        loadingView.animate().alpha(0).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                loadingView.setVisibility(View.GONE);
            }
        }).start();
    }

    private void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
