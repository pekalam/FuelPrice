package com.projekt.fuelprice;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.projekt.fuelprice.data.GasStation;

import java.util.ArrayList;



public class GasStationsAdapter extends RecyclerView.Adapter<GasStationsAdapter.ViewHolder> {

    private ArrayList<GasStation> gasStations = new ArrayList<>();

    public GasStationsAdapter(ArrayList<GasStation> Stations) {
        gasStations=Stations;
    }

    

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        /*viewHolder.cena.setText();
        viewHolder.odleglosc.setText();*/
        viewHolder.button.setText("Nawigoj");        
    }

    @Override
    public int getItemCount() {
        return gasStations.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView cena;
        TextView odleglosc;
        Button button;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image1);
            cena = itemView.findViewById(R.id.tekst1);
            odleglosc = itemView.findViewById(R.id.tekst2);
            button =  itemView.findViewById(R.id.btn1);
            linearLayout = itemView.findViewById(R.id.list_item);

        }
    }
}
