package cloud;

import android.os.AsyncTask;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import java.io.IOException;
import BDD.DbHelper;
import entities.comicApi.ComicApi;
import entities.comicApi.model.Comic;

/**
 * Created by Julie on 15.05.2017.
 */

public class InsertComicAsync extends AsyncTask<Void, Void, Comic> {

    private static ComicApi comicApi = null;
    private DbHelper db;
    private Comic comic;

    public InsertComicAsync(Comic comic) {
        this.comic = comic;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Comic doInBackground(Void... params) {
        if (comicApi == null) {
            ComicApi.Builder builder = new ComicApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
            builder.setRootUrl("https://exchangecomic-167413.appspot.com/_ah/api/");
            builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });
            comicApi = builder.build();
        }

        try {
            if (comic != null) {
                comicApi.insert(comic).execute();
            }
            return comic;
        } catch (IOException e) {
            return new Comic();
        }
    }

}