package cloud;

import android.os.AsyncTask;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import java.io.IOException;
import BDD.DbHelper;
import entities.authorsApi.AuthorsApi;
import entities.authorsApi.model.Authors;

/**
 * Created by Julie on 15.05.2017.
 */

public class InsertAuthorsAsync extends AsyncTask<Void, Void, Authors> {

    private static AuthorsApi authorsApi = null;
    private DbHelper db;
    private Authors authors;

    public InsertAuthorsAsync(Authors authors) {
        this.authors = authors;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Authors doInBackground(Void... params) {
        if (authorsApi == null) {
            AuthorsApi.Builder builder = new AuthorsApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
            builder.setRootUrl("https://exchangecomic-167413.appspot.com/_ah/api/");
            builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });
            authorsApi = builder.build();
        }

        try {
            if (authors != null) {
                authorsApi.insert(authors).execute();
            }
            return authors;
        } catch (IOException e) {
            return new Authors();
        }
    }

}