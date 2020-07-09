package com.example.bakery.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakery.R;
import com.example.bakery.model.Recipe;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private List<Recipe> recipeList;
    final private CardClickListener mOnClickListener;

    public MainAdapter(List<Recipe> arrayList, CardClickListener listener) {
        recipeList = arrayList;
        mOnClickListener = listener;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public Recipe getRecipeByPosition(int position) {
        return recipeList.get(position);
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.main_item, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if ((recipeList == null) || (recipeList.isEmpty())) {
            return 0;
        }
        return recipeList.size();
    }


    public interface CardClickListener {
        void onCardClick(int clickedItemIndex);
    }

    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cardTitle;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            cardTitle = itemView.findViewById(R.id.card_title);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            Recipe recipe = recipeList.get(position);
            cardTitle.setText(recipe.getName());
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onCardClick(clickedPosition);
        }
    }
}
