package joao.vitor.oliveira.galeriapublica;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListViewFragment extends Fragment {

    private MainViewModel mViewModel;
    private View view;

    public ListViewFragment() {
        // Required empty public constructor
    }

    public static ListViewFragment newInstance() {
        return new ListViewFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_view, container, false);
        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        ListAdapter listAdapter = new ListAdapter(new ImageDataComparator());
        LiveData<PagingData<ImageData>> liveData = mViewModel.getPageLv();
        liveData.observe(getViewLifecycleOwner(),new Observer<PagingData<ImageData>>() {
            @Override
            public void onChanged(PagingData<ImageData> objectPagingData) {
                listAdapter.submitData(getViewLifecycleOwner().getLifecycle(), objectPagingData);
            }
        });
        RecyclerView tvGallery = (RecyclerView) view.findViewById(R.id.rvList);
        tvGallery.setAdapter(listAdapter);
        tvGallery.setLayoutManager(new LinearLayoutManager(getContext()));
    }




}