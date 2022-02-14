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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
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
            TextView coordinate = v.findViewById(R.id.item_donation_coordinate);
            TextView userID = v.findViewById(R.id.item_donation_donor_id);

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
        holder.txtDescription.setText(String.format("Description %s", donation.getDescription()));
        holder.txtPhoneNo.setText(String.format("Phone number: %s", donation.getPhoneNo()));
        holder.txtPickUpTime.setText(String.format("Pick-up Time: %s", donation.getPickUpTime()));
        holder.txtQuantity.setText(String.format("Quantity: %s", donation.getQuantity()));
        holder.txtDonorID.setText(String.format("Donor ID: %s", donation.getDonorID()));
        holder.txtCoordinate.setText(String.format("Coordinate: %s, %s", donation.getLatitude(), donation.getLongitude()));
    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtDonationTitle, txtPhoneNo, txtPickUpTime, txtQuantity, txtDescription, txtDonorID, txtCoordinate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDonationTitle = itemView.findViewById(R.id.item_donation_title);
            txtDescription = itemView.findViewById(R.id.item_donation_description);
            txtPhoneNo = itemView.findViewById(R.id.item_donation_phone_no);
            txtPickUpTime = itemView.findViewById(R.id.item_donation_pickup_time);
            txtQuantity = itemView.findViewById(R.id.item_donation_quantity);
            txtDonorID = itemView.findViewById(R.id.item_donation_donor_id);
            txtCoordinate = itemView.findViewById(R.id.item_donation_coordinate);
        }
    }
}
