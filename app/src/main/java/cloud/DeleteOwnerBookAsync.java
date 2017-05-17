package cloud;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import BDD.DbHelper;
import entities.ownerBooksApi.OwnerBooksApi;

/**
 * Created by Julie on 17.05.2017.
 */

public class DeleteOwnerBookAsync extends AsyncTask<Void, Void, Long> {

    private static OwnerBooksApi ownerBooksApi = null;
    private DbHelper db;
    private Long id =-1l;

    public DeleteOwnerBookAsync(Long id) {
        this.id = id;
        Log.e("debugCloud", String.valueOf(id));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Long doInBackground(Void... params) {
        if (ownerBooksApi == null) {
            OwnerBooksApi.Builder builder = new OwnerBooksApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null);
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
            if(id != -1l)
                ownerBooksApi.remove(id).execute();


            return id;
        } catch (IOException e) {
            return -2l;
        }
    }
}
