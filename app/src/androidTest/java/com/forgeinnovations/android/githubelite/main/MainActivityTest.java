package com.forgeinnovations.android.githubelite.main;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.forgeinnovations.android.githubelite.R;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Rahul B Gautam on 5/25/18.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends TestCase {

    @Rule
    public ActivityTestRule<MainActivity> mainTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

//    public void setUp() throws Exception {
//        super.setUp();
//    }
//
//    public void tearDown() throws Exception {
//    }
    @Test
    public void testTypeNewSearch(){
       onView(withId(R.id.action_search_widget)).perform(typeText("android"));
    }
}