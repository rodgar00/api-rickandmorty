package com.rodgar00.rickandmorty;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Character {

    @SerializedName("results")
    public List<CharacterItem> characters = new ArrayList<>();

    public static class CharacterItem {

        @SerializedName("name")
        public String characterName;

        @SerializedName("image")
        public String characterImageUrl;

        @SerializedName("id")
        public String characterId;
    }
}
