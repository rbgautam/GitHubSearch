package com.forgeinnovations.android.githubelite.db;

import junit.framework.TestCase;

/**
 * Created by Rahul B Gautam on 5/25/18.
 */
public class DataManagerTest extends TestCase {

    private DataManager mSingletonInstance;

    public void testGetSingletonInstance() {
        mSingletonInstance = DataManager.getSingletonInstance();
        assertNotNull(mSingletonInstance);

    }


    public void testLoadFromDatabase() {

        if(mSingletonInstance != null ){


        }
    }

    public void testGetBookmarks() {
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testSaveBookmark() {
    }

    public void testGetBookmarks1() {
    }

    public void testDeleteBookmark() {
    }
}