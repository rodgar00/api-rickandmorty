package com.rodgar00.rickandmorty;



public class CharacterModel {
    public String characterName;
    public String characterImageUrl;

    public CharacterModel(String characterName, String characterImageUrl) {
        this.characterName = characterName;
        this.characterImageUrl = characterImageUrl;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getCharacterImageUrl() {
        return characterImageUrl;
    }
}