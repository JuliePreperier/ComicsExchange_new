package cloud;

import android.os.AsyncTask;

import com.example.comicsexchange_new.SyncToCloud;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import BDD.DbHelper;
import entities.comicApi.ComicApi;
import entities.comicApi.model.Comic;


/**
 * Created by Julie on 15.05.2017.
 */

public class ListComicAsync extends AsyncTask<Void, Void, List<Comic>> {


    private static ComicApi comicApi = null;
    private DbHelper db;

    public ListComicAsync(DbHelper db) {
        this.db = db;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Comic> doInBackground(Void... params) {
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

            return comicApi.list().execute().getItems();
        } catch (IOException e) {
            return new ArrayList<Comic>();
        }
    }

    @Override
    protected void onPostExecute(List<Comic> comics) {
        if (comics != null) {
            db.fromCloudComic(comics);
        }

        SyncToCloud.setComicUpdatedTrue();
        new ListOwnerBooksAsync(db).execute();
    }
}
