package cloud;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import BDD.DbHelper;
import entities.userApi.UserApi;

/**
 * Created by Julie on 17.05.2017.
 */

public class DeleteUserAsync extends AsyncTask<Void, Void, Long> {
    private static UserApi userApi = null;
    private DbHelper db;
    private Long id =-1l;

    public DeleteUserAsync(Long id) {
        this.id = id;
        Log.e("debugCloud", String.valueOf(id));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Long doInBackground(Void... params) {
        if (userApi == null) {
            UserApi.Builder builder = new UserApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null);
            builder.setRootUrl("https://exchangecomic-167413.appspot.com/_ah/api/");
            builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });
            userApi = builder.build();
        }

        try {
            if(id != -1l)
                userApi.remove(id).execute();


            return id;
        } catch (IOException e) {
            return -2l;
        }
    }
}
