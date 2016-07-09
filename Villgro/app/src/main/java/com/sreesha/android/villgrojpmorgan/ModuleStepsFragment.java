package com.sreesha.android.villgro;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ModuleStepsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;


    public ModuleStepsFragment() {
        // Required empty public constructor
    }

    CoordinatorLayout mCoordinatorLayout;

    public static ModuleStepsFragment newInstance(int param1, String param2) {
        ModuleStepsFragment fragment = new ModuleStepsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mParam1 != -1) {
            View view = inflater.inflate(R.layout.fragment_module_steps, container, false);
            mCoordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coOrdLayout);
            mCoordinatorLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ModuleCourseActivity) getActivity()).onFragmentInteraction(mParam1);
                }
            });
            return view;
        } else {
            View view = inflater.inflate(R.layout.fragment_module_steps, container, false);
            return view;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface ModuleStepsFragmentInterface {
        void onFragmentInteraction(int num);
    }
}
