package com.example.android.udacity_nanoand_bakeit;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;
import static org.hamcrest.Matchers.anything;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class MainActivityRecipeClickTest {
    private static String RECIPE_INTRODUCTION = "Recipe Introduction";
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    /**
     * Clicks on a GridView item and checks that it opens up the StepDetailActivity with the correct details.
     */
    @Test
    public void clickRecipeListViewItem_OpensRecipeDetailActivity() {

        // get a reference to a recycleView item and clicks it.
        //onData(anything()).inAdapterView(withId(R.id.recipe_list)).atPosition(1).perform(click());
        //
        // CLICK ON FIRST RECIPE
        onView(withId(R.id.recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // CLICK ON FIRST ITEM - SHOULD BE 'Recipe Introduction'
        onView(withId(R.id.steps_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));


        // CHECK THAT RECIPE INSTRUCTIONS CONTAINS THE TEXT "Recipe Introduction"

        onView(withId(R.id.tv_stepinstructions)).check(matches(withText(RECIPE_INTRODUCTION)));


    }



}
