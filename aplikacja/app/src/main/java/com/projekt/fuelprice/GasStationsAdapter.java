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

    private GasStation[] gasStations = new GasStation[0];
    private Context context;

    public GasStationsAdapter(Context context) {
        this.context = context;
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
        viewHolder.cena.setText(String.valueOf(gasStations[i].price95));
        viewHolder.odleglosc.setText("x");
        switch (gasStations[i].brandName){
            case "Shell":
                viewHolder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.shell));
                break;
            case "Orlen":
                viewHolder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.orlen));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return gasStations.length;
    }

    public void setGasStations(GasStation[] gasStations){
        this.gasStations = gasStations;
        notifyDataSetChanged();
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
