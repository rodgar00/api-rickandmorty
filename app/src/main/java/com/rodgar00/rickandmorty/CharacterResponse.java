package com.rodgar00.rickandmorty;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterResponse {

    @SerializedName("results")
    public List<CharacterItem> characters;

    public static class CharacterItem {
        @SerializedName("name")
        public String characterName;

        @SerializedName("image")
        public String characterImageUrl;
    }
}
