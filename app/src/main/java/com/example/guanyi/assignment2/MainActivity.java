package com.example.guanyi.assignment2;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity implements AsyncResponse {
    DBConnector dbConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getData(View view) {
        EditText txteDbName = (EditText)findViewById(R.id.txteDBName);
        EditText txteCollectionName = (EditText)findViewById(R.id.txteCollectionName);
        EditText txteAPIKey = (EditText)findViewById(R.id.txteAPIKey);

        String dbName = txteDbName.getText().toString();
        String collectionName = txteCollectionName.getText().toString();
        String APIKey = txteAPIKey.getText().toString();

        String url = "https://api.mongolab.com/api/1/databases/" + dbName + "/collections/" + collectionName + "/?apiKey=" + APIKey;
        MongoConnector mc = new MongoConnector(this);
        mc.execute(url);
    }

    public void getMongoResponse(String result) {
        dbConnector = new DBConnector(getApplicationContext());
        dbConnector.open();
        try {
            JSONArray studentList = new JSONArray(result);
            for( int i = 0; i < studentList.length(); i++) {
                JSONObject student = studentList.getJSONObject(i);
                String first_name = student.get("first_name").toString();
                String last_name = student.get("last_name").toString();
                String email_address = student.get("email_address").toString();
                int student_number =Integer.parseInt( student.get("student_number").toString());
                System.out.println(first_name + " " + last_name + " " + email_address + " " + student_number);

                dbConnector.createStudent(student_number, first_name, last_name, email_address);
            }
        }catch( Exception e) {
            e.printStackTrace();
        }

        TextView txtVStudentInfo = (TextView)findViewById(R.id.textVStudentInfo);
        ArrayList<Student> students = dbConnector.getAllStudents();
        for (Student s : students) {
            txtVStudentInfo.append( s.getFirstName() + ", " + s.getLastName() + ", " + s.getEmail() + ", " + s.getId() + "\n" );
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbConnector != null)
            dbConnector.close();
    }

    private class MongoConnector extends AsyncTask <String, Void, String> {
        private AsyncResponse response;

        public MongoConnector(AsyncResponse response) {
            this.response = response;
        }

        @Override
        protected String doInBackground(String... url) {
            String result = "";
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url[0]);
                ResponseHandler<String> handler = new BasicResponseHandler();
                result = client.execute(get, handler);


            }catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            //System.out.println(result);
            response.getMongoResponse(result);
        }
    }
}
