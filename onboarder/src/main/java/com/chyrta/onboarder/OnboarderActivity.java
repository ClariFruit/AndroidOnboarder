package com.chyrta.onboarder;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.chyrta.onboarder.views.CircleIndicatorView;

import java.util.List;

public abstract class OnboarderActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private CircleIndicatorView circleIndicatorView;
    private ViewPager vpOnboarderPager;
    private OnboarderAdapter onboarderAdapter;
    private TextView btnSkip;

    private int btnSkipTextId;
    private int btnFinishTextId;
    private View layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarder);
        setStatusBackgroundColor();
        hideActionBar();
        circleIndicatorView = (CircleIndicatorView) findViewById(R.id.circle_indicator_view);
        btnSkip = (TextView) findViewById(R.id.btn_skip);
        vpOnboarderPager = (ViewPager) findViewById(R.id.vp_onboarder_pager);
        vpOnboarderPager.addOnPageChangeListener(this);
        btnSkip.setOnClickListener(this);
        layout = findViewById(R.id.cl_onboarder);
    }

    public void setOnboardPagesReady(List<OnboarderPage> pages) {
        onboarderAdapter = new OnboarderAdapter(pages, getSupportFragmentManager());
        vpOnboarderPager.setAdapter(onboarderAdapter);
        circleIndicatorView.setPageIndicators(pages.size());
    }

    public void setInactiveIndicatorColor(int color) {
        this.circleIndicatorView.setInactiveIndicatorColor(color);
    }

    public void setActiveIndicatorColor(int color) {
        this.circleIndicatorView.setActiveIndicatorColor(color);
    }




    public void setSkipButtonTitle(@StringRes int titleResId) {
        btnSkipTextId = titleResId;
        btnSkip.setText(titleResId);

    }


    public void setFinishButtonTitle(@StringRes int titleResId) {
        btnFinishTextId = titleResId;
    }

    public void setButtonTextColor(int id){
        btnSkip.setTextColor(getResources().getColor(id));
    }

    public void setBackgroundColor(int id){
        layout.setBackgroundColor(getResources().getColor(id));
    }

    public void setButtonBottomDrawable(int id){
        btnSkip.setBackgroundResource(id);
    }



    public void setStatusBackgroundColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black_transparent));
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        boolean isInLastPage = vpOnboarderPager.getCurrentItem() == onboarderAdapter.getCount() - 1;
        if (i == R.id.btn_skip) {
            if(!isInLastPage){
                onSkipButtonPressed();
            }else{
                onFinishButtonPressed();
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        int lastPagePosition = onboarderAdapter.getCount() - 1;
        circleIndicatorView.setCurrentPage(position);
        btnSkip.setText( position !=lastPagePosition ?btnSkipTextId:btnFinishTextId);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    protected void onSkipButtonPressed() {
    }

    abstract public void onFinishButtonPressed();

}
