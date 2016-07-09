package com.sreesha.android.villgro;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.drozdzynski.library.steppers.OnCancelAction;
import me.drozdzynski.library.steppers.OnFinishAction;
import me.drozdzynski.library.steppers.SteppersItem;
import me.drozdzynski.library.steppers.SteppersView;


public class ModuleStepsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    SteppersView steppersView;
    SteppersView.Config steppersViewConfig;
    ArrayList<SteppersItem> steps;

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
        }
        return null;
    }

    void initializeSteppers(View view) {
        steppersViewConfig = new SteppersView.Config();
        steppersViewConfig.setOnFinishAction(new OnFinishAction() {
            @Override
            public void onFinish() {
                if (mParam2 == "qq") {

                }
            }
        });
        steppersViewConfig.setOnCancelAction(new OnCancelAction() {
            @Override
            public void onCancel() {
                // Action when click cancel on one of steps
            }
        });
        steppersViewConfig.setFragmentManager(getChildFragmentManager());

        steps = new ArrayList<>();

        int i = 0;
        while (i <= 10) {

            final SteppersItem item = new SteppersItem();
            item.setLabel("Quiz Question" + i);
            item.setPositiveButtonEnable(true);

            if (i % 2 == 0) {
                ModuleStepsFragment blankFragment = ModuleStepsFragment.newInstance(-1, "qq");

                item.setSubLabel("Multiple Answers");
                item.setFragment(blankFragment);
            } else {
                ModuleStepsFragment blankSecondFragment = ModuleStepsFragment.newInstance(-1, "qq");
                item.setSubLabel("Single Answer");
                item.setFragment(blankSecondFragment);
            }

            steps.add(item);
            i++;
        }
        setupSteppersView(view);
    }

    void setupSteppersView(View view) {
        steppersView = (SteppersView) view.findViewById(R.id.quizSteppersLayout);
        steppersView.setConfig(steppersViewConfig);
        steppersView.setItems(steps);
        steppersView.build();
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
