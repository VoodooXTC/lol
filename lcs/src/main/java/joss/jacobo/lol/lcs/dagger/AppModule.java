package joss.jacobo.lol.lcs.dagger;

import android.app.Application;
import android.content.Context;

import com.squareup.okhttp.OkHttpClient;

import java.net.URL;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import joss.jacobo.lol.lcs.Datastore;
import joss.jacobo.lol.lcs.MainApp;
import joss.jacobo.lol.lcs.activity.BaseActivity;
import joss.jacobo.lol.lcs.activity.MainActivity;
import joss.jacobo.lol.lcs.fragment.BaseFragment;
import joss.jacobo.lol.lcs.fragment.BaseListFragment;
import joss.jacobo.lol.lcs.fragment.OverviewFragment;
import joss.jacobo.lol.lcs.views.DrawerHeader;

@Module(
        injects = {
                Injector.class,
                MainApp.class,
                BaseActivity.class,
                MainActivity.class,

                BaseFragment.class,
                BaseListFragment.class,
                OverviewFragment.class,

                DrawerHeader.class

}, library = true, complete = true)
public class AppModule {
    private final Application application;
    private final Injector injector;

    public AppModule(Application application) {
        this.application = application;
        this.injector = new Injector(application);
    }

    /**
     * Allow the application context to be injected but require that it be annotated with {@link
     * ForApplication @Annotation} to explicitly differentiate it from an activity context.
     */
    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    Injector providesInjector() {
        return injector;
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient() {
        OkHttpClient httpClient = new OkHttpClient();
        URL.setURLStreamHandlerFactory(httpClient);
        return httpClient;
    }

    @Provides
    @Singleton
    Datastore providesDatastore() {
        return new Datastore(application);
    }

}

