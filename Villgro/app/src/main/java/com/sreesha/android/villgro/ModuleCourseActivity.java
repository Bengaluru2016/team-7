package com.sreesha.android.villgro;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import me.drozdzynski.library.steppers.OnCancelAction;
import me.drozdzynski.library.steppers.OnFinishAction;
import me.drozdzynski.library.steppers.SteppersItem;
import me.drozdzynski.library.steppers.SteppersView;

public class ModuleCourseActivity extends AppCompatActivity implements ModuleStepsFragment.ModuleStepsFragmentInterface {
    SteppersView steppersView;
    SteppersView.Config steppersViewConfig;
    ArrayList<SteppersItem> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        steppersViewConfig = new SteppersView.Config();
        steppersViewConfig.setOnFinishAction(new OnFinishAction() {
            @Override
            public void onFinish() {
                setupQuizStep();
            }
        });
        steppersViewConfig.setOnCancelAction(new OnCancelAction() {
            @Override
            public void onCancel() {
                // Action when click cancel on one of steps
            }
        });
        steppersViewConfig.setFragmentManager(getSupportFragmentManager());

        steps = new ArrayList<>();

        int i = 0;
        while (i <= 2) {

            final SteppersItem item = new SteppersItem();
            item.setLabel("Step nr " + i);
            item.setPositiveButtonEnable(true);

            if (i % 2 == 0) {
                ModuleStepsFragment blankFragment = ModuleStepsFragment.newInstance(i, null);

                item.setSubLabel("Fragment: " + blankFragment.getClass().getSimpleName());
                item.setFragment(blankFragment);
            } else {
                ModuleStepsFragment blankSecondFragment = new ModuleStepsFragment();
                item.setSubLabel("Fragment: " + blankSecondFragment.getClass().getSimpleName());
                item.setFragment(blankSecondFragment);
            }

            steps.add(item);
            i++;
        }
        setupSteppersView();
    }

    void setupSteppersView() {
        steppersView = (SteppersView) findViewById(R.id.steppersView);
        steppersView.setConfig(steppersViewConfig);
        steppersView.setItems(steps);
        steppersView.build();
    }

    void setupQuizStep() {
        ModuleStepsFragment blankFragment = ModuleStepsFragment.newInstance(-1,"q");

        SteppersItem stepFirst = new SteppersItem();

        stepFirst.setLabel("Quiz");
        stepFirst.setSubLabel("Ace it !");
        stepFirst.setFragment(blankFragment);
        stepFirst.setPositiveButtonEnable(true);

        steps.add(stepFirst);
        /*
        steppersView.setConfig(steppersViewConfig);
        steppersView.setItems(steps);
        steppersView.build();*/
    }

    @Override
    public void onFragmentInteraction(int num) {
        if (steps != null) {
            steps.get(num).setPositiveButtonEnable(true);
        }
    }
}