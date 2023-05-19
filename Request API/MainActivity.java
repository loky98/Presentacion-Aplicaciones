import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private Button requestButton;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);
        requestButton = findViewById(R.id.req_button);
        editText = findViewById(R.id.edit_text);

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestAPI();
            }
        });
    }

    private void requestAPI(){
        // Obtener numero diguitado
        final String message = editText.getText().toString();

        // Realizar la solicitud GET
        new GetDataTask().execute(message);
    }

    private class GetDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String result = null;
            String num = voids[0];

            try {
                URL url = new URL("https://rickandmortyapi.com/api/character/"+num);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                if (stringBuilder.length() == 0) {
                    return null;
                }

                result = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    // Obtener los datos del JSON
                    String name = jsonObject.getString("name");
                    String status = jsonObject.getString("status");
                    String species = jsonObject.getString("species");
                    String imageUrl = jsonObject.getString("image");

                    // Mostrar los datos en el TextView
                    String output = "Name: " + name + "\n" +
                            "Status: " + status + "\n" +
                            "Species: " + species;

                    textViewResult.setText(output);

                    // Cargar y mostrar la imagen utilizando Picasso
                    ImageView imageView = findViewById(R.id.image_view);
                    Picasso.get().load(imageUrl).into(imageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("MainActivity", "No se pudo obtener el resultado JSON");
            }
        }

    }
}
