package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import developer.mstudio.foodchk.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Tres#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tres extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    //private OnFragmentInteractionListener mListener;

    public Tres() {
        // Required empty public constructor
    }

    public static Tres newInstance(String str) {
        Tres fragment = new Tres();


        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//       // getActivity().getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //
        final Context contexThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.SettingsTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contexThemeWrapper);
        View v = localInflater.inflate(R.layout.settings, container, false);
        //setHasOptionsMenu(true);
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        return v;
    }

    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {


        super.onCreateOptionsMenu(menu,inflater);
        getActivity().getMenuInflater().inflate(R.menu.setting, menu);

    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
