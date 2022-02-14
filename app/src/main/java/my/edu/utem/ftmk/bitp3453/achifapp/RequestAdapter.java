package my.edu.utem.ftmk.bitp3453.achifapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Vector;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder>  {

    private Vector<Request> requests;
    private Context context;

    public RequestAdapter(Context context, Vector<Request> requests) {
        this.requests = requests;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater.from(context).inflate(R.layout.request_item, parent, false);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.request_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Request request = requests.get(position);

        holder.txtname.setText(request.getName());
        holder.txtdescription.setText(request.getDescription());
        holder.txtphonenumber.setText(request.getPhonenumber());
        holder.txtquantity.setText(request.getQuantity());
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        private final TextView txtname, txtphonenumber, txtquantity, txtdescription;
        private GoogleMap mapCurrent;
        MapView map;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtname = itemView.findViewById(R.id.item_request_title);
            txtdescription = itemView.findViewById(R.id.item_request_description);
            txtphonenumber = itemView.findViewById(R.id.item_request_phone_no);
            txtquantity = itemView.findViewById(R.id.item_request_quantity);
        }

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            mapCurrent = googleMap;
        }

    }

}
