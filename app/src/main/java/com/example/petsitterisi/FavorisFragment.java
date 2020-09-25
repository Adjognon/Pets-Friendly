package com.example.petsitterisi;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.petsitterisi.services.ApiListFavorisFetcher;

public class FavorisFragment extends Fragment {

    Context ctx;
    LinearLayout favoris_container;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View fragementXmlView = inflater.inflate(R.layout.fragment_favoris, container, false);

        ctx =  fragementXmlView.getContext();
        favoris_container = fragementXmlView.findViewById(R.id.favoris_container);

        ApiListFavorisFetcher apiListFavorisFetcher = new ApiListFavorisFetcher(ctx, favoris_container);
        apiListFavorisFetcher.execute("");


      return fragementXmlView;
    }
}
