package com.rodgar00.rickandmorty;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CharacterRecycler extends RecyclerView.Adapter<CharacterRecycler.CharacterViewHolder> {
    Context context;
    ArrayList<CharacterModel> characterModels;
    public CharacterRecycler(Context context, ArrayList<CharacterModel> characterModels) {
        this.context = context;
        this.characterModels = characterModels;
    }


    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tarjeta, parent, false);
        return new CharacterRecycler.CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        holder.characterName.setText(characterModels.get(position).getCharacterName());
        String url = characterModels.get(position).getCharacterImageUrl();
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.rickymortyfondo)
                .into(holder.characterImage);
    }


    @Override
    public int getItemCount() {
        return characterModels.size();
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder {
        TextView characterName;
        ImageView characterImage;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            characterName = itemView.findViewById(R.id.textoTarjetaCharacter);
            characterImage = itemView.findViewById(R.id.imagenTarjetaCharacter);
        }
    }
}