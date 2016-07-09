package com.sreesha.android.villgro;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import me.drozdzynski.library.steppers.OnCancelAction;
import me.drozdzynski.library.steppers.OnFinishAction;
import me.drozdzynski.library.steppers.SteppersItem;
import me.drozdzynski.library.steppers.SteppersView;

public class ModuleCourseActivity extends AppCompatActivity implements ModuleStepsFragment.ModuleStepsFragmentInterface {
    SteppersView steppersView;
    SteppersView.Config steppersViewConfig;
    ArrayList<SteppersItem> steps;

    SteppersView quizSteppersView;
    SteppersView.Config quizSteppersViewConfig;
    ArrayList<SteppersItem> quizSteps;
    public static final int STEP_NUM = 2;

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
        while (i <= STEP_NUM) {

            final SteppersItem item = new SteppersItem();

            if (i == STEP_NUM ) {
                item.setLabel("Quiz !!!!");
                item.setPositiveButtonEnable(true);
                ModuleStepsFragment blankFragment = ModuleStepsFragment.newInstance(-1, "q");

                item.setSubLabel(" Ace It !! ");
                item.setFragment(blankFragment);
            }else{
                item.setLabel("Step nr " + i);
                item.setPositiveButtonEnable(true);
                ModuleStepsFragment blankFragment = ModuleStepsFragment.newInstance(i, null);

                item.setSubLabel("Fragment: " + blankFragment.getClass().getSimpleName());
                item.setFragment(blankFragment);
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

    @Override
    public void onFragmentInteraction(int num) {
        if (steps != null) {
            steps.get(num).setPositiveButtonEnable(true);
        }
    }
}
