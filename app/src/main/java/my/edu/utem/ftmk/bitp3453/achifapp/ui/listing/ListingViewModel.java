package my.edu.utem.ftmk.bitp3453.achifapp.ui.listing;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}