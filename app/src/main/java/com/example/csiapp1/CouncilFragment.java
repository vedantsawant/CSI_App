package com.example.csiapp1;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class CouncilFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        List<String> list = new ArrayList<>();
        list.add("SE COUNCIL");
        list.add("TE COUNCIL");
        list.add("BE COUNCIL");
        list.add("FACULTY IN CHARGE");
        View view = inflater.inflate(R.layout.council_fragment,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.council_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerViewAdapter(list));
        return view;
    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private CardView mCardView;
        private TextView mTextView;
        public View mView;
        public ClipData.Item currentItem;



        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.card_view, container, false));
            mCardView = itemView.findViewById(R.id.council_card_view);
            mTextView = itemView.findViewById(R.id.council_textHolder);
            mView = this.mCardView;
        }

    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

        private List<String> mList;

        public RecyclerViewAdapter(List<String> list){
            this.mList = list;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            return new RecyclerViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
            holder.mTextView.setText(mList.get(position));
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getActivity(), mList.get(position), Toast.LENGTH_SHORT).show();
                    if(mList.get(position) == "SE COUNCIL"){
                        Intent intent = new Intent(getActivity(), SE_council.class);
                        startActivity(intent);
                    }
                    if(mList.get(position) == "TE COUNCIL"){
                        Intent intent = new Intent(getActivity(), TE_council.class);
                        startActivity(intent);
                    }
                    if(mList.get(position) == "BE COUNCIL"){
                        Intent intent = new Intent(getActivity(), BE_council.class);
                        startActivity(intent);
                    }
                    if(mList.get(position) == "FACULTY IN CHARGE"){
                        Intent intent = new Intent(getActivity(), faculty_incharge.class);
                        startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }

}

