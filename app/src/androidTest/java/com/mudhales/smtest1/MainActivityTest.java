package com.mudhales.smtest1;

import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.*;
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private MainActivity mainActivity = null;
    @Before
    public void setUp() throws Exception {
        mainActivity = mActivityTestRule.getActivity();
    }
    @Test
    public void testLaunch() {
        View  view = mainActivity.findViewById(R.id.screenContainer);
        assertNotNull(view);
    }
    @Test
    public void testTitle() {
        assertNotNull(mainActivity.getSupportActionBar().getTitle());
        assertTrue(mainActivity.getSupportActionBar().isShowing());
    }
    @Test
    public void testCheckFragment() {
        Fragment fragment = mainActivity.getSupportFragmentManager().findFragmentById(R.id.screenContainer);
        assertNotNull(fragment);
    }
    @Test
    public void testCheckRecyclerView() {
        Fragment fragment = mainActivity.getSupportFragmentManager().findFragmentById(R.id.screenContainer);
        RecyclerView rvItems = fragment.getView().findViewById(R.id.rvItems);
        assertNotNull(rvItems);
    }
    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
}