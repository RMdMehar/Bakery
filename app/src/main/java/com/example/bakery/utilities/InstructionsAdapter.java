package com.example.bakery.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakery.R;
import com.example.bakery.model.Instruction;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.InstructionsViewHolder> {
    private List<Instruction> instructionList;
    final private InstructionClickListener mOnClickListener;

    public InstructionsAdapter(List<Instruction> instructionList, InstructionClickListener listener) {
        this.instructionList = instructionList;
        mOnClickListener = listener;
    }

    public void setInstructionList(List<Instruction> instructionList) {
        this.instructionList = instructionList;
    }

    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.instructions_item, parent, false);
        return new InstructionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if ((instructionList == null) || (instructionList.isEmpty())) {
            return 0;
        }
        return instructionList.size();
    }

    public interface InstructionClickListener {
        void onInstructionClick(int clickedItemIndex);
    }

    public class InstructionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView shortDescription;
        public InstructionsViewHolder(@NonNull View itemView) {
            super(itemView);
            shortDescription = itemView.findViewById(R.id.short_description);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            Instruction instruction = instructionList.get(position);
            shortDescription.setText(instruction.getShortDescription());
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onInstructionClick(clickedPosition);
        }
    }
}
