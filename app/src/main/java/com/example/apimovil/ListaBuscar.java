package com.example.apimovil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.apimovil.Config.RestApiMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaBuscar extends AppCompatActivity {
    private RequestQueue Queue;
    private ListView listView;
    private ArrayAdapter<String> adp;
    private List<String> Arreglo;
    private Button Buscar, regresar;
    private TextView textView2;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_buscar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listabusq);
        Arreglo = new ArrayList<>();
        adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Arreglo);
        listView.setAdapter(adp);
        editText = (EditText) findViewById(R.id.editText);
        textView2 = (TextView) findViewById(R.id.textView2);
        Buscar = (Button) findViewById(R.id.button);
        regresar = (Button) findViewById(R.id.btn_regresar);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(ListaBuscar.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString()==null){
                    Toast.makeText(ListaBuscar.this, "Campo vacio.", Toast.LENGTH_SHORT).show();
                }else{
                    Buscar();
                }
            }
        });


    }

    private void Buscar() {
        Queue = Volley.newRequestQueue(this);
        int idVal = Integer.parseInt(editText.getText().toString());
        String URL = RestApiMethods.EndpointPosts;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject post = response.getJSONObject(idVal-1);
                            String id = post.getString("id");
                            String title = post.getString("title");
                            String body = post.getString("body");
                            String data = "ID= "+id+
                                    "\n Title=\n"+title+
                                    " \n Body=\n"+body;
                            textView2.setText(data);
                        } catch (JSONException e) {
                            Log.i("Error", "onResponse: "+e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.i("Error", "onResponse: "+e.toString());
            }
        });
        Queue.add(request);
    }
}