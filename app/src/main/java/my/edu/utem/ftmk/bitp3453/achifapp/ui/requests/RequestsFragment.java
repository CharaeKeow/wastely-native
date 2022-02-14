package my.edu.utem.ftmk.bitp3453.achifapp.ui.requests;

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
import my.edu.utem.ftmk.bitp3453.achifapp.Request;
import my.edu.utem.ftmk.bitp3453.achifapp.RequestAdapter;
import my.edu.utem.ftmk.bitp3453.achifapp.databinding.FragmentRequestsBinding;

public class RequestsFragment extends Fragment {

    private RequestsViewModel requestsViewModel;
    private FragmentRequestsBinding binding;
    private FirebaseFirestore db;
    private Vector<Request> requests;
    private RecyclerView rcvRequests;
    private RequestAdapter requestAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        requestsViewModel =
                new ViewModelProvider(this).get(RequestsViewModel.class);

        binding = FragmentRequestsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rcvRequests = root.findViewById(R.id.rvc_requests);
        db = FirebaseFirestore.getInstance();

        requests = new Vector<>();
        rcvRequests.setLayoutManager(new LinearLayoutManager(MainActivity.context));

        requestAdapter = new RequestAdapter(root.getContext(), requests);
        rcvRequests.setAdapter(requestAdapter);

        db.collection("request")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                            //loop through the documents
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                Request request = documentSnapshot.toObject(Request.class);

                                requests.add(request); //add donation to vector
                            }
                            requestAdapter.notifyDataSetChanged();
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