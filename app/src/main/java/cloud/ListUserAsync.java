package cloud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.comicsexchange_new.LogInActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import BDD.DbHelper;
import entities.userApi.UserApi;
import entities.userApi.model.User;

/**
 * Created by Julie on 15.05.2017.
 */

public class ListUserAsync extends AsyncTask<Void, Void, List<User>> {


    private static UserApi userAPI = null;
    private DbHelper db;
    public SQLiteDatabase database;

    public ListUserAsync(DbHelper db) {
        this.db = db;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<User> doInBackground(Void... params) {

        if (userAPI == null) {
            UserApi.Builder builder = new UserApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null);
            builder.setRootUrl("https://exchangecomic-167413.appspot.com/_ah/api/");
            builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });
            userAPI = builder.build();
        }

        try {

            return userAPI.list().execute().getItems();
        } catch (IOException e) {
            return new ArrayList<User>();
        }
    }

    @Override
    protected void onPostExecute(List<User> users) {
        new ListAuthorsAsync(db).execute();

        if (users != null) {
            db.fromCloudUser(users);
        }
    }
}

