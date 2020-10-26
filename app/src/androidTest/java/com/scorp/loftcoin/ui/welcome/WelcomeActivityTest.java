package com.scorp.loftcoin.ui.welcome;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.scorp.loftcoin.R;
import com.scorp.loftcoin.ui.main.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class WelcomeActivityTest {

    @Test
    public void open_main_if_button_being_pressed() {
        ActivityScenario.launch(WelcomeActivity.class);
        Intents.init();
        onView(withId(R.id.btn_start)).perform(click());
        Intents.intended(IntentMatchers.hasComponent(MainActivity.class.getName()));
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }
}