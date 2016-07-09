package com.sreesha.android.villgro;

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

public class ModuleCourseActivity extends AppCompatActivity {
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
                // Action on last step Finish button
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

        SteppersItem stepFirst = new SteppersItem();

        stepFirst.setLabel("Step - 1");
        stepFirst.setSubLabel("Subtitle of step");
        stepFirst.setFragment(
                new ModuleStepsFragment()

        );
        stepFirst.setPositiveButtonEnable(false);

        steps.add(stepFirst);

        SteppersItem stepSecond = new SteppersItem();

        stepFirst.setLabel("Step - 2");
        stepFirst.setSubLabel("Subtitle of step");
        stepFirst.setFragment(
                new ModuleStepsFragment()

        );
        stepFirst.setPositiveButtonEnable(false);
        steps.add(stepSecond);

        SteppersItem stepThird = new SteppersItem();

        stepFirst.setLabel("Step - 3");
        stepFirst.setSubLabel("Subtitle of step");
        stepFirst.setFragment(
                new ModuleStepsFragment()

        );
        stepFirst.setPositiveButtonEnable(false);
        steps.add(stepThird);

        setupSteppersView();
    }

    void setupSteppersView() {
        steppersView = (SteppersView) findViewById(R.id.steppersView);
        steppersView.setConfig(steppersViewConfig);
        steppersView.setItems(steps);
        steppersView.build();
    }
}
