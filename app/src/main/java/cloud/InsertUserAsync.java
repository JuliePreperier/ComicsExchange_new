package cloud;


import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import java.io.IOException;
import BDD.DbHelper;
import entities.userApi.UserApi;
import entities.userApi.model.User;

/**
 * Created by Julie on 15.05.2017.
 */

public class InsertUserAsync extends AsyncTask<Void, Void, User> {

    private static UserApi userApi = null;
    private DbHelper db;
    private User user;

    public InsertUserAsync(User user) {
        this.user = user;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected User doInBackground(Void... params) {
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
            if (user != null) {
                userApi.insert(user).execute();
            }
            return user;
        } catch (IOException e) {
            return new User();
        }
    }

}