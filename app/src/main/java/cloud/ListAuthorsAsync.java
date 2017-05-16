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
import entities.authorsApi.AuthorsApi;
import entities.authorsApi.model.Authors;

/**
 * Created by Julie on 15.05.2017.
 */

public class ListAuthorsAsync extends AsyncTask<Void, Void, List<Authors>> {


        private static AuthorsApi authorsAPI = null;
        private DbHelper db;

        public ListAuthorsAsync(DbHelper db) {
            this.db = db;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Authors> doInBackground(Void... params) {
            if (authorsAPI == null) {
                AuthorsApi.Builder builder = new AuthorsApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null);
                builder.setRootUrl("https://exchangecomic-167413.appspot.com/_ah/api/");
                builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
                authorsAPI = builder.build();
            }

            try {

                return authorsAPI.list().execute().getItems();
            } catch (IOException e) {
                return new ArrayList<Authors>();
            }
        }

        @Override
        protected void onPostExecute(List<Authors> authors) {


            if (authors != null) {
                db.fromCloudAuhtor(authors);
            }
        }
}
