package com.exhibition.exhibition;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TabWidget;

import com.exhibition.exhibition.fragments.ArtFragment;
import com.exhibition.exhibition.fragments.ArtistFragment;
import com.exhibition.exhibition.fragments.GalleryFragment;
import com.exhibition.exhibition.models.ArtResult;
import com.exhibition.exhibition.models.ArtistResult;
import com.exhibition.exhibition.models.GalleryResult;
import com.exhibition.exhibition.models.SearchResult;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ArtistFragment artistFragment;
    ArtFragment artFragment;
    GalleryFragment galleryFragment;
    SearchPagerAdapter adapter;
    EditText searchQuery;
    private ViewPager viewPager;
    private List<SearchResult> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchQuery = (EditText) findViewById(R.id.editText);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Art"));
        tabLayout.addTab(tabLayout.newTab().setText("Artist"));
        tabLayout.addTab(tabLayout.newTab().setText("Gallery"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new SearchPagerAdapter(getSupportFragmentManager());
        artistFragment = new ArtistFragment();
        artFragment = new ArtFragment();
        galleryFragment = new GalleryFragment();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        (findViewById(R.id.searchBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSearch(searchQuery.getText().toString());
            }
        });
    }

    private void updateSearch(String query) {
        new Search().execute(query);
    }

    public class SearchPagerAdapter extends FragmentStatePagerAdapter {
        public SearchPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return artFragment;
                case 1:
                    return artistFragment;
                default:
                    return galleryFragment;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    private class Search extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                searchResults = ApiHelper.search("1", params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateFragments();
                }
            });
            return null;
        }
    }

    private void updateFragments() {
        if (searchResults != null) {
            artFragment.updateSearch(((ArtResult) searchResults.get(0)).getArtList());
            artistFragment.updateSearch(((ArtistResult) searchResults.get(1)).getArtistList());
            galleryFragment.updateSearch(((GalleryResult) searchResults.get(2)).getGalleryList());
        }
    }
}
