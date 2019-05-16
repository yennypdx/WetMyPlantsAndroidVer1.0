package com.android.wetmyplants;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {
    @Test
    public void wmpPackageTest() {
        Context wmpPackage = InstrumentationRegistry.getTargetContext();

        assertEquals("com.android.wetmyplants", wmpPackage.getPackageName());
    }
}
