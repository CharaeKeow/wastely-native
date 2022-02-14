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

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder> {

    private Vector<Donation> donations;
    private Context context;

    public DonationAdapter(Context context, Vector<Donation> donations) {
        this.donations = donations;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater.from(context).inflate(R.layout.donation_item, parent, false);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.donation_item, parent, false);
        view.setOnClickListener(v -> {
            TextView donationTitle = v.findViewById(R.id.item_donation_title);
            TextView quantity = v.findViewById(R.id.item_donation_quantity);
            TextView description = v.findViewById(R.id.item_donation_description);
            TextView phoneNo = v.findViewById(R.id.item_donation_phone_no);
            TextView pickUpTime = v.findViewById(R.id.item_donation_pickup_time);

            Intent intent = new Intent(v.getContext(), DonationDetailActivity.class);
            intent.putExtra("donationTitle", donationTitle.getText().toString());
            intent.putExtra("quantity", quantity.getText().toString());
            intent.putExtra("description", description.getText().toString());
            intent.putExtra("phoneNo", phoneNo.getText().toString());
            intent.putExtra("pickUpTime", pickUpTime.getText().toString());

            context.startActivity(intent);
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Donation donation = donations.get(position);

        holder.txtDonationTitle.setText(donation.getDonationTitle());
        holder.txtDescription.setText(donation.getDescription());
        holder.txtPhoneNo.setText(donation.getPhoneNo());
        holder.txtPickUpTime.setText(donation.getPickUpTime());
        holder.txtQuantity.setText(donation.getQuantity());

        GoogleMap thisMap = holder.mapCurrent;
        if (thisMap != null) {
            thisMap.addMarker(new MarkerOptions().position(new LatLng(donation.getLatitude(), donation.getLongitude())));
        }
    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        private final TextView txtDonationTitle, txtPhoneNo, txtPickUpTime, txtQuantity, txtDescription;
        private GoogleMap mapCurrent;
        MapView map;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            map = itemView.findViewById(R.id.item_donation_map);
            if (map != null) {
                //map.onCreate(null);
               // map.onResume();
                //map.getMapAsync(this);
            }

            txtDonationTitle = itemView.findViewById(R.id.item_donation_title);
            txtDescription = itemView.findViewById(R.id.item_donation_description);
            txtPhoneNo = itemView.findViewById(R.id.item_donation_phone_no);
            txtPickUpTime = itemView.findViewById(R.id.item_donation_pickup_time);
            txtQuantity = itemView.findViewById(R.id.item_donation_quantity);
        }

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            mapCurrent = googleMap;
        }

    }
}
