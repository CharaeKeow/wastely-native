package my.edu.utem.ftmk.bitp3453.achifapp.ui.donations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DonationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DonationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}