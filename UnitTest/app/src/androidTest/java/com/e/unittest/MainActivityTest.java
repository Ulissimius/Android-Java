package com.e.unittest;

import android.view.View;
import android.widget.TextView;
import androidx.test.rule.ActivityTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testView() {
        View v = mActivity.findViewById(R.id.tvMainText);
        assertNotNull(v);
    }
    @Test
    public void testText() {
        TextView tv = mActivity.findViewById(R.id.textView2);
        String tvValue = tv.getText().toString();
        assertEquals("Hello World!", tvValue);
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}