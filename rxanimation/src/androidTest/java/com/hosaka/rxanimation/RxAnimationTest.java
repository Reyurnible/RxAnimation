package com.hosaka.rxanimation;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.hosaka.rxanimation.animation.AnimationEvent;
import com.hosaka.rxanimation.animation.RxAnimation;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public final class RxAnimationTest {
    @Rule
    public final ActivityTestRule<TestActivity> activityRule =
            new ActivityTestRule<>(TestActivity.class);

    private final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
    private View child;

    @Before
    public void setUp() {
        TestActivity activity = activityRule.getActivity();
        child = activity.child;
    }

    @Test
    public void events() throws InterruptedException {
        RecordingObserver<Object> o = new RecordingObserver<>();

        final AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setStartOffset(0);
        alphaAnimation.setDuration(100);

        final Subscription subscription = RxAnimation.events(alphaAnimation, child).subscribeOn(AndroidSchedulers.mainThread()).subscribe(o);
        {   // ロードが完了するまで待つ
            waitForLoadingFinished(500);
            // Stack Start event
            assertThat(((AnimationEvent) o.takeNext()).kind() == AnimationEvent.Kind.START);
            // Stack End event
            assertThat(((AnimationEvent) o.takeNext()).kind() == AnimationEvent.Kind.END);
        }
        // Checking complete
        {
            waitForLoadingFinished(4000);
            o.assertOnCompleted();
        }
        subscription.unsubscribe();
        o.assertNoMoreEvents();
    }

    public static void waitForLoadingFinished(int timeout) {
        long t = SystemClock.elapsedRealtime();
        do {
            SystemClock.sleep(100);
        } while (SystemClock.elapsedRealtime() - t < timeout);
    }


}
