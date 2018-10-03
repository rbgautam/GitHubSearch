package com.forgeinnovations.android.githubelite.tabs;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.forgeinnovations.android.githubelite.R;
import com.forgeinnovations.android.githubelite.view.TabPageAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IntroFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IntroFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroFrag extends Fragment {
    static final String TAG = "PagerActivity";
    private static final String ARG_SECTION_NUMBER = "section_number";


    int lastLeftValue = 0;
    CoordinatorLayout mCoordinator;

    ImageView img;
    int[] bgs = new int[]{R.drawable.octocat_jetpack, R.drawable.intro_screen_1, R.drawable.intro_screen_2};
    private TabPageAdapter mAdapter;
    private OnFragmentInteractionListener mListener;
    private ViewPager mViewPager;
    private ImageView[] indicators;
    private TextView mSectionHeadingTextView;
    private TextView mSectionDescTextView;
    private String[] mPageHeadings;
    private String[] mPageDescs;

    public IntroFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sectionNumber Cuurent page.
     * @return A new instance of fragment IntroFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static IntroFrag newInstance(int sectionNumber) {
        IntroFrag fragment = new IntroFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Resources res = getResources();
        mPageDescs = res.getStringArray(R.array.intro_page_desc);
        mPageHeadings = res.getStringArray(R.array.intro_page_headings);

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mCoordinator = (CoordinatorLayout) getActivity().findViewById(R.id.tab_main_layout);

        showOnBoardingScreen();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro, container, false);

        mSectionHeadingTextView = (TextView) view.findViewById(R.id.section_label);
        mSectionHeadingTextView.setText(mPageHeadings[getArguments().getInt(ARG_SECTION_NUMBER) - 1]);

        mSectionDescTextView = (TextView) view.findViewById(R.id.intro_desc);
        mSectionDescTextView.setText(mPageDescs[getArguments().getInt(ARG_SECTION_NUMBER) - 1]);


        img = (ImageView) view.findViewById(R.id.section_img);
        img.setBackgroundResource(bgs[getArguments().getInt(ARG_SECTION_NUMBER) - 1]);
        return view;

    }

    private void showOnBoardingScreen() {
        Log.i(TAG, "onBoarding on");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.black_trans80));
        }

        //updateIndicators(page);





    }

    private void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                                               + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
