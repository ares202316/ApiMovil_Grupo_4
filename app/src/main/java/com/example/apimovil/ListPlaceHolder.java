package com.example.apimovil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apimovil.Config.Posts;
import com.example.apimovil.Config.RestApiMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListPlaceHolder extends AppCompatActivity {

    private RequestQueue Queue;
    private ListView listView;
    private ArrayAdapter<String> adp;
    private List<String> Arreglo;
    Button regresar;

    List<Posts> potsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_place_holder);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listplace);
        Arreglo = new ArrayList<>();
        adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Arreglo);
        listView.setAdapter(adp);
        regresar = (Button) findViewById(R.id.btn_regresar);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPlaceHolder.this, MainActivity.class);
                startActivity(intent);
            }
        });

        RellenarDatos();

    }

    private void RellenarDatos() {
        Queue = Volley.newRequestQueue(this);
        JsonArrayRequest Req = new JsonArrayRequest(Request.Method.GET, RestApiMethods.EndpointPosts, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject post = response.getJSONObject(i);
                            String id = post.getString("id");
                            String title = post.getString("title");
                            String body = post.getString("body");
                            String data = "Id= " +id+
                                    "\n Title= "+title+
                                    "\n Body= "+body+"\n";
                            Arreglo.add(data);
                        }
                        adp.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Log.i("Error", "onResponse: "+ e.toString());
                    }
                }, e -> Log.i("Error", "onErrorResponse: "+ e.toString()));
        Queue.add(Req);
    }
}