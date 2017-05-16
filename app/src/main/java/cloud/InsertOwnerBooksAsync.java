package cloud;

import android.os.AsyncTask;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import java.io.IOException;
import BDD.DbHelper;
import entities.ownerBooksApi.OwnerBooksApi;
import entities.ownerBooksApi.model.OwnerBooks;

/**
 * Created by Julie on 15.05.2017.
 */

public class InsertOwnerBooksAsync extends AsyncTask<Void, Void, OwnerBooks> {

    private static OwnerBooksApi ownerBooksApi = null;
    private DbHelper db;
    private OwnerBooks ownerBooks;

    public InsertOwnerBooksAsync(OwnerBooks ownerBooks) {
        this.ownerBooks = ownerBooks;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected OwnerBooks doInBackground(Void... params) {
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
            if (ownerBooks != null) {
                ownerBooksApi.insert(ownerBooks).execute();
            }
            return ownerBooks;
        } catch (IOException e) {
            return new OwnerBooks();
        }
    }

}