package local.themepark;

import android.content.SharedPreferences;
import androidx.test.rule.ActivityTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.assertEquals;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    //Tests FragmentLoader method storing a new user.
    //fragmentLoader test takes no input from the text box or button because trying to add text/press the button with code in the JUnit test caused an error.
    @Test
    public void testView() {
        double rndUser = Math.random() * 999999;
        rndUser = Math.round(rndUser);
        String inputName = "TestUser#"+rndUser;

        mActivity.fragmentLoaderBeefyTest(inputName);

        SharedPreferences prefs1 = mActivity.getSharedPreferences("default", MODE_PRIVATE);

        String username = prefs1.getString("currentName", "");

        assertEquals(inputName, username);
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}
