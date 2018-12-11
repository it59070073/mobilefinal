package a59070073.kmitl.ac.th.mobilefinal;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ArrayList<String> menu = new ArrayList<>();

    public HomeFragment(){
        menu.add("Setup Profile");
        menu.add("My Friends");
        menu.add("Sign Out");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        ArrayAdapter<String> menuAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menu
        );

        ListView homeList = getView().findViewById(R.id.home_list);
        homeList.setAdapter(menuAdapter);
        homeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("HOME", "Click on menu = " + menu.get(i));

                if (menu.get(i).equals("Setup Profile")){
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new FriendFragment())
                            .addToBackStack(null)
                            .commit();
                }else if (menu.get(i).equals("My Friends")){
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new ProfileFragment())
                            .addToBackStack(null)
                            .commit();

                }else if (menu.get(i).equals("Sign Out")){
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new LoginFragment())
                            .commit();

                }

            }
        });

    }



}
