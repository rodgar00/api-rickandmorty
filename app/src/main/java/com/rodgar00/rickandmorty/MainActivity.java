package com.rodgar00.rickandmorty;


import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import android.os.Handler;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView characterRecycler;
    CharacterRecycler adapter;
    ArrayList<CharacterModel> charactersList = new ArrayList<>();
    TextInputEditText searchBar, speciesBar;

    private Handler searchHandler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;
    private static final long SEARCH_DELAY = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        characterRecycler = findViewById(R.id.characterRecycler);
        int numberOfColumns = 2;
        characterRecycler.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new CharacterRecycler(this, charactersList);
        characterRecycler.setAdapter(adapter);

        searchBar = findViewById(R.id.searchBar);
        speciesBar = findViewById(R.id.speciesBar);

        TextWatcher watcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchRunnable != null) {
                    searchHandler.removeCallbacks(searchRunnable);
                }
                searchRunnable = () -> searchCharacter(
                        searchBar.getText().toString(),
                        speciesBar.getText().toString()
                );
                searchHandler.postDelayed(searchRunnable, SEARCH_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        searchBar.addTextChangedListener(watcher);
        speciesBar.addTextChangedListener(watcher);

    }

    private void searchCharacter(String name, String species) {
        if ((name == null || name.isEmpty()) && (species == null || species.isEmpty())) {
            charactersList.clear();
            adapter.notifyDataSetChanged();
            return;
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CharacterResponse> call = apiInterface.getCharacters(
                name.isEmpty() ? null : name,
                species.isEmpty() ? null : species
        );

        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                if (response.body() != null && response.body().characters != null) {
                    charactersList.clear();
                    for (CharacterResponse.CharacterItem c : response.body().characters) {
                        charactersList.add(new CharacterModel(c.characterName, c.characterImageUrl));
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                Log.e("API Error", "No se pudo cargar los personajes", t);
            }
        });
    }
}
