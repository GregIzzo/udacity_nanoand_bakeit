package com.example.android.udacity_nanoand_bakeit;

import static org.hamcrest.Matchers.anything;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


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
    private static String RECIPE_NAME = "Nutella Pie";
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    /**
     * Clicks on a GridView item and checks it opens up the StepDetailActivity with the correct details.
     */
    @Test
    public void clickRecipeListViewItem_OpensRecipeStepsActivity() {

        // get a reference to a recycleView item and clicks it.
        //onData(anything()).inAdapterView(withId(R.id.recipe_list)).atPosition(1).perform(click());
        onView(withId(R.id.recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Checks that the RecipeStepsActivity opens (layout:activity_recipesteps_list)
        // with the correct recipe name displayed (displayed in toolbar : R.id.toolbar)
        onView(withId(R.id.toolbar)).check(matches(withText(RECIPE_NAME)));


    }

}
