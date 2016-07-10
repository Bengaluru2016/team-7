package com.sreesha.android.villgro;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sreesha.android.villgro.Networking.APIUrls;
import com.sreesha.android.villgro.Networking.AsyncResult;
import com.sreesha.android.villgro.Networking.DownloadData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import me.drozdzynski.library.steppers.OnCancelAction;
import me.drozdzynski.library.steppers.OnFinishAction;
import me.drozdzynski.library.steppers.SteppersItem;
import me.drozdzynski.library.steppers.SteppersView;


public class ModuleStepsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    // TODO: Rename and change types of parameters

    private int mParam1;
    private String mParam2;
    private String mParam3;

    SteppersView steppersView;
    SteppersView.Config steppersViewConfig;
    static ArrayList<SteppersItem> steps;
    TextView mContentTextView;
    TextToSpeech textToSpeech;
    RadioGroup quizOptionsRadioGroup;
    JSONArray quizJSONArray;
    JSONObject quizData;

    RadioButton radB1;
    RadioButton radB2;
    RadioButton radB3;
    RadioButton radB4;

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

    public static ModuleStepsFragment newInstance(int param1, String param2, String param3) {
        ModuleStepsFragment fragment = new ModuleStepsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            if (getArguments().getString(ARG_PARAM3) != null) {
                mParam3 = getArguments().getString(ARG_PARAM3);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mParam1 != -1 && mParam3 == null) {
            View view = inflater.inflate(R.layout.fragment_module_steps, container, false);
            steppersView = (SteppersView) view.findViewById(R.id.quizSteppersLayout);
            mCoordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coOrdLayout);
            mCoordinatorLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ModuleCourseActivity) getActivity()).onFragmentInteraction(mParam1);
                }
            });
            mContentTextView = (TextView) view.findViewById(R.id.contentText);
            mContentTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //textToSpeech.speak(getString(R.string.small_text), TextToSpeech.QUEUE_FLUSH, null, null);
                    ((ModuleCourseActivity) getActivity()).callTextToSpeech(null);
                }
            });
            textToSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {

                        int result = textToSpeech.setLanguage(Locale.US);

                        if (result == TextToSpeech.LANG_MISSING_DATA
                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "This Language is not supported");
                        } else {
                            //textToSpeech.speak(getString(R.string.small_text), TextToSpeech.QUEUE_FLUSH, null, null);
                        }

                    } else {
                        Log.e("TTS", "Initilization Failed!");
                    }
                }
            });
            return view;
        } else if (mParam2.equals("q")) {
            View view = inflater.inflate(R.layout.quiz_layout, container, false);
            steppersView = (SteppersView) view.findViewById(R.id.quizSteppersLayout);
            new DownloadData(
                    getActivity()
                    , APIUrls.getQuizDataURL("2dcc81571ab8ef191f3d1156")
                    , new AsyncResult() {
                @Override
                public void onResultJSON(JSONObject object) throws JSONException {

                }

                @Override
                public void onResultString(String stringObject, String errorString, String parseStatus) {

                }

                @Override
                public void onResultQuizData(JSONArray object) {
                    Log.d("Quiz", object.toString());
                    quizJSONArray = object;
                    initializeSteppers(null);
                }

                @Override
                public void onResultSignInData(JSONObject object) {

                }

                @Override
                public void onResultSignUpData(JSONObject object) {

                }
            }
            ).execute("getQuize");


            return view;
        } else if (mParam2.equals("qq")) {
            View view = inflater.inflate(R.layout.quiz_question_single_item, container, false);
            quizOptionsRadioGroup = (RadioGroup) view.findViewById(R.id.quizOptionsRadioButton);
            try {
                quizData = new JSONObject(mParam3);
                radB1 = (RadioButton) view.findViewById(R.id.op1);
                radB2 = (RadioButton) view.findViewById(R.id.op2);
                radB3 = (RadioButton) view.findViewById(R.id.op3);
                radB4 = (RadioButton) view.findViewById(R.id.op4);

                radB1.setHint(quizData.getJSONArray("answers").getJSONObject(0).getString("1"));
                radB2.setHint(quizData.getJSONArray("answers").getJSONObject(0).getString("2"));
                radB3.setHint(quizData.getJSONArray("answers").getJSONObject(0).getString("3"));
                radB4.setHint(quizData.getJSONArray("answers").getJSONObject(0).getString("4"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            quizOptionsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    boolean right = false;
                    switch (checkedId) {
                        case R.id.op1:
                            try {
                                if (quizData.getString("correct_ans").equals(String.valueOf(1))) {
                                    right = true;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            break;
                        case R.id.op2:
                            try {
                                if (quizData.getString("correct_ans").equals(String.valueOf(1))) {
                                    right = true;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            break;
                        case R.id.op3:
                            try {
                                if (quizData.getString("correct_ans").equals(String.valueOf(1))) {
                                    right = true;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            break;
                        case R.id.op4:
                            try {
                                if (quizData.getString("correct_ans").equals(String.valueOf(1))) {
                                    right = true;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    String id=PreferenceManager.getDefaultSharedPreferences(getActivity())
                            .getString(LoginActivity.USER_ID_KEY,"default");
                    new DownloadData(getActivity()
                            , APIUrls.getaddQuizDataURL("2dcc81571ab8ef191f3d1156", id, String.valueOf(right))
                            , new AsyncResult() {
                        @Override
                        public void onResultJSON(JSONObject object) throws JSONException {

                        }

                        @Override
                        public void onResultString(String stringObject, String errorString, String parseStatus) {

                        }

                        @Override
                        public void onResultQuizData(JSONArray object) {

                        }

                        @Override
                        public void onResultSignInData(JSONObject object) {

                        }

                        @Override
                        public void onResultSignUpData(JSONObject object) {

                        }
                    })
                            .execute("addResults");
                    steps.get(mParam1).setPositiveButtonEnable(true);
                }
            });
            return view;
        }
        return null;
    }

    void downloadWAVFile(String string) {

    }

    void initializeSteppers(View view) {
        steppersViewConfig = new SteppersView.Config();
        steppersViewConfig.setOnFinishAction(new OnFinishAction() {
            @Override
            public void onFinish() {
                Toast.makeText(getActivity(), "Hurray You've Finished The quiz\n Going Back in a few seconds !!! :) ", Toast.LENGTH_LONG).show();
               /*Send an email to the user to notify*/
                new DownloadData(getActivity(),APIUrls.getQuizDataURL("sbhdbsd"),null).equals("emailPHPPost");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().finish();
                    }
                }, 1000);
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
        while (i < quizJSONArray.length()) {
            try {


                final SteppersItem item = new SteppersItem();
                item.setLabel(quizJSONArray.getJSONObject(i).getString("question"));
                item.setPositiveButtonEnable(true);

                ModuleStepsFragment blankFragment = ModuleStepsFragment.newInstance(i, "qq"
                        , quizJSONArray.getJSONObject(i).toString());

                item.setSubLabel("Multiple Answers");
                item.setFragment(blankFragment);
                item.setPositiveButtonEnable(false);
                steps.add(item);
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setupSteppersView(view);
    }

    void setupSteppersView(View view) {
        steppersView.setConfig(steppersViewConfig);
        steppersView.setItems(steps);
        steppersView.build();
    }

    @Override
    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
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
