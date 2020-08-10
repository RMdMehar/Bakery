package com.example.bakery;

import android.app.Instrumentation;

import com.example.bakery.activity.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeCardClickTest {
    private RecipesRecyclerViewIdlingResource idlingResource;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIntentServiceIdlingResource() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        idlingResource = new RecipesRecyclerViewIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void clickRecipeCard_OpenDetailsActivity() {
        onData(anything())
                .inAdapterView(withId(R.id.main_recycler_view))
                .atPosition(1)
                .perform(click());

        onView(withId(R.id.ingredients_item)).check(matches(withText("INGREDIENTS")));
    }

    @After
    public void unregisterIntentServiceIdlingResource() {
        Espresso.unregisterIdlingResources(idlingResource);
    }
}

