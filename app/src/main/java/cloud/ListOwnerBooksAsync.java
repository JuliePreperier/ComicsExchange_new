package cloud;

import android.os.AsyncTask;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import BDD.DbHelper;
import entities.ownerBooksApi.OwnerBooksApi;
import entities.ownerBooksApi.model.OwnerBooks;


/**
 * Created by Julie on 15.05.2017.
 */

public class ListOwnerBooksAsync extends AsyncTask<Void, Void, List<OwnerBooks>> {


    private static OwnerBooksApi ownerBooksApi = null;
    private DbHelper db;

    public ListOwnerBooksAsync(DbHelper db) {
        this.db = db;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<OwnerBooks> doInBackground(Void... params) {
        if (ownerBooksApi == null) {
            OwnerBooksApi.Builder builder = new OwnerBooksApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
            builder.setRootUrl("https://exchangecomic-167413.appspot.com/_ah/api/");
            builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });
            ownerBooksApi = builder.build();
        }

        try {

            return ownerBooksApi.list().execute().getItems();
        } catch (IOException e) {
            return new ArrayList<OwnerBooks>();
        }
    }

    @Override
    protected void onPostExecute(List<OwnerBooks> ownerBooks) {


        if (ownerBooks != null) {
            db.fromCloudOwnerBook(ownerBooks);
        }
    }
}
