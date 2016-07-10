package com.sreesha.android.villgro;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sreesha.android.villgro.Animation.AnimationUtils;
import com.sreesha.android.villgro.Networking.APIUrls;
import com.sreesha.android.villgro.Networking.DownloadData;

import java.util.ArrayList;

public class ModulesDashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    RecyclerView outerRecyclerView;
    OuterRecyclerViewCA outerRVAdapter;
    private int previousPosition = 0;
    private String mAnimationType = AnimationUtils.REVERSE_SCATTER;
    private final String ANIMATION_TYPE_STRING_KEY = "animationTypeStringKey";
    public static final String[] mCourseTitles = {"Health Care", "Education", "Agri-Business", "Energy"};
    public static final int[] mCourseBackDrop =
            {R.drawable.healthcare_backdrop
                    , R.drawable.education_backdrop
                    , R.drawable.agri_business
                    , R.drawable.energy_conservation_backdrop};
    ArrayList<ModuleMetaData> mModuleMetaDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules_dash_board);

        if (savedInstanceState != null) {
            mAnimationType = savedInstanceState.getString(ANIMATION_TYPE_STRING_KEY, AnimationUtils.REVERSE_SCATTER);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        outerRVAdapter = new OuterRecyclerViewCA(mModuleMetaDataArrayList);
        outerRecyclerView = (RecyclerView) findViewById(R.id.outerRecyclerView);
        outerRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
        outerRecyclerView.setAdapter(outerRVAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        outState.putString(ANIMATION_TYPE_STRING_KEY, mAnimationType);
        outPersistentState.putString(ANIMATION_TYPE_STRING_KEY, mAnimationType);

        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_gallery) {
            Intent in=new Intent(ModulesDashBoard.this,LeaderBoard.class);
            startActivity(in);

        } else if (id == R.id.nav_slideshow) {
            Intent in=new Intent(ModulesDashBoard.this,Forum.class);
            startActivity(in);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public class OuterRecyclerViewCA extends RecyclerView.Adapter<OuterRecyclerViewCA.ViewHolder> {
        InnerRecyclerViewCA innerRCAdapter;
        ArrayList<ModuleMetaData> mModuleMetaDataArrayList = new ArrayList<>();

        OuterRecyclerViewCA(ArrayList<ModuleMetaData> mModuleMetaDataArrayList) {
            this.mModuleMetaDataArrayList = mModuleMetaDataArrayList;
            innerRCAdapter = new InnerRecyclerViewCA();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.outer_recycler_view_row_element, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.titleTextView.setText(mCourseTitles[position]);
            holder.mBackDropImageView
                    .setImageDrawable(
                            holder.itemView
                                    .getContext()
                                    .getDrawable(mCourseBackDrop[position]));
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            switch (mAnimationType) {
                case AnimationUtils.REVERSE_SCATTER:

                    if (holder.getAdapterPosition() > previousPosition) {
                        AnimationUtils.animateAlphaFadeAndScatter(holder, true);
                    } else {
                        AnimationUtils.animateAlphaFadeAndScatter(holder, false);
                    }
                    previousPosition = holder.getAdapterPosition();
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return mCourseTitles.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            RecyclerView innerRecyclerView;
            CardView posterCard;
            TextView titleTextView;
            ImageView mBackDropImageView;

            public ViewHolder(View itemView) {
                super(itemView);
                innerRecyclerView = (RecyclerView) itemView.findViewById(R.id.nestedRecyclerView);
                innerRecyclerView.setLayoutManager(
                        new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false)
                );
                innerRecyclerView.setAdapter(innerRCAdapter);
                posterCard = (CardView) itemView.findViewById(R.id.posterCard);
                titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
                mBackDropImageView = (ImageView) itemView.findViewById(R.id.backDropImageView);
            }

            public RecyclerView getInnerRecyclerView() {
                return innerRecyclerView;
            }
        }

        public class InnerRecyclerViewCA extends RecyclerView.Adapter<InnerRecyclerViewCA.ViewHolder> {
            private int prevPos = 0;

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.inner_recycler_view_row_element, parent, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                if (position > prevPos) {
                    AnimationUtils.animateAlphaFadeAndScatter(holder, true);
                } else {
                    AnimationUtils.animateAlphaFadeAndScatter(holder, false);
                }
                holder.moduleElement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.getContext().startActivity(new Intent(ModulesDashBoard.this, ModuleCourseActivity.class));
                    }
                });
                prevPos = position;
            }

            @Override
            public int getItemCount() {
                return 100;
            }

            class ViewHolder extends RecyclerView.ViewHolder {
                CardView moduleElement;

                public ViewHolder(View itemView) {
                    super(itemView);
                    moduleElement = (CardView) itemView.findViewById(R.id.moduleElement);
                }
            }
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }
}
