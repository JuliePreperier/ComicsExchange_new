package cloud;

import android.os.AsyncTask;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import java.io.IOException;
import BDD.DbHelper;
import entities.serieApi.SerieApi;
import entities.serieApi.model.Serie;

/**
 * Created by Julie on 15.05.2017.
 */

public class InsertSerieAsync extends AsyncTask<Void, Void, Serie> {

    private static SerieApi serieApi = null;
    private DbHelper db;
    private Serie serie;

    public InsertSerieAsync(Serie serie) {
        this.serie = serie;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Serie doInBackground(Void... params) {
        if (serieApi == null) {
            SerieApi.Builder builder = new SerieApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
            builder.setRootUrl("https://exchangecomic-167413.appspot.com/_ah/api/");
            builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });
            serieApi = builder.build();
        }

        try {
            if (serie != null) {
                serieApi.insert(serie).execute();
            }
            return serie;
        } catch (IOException e) {
            return new Serie();
        }
    }

}