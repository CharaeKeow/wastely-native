package my.edu.utem.ftmk.bitp3453.achifapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.donation_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Donation donation = donations.get(position);

        holder.txtDonationTitle.setText(donation.getDonationTitle());
        holder.txtDescription.setText(donation.getDescription());
        holder.txtPhoneNo.setText(donation.getPhoneNo());
        holder.txtPickUpTime.setText(donation.getPickUpTime());
        holder.txtQuantity.setText(donation.getQuantity());
    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtDonationTitle, txtPhoneNo, txtPickUpTime, txtQuantity, txtDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDonationTitle = itemView.findViewById(R.id.item_donation_title);
            txtDescription = itemView.findViewById(R.id.item_donation_description);
            txtPhoneNo = itemView.findViewById(R.id.item_donation_phone_no);
            txtPickUpTime = itemView.findViewById(R.id.item_donation_pickup_time);
            txtQuantity = itemView.findViewById(R.id.item_donation_quantity);
        }
    }
}
