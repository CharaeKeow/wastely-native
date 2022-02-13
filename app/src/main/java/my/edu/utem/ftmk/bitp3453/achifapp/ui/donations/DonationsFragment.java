package my.edu.utem.ftmk.bitp3453.achifapp.ui.donations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Vector;

import my.edu.utem.ftmk.bitp3453.achifapp.Donation;
import my.edu.utem.ftmk.bitp3453.achifapp.DonationAdapter;
import my.edu.utem.ftmk.bitp3453.achifapp.MainActivity;
import my.edu.utem.ftmk.bitp3453.achifapp.R;
import my.edu.utem.ftmk.bitp3453.achifapp.databinding.FragmentDonationsBinding;

public class DonationsFragment extends Fragment {

    private DonationsViewModel homeViewModel;
    private FragmentDonationsBinding binding;
    private FirebaseFirestore db;
    private Vector<Donation> donations;
    private RecyclerView rcvDonation;
    private DonationAdapter donationAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(DonationsViewModel.class);

        binding = FragmentDonationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rcvDonation = root.findViewById(R.id.rcv_donations);
        db = FirebaseFirestore.getInstance();

        donations = new Vector<>();
        rcvDonation.setLayoutManager(new LinearLayoutManager(MainActivity.context));

        donationAdapter = new DonationAdapter(root.getContext(), donations);
        rcvDonation.setAdapter(donationAdapter);

        db.collection("donation")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                            //loop through the documents
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                Donation donation = documentSnapshot.toObject(Donation.class);

                                donations.add(donation); //add donation to vector
                            }
                            donationAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(root.getContext(), "Cannot load data from database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(root.getContext(), "Fail to load the data", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}