package com.rodgar00.rickandmorty;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("character")
    Call<CharacterResponse> getCharacters(
            @Query("name") String name,
            @Query("species") String species
    );
}
