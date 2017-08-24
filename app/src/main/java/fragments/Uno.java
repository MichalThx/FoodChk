package fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.CustomAdapterBarcode;
import barcode.BarcodeCaptureActivity;
import developer.mstudio.foodchk.MainActivity;
import developer.mstudio.foodchk.R;
import helper.daneDBHelper;
import query.Searching;
import szablon.Kod;
import szablon.Sklad;

public class Uno extends Fragment{
    private CustomAdapterBarcode customAdapter;
    ListView listView;
    Cursor cursor;
    ImageButton request_butt;
    ImageButton barcode_butt;
    Searching studentRepo ;
    String doSklad;
    // Fragment fragment;
    private List<Sklad> sklads;
    private List<Kod> kods;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.barcode, container, false);
//        setHasOptionsMenu(true);
        // sklads = getSklads();
        kods = getKods();
        try {
            studentRepo = new Searching(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Toolbar myToolbar = (Toolbar) v.findViewById(R.id.my_toolbar);
        ((MainActivity) getActivity()).setActionBarTitle("");
        cursor=studentRepo.getKodList();

        customAdapter = new CustomAdapterBarcode(getActivity(), cursor, 0);
        listView = (ListView) v.findViewById(R.id.listView);
        listView.setAdapter(customAdapter);
        //final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Fragment fragment = new Duo();
//                //Duo deatil = (Duo)getFragmentManager().findFragmentById(R.id.bar)
//                FragmentManager fm = getFragmentManager();
//               // FragmentBoxOffice f = (FragmentBoxOffice) fm.findFragmentByTag(FragmentBoxOffice.TAG);
//                fm.beginTransaction().replace(R.id.fragment_place,fragment).commit();

                doSklad = String.valueOf(kods.get(i).getKodSKL());
                Log.v("list", String.valueOf(kods.get(i).getKodSKL()));
                // searchView.setQuery(String.valueOf(kods.get(i).getKodSKL()), true);
                //Fragment fragment = new Fragment();

                MainActivity.wuszukiwanie=String.valueOf(kods.get(i).getKodSKL());
                MainActivity.wyszuk++;
                ((MainActivity)getActivity()).setCurrentItem(1, true);


                Log.v("list------>", String.valueOf(kods.get(i).getKodSKL()));


            }
        });
        request_butt = (ImageButton) v.findViewById(R.id.button_result);
        request_butt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.v("button","----");
                        request_butt.setVisibility(View.GONE);

                    }
                });

        barcode_butt = (ImageButton) v.findViewById(R.id.button_barcode);
        barcode_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Log.v("button","clicked");
                Intent intent = new Intent(getActivity(), BarcodeCaptureActivity.class);
                //  Log.v("===", "1");
                intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
                //Log.v("===", "2");
                startActivityForResult(intent, 9003);
                //Log.v("===", "3");

            }
        });
//        sklads = getSklads();
//        kods = getKods();
        //     setHasOptionsMenu(true);
        //  TextView tv = (TextView) v.findViewById(R.id.textView2);t

        setHasOptionsMenu(true);
        //  ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        return v;
    }
    public static Uno newInstance(String text) {

        Uno f = new Uno();
        Bundle b = new Bundle();
        // b.putString("msg", text);-

        f.setArguments(b);

        return f;
    }




    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult( requestCode, resultCode,  data);
        // Log.v("----","1");
        if (resultCode == CommonStatusCodes.SUCCESS) {
            //  Log.v("----","2");
            if (data != null) {
                //  Log.v("----","3");
                Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                //  statusMessage.setText(R.string.barcode_success);
                //  Value.setText(barcode.displayValue);
                //onQueryTextSubmit(barcode.displayValue);
                // Log.v("----","4");

                cursor=studentRepo.getKodListByKeyword(barcode.displayValue);
                if (cursor==null){
                    request_butt.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(),"No records found!",Toast.LENGTH_LONG).show();
                }else{
                    customAdapter = new CustomAdapterBarcode(getActivity(), cursor, 0);
                    listView.setAdapter(customAdapter);
                }



                //cursor.
                // customAdapter.swapCursor(cursor);
                //  Log.v("----","5");
                Log.d("-------------------", "Barcode read: " + barcode.displayValue);
                // Log.v("----","6");
            } else {
                //Log.v("----","7");
                //    statusMessage.setText(R.string.barcode_failure);
                Log.d("-", "No barcode captured, intent data is null");
            }
        } else {
            Log.v("----","8");
            //  statusMessage.setText(R.string.barcode_error);

        }
        //  Log.v("----","9");


    }


    private List<Kod> getKods()  {
        List<Kod> kods = new ArrayList<>();

        daneDBHelper db = new daneDBHelper(getActivity().getApplicationContext());
        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        if (db.open()) {
            kods = db.getKods();
        }
        return kods;
    }


    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        Log.v("---","menu");
        super.onCreateOptionsMenu(menu,inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_search, menu);


        SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
        search.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("----", "onQueryTextSubmit ");
                cursor=studentRepo.getKodListByKeyword(s);
                if (cursor==null){
                    request_butt.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(),"No records found!",Toast.LENGTH_LONG).show();
                }else{
                    request_butt.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), cursor.getCount() + " records found!",Toast.LENGTH_LONG).show();
                }
                customAdapter.swapCursor(cursor);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                request_butt.setVisibility(View.GONE);

                Log.d("--ssss-", "onQueryTextChange ");
                cursor=studentRepo.getKodListByKeyword(s);
                if (cursor!=null){
                    customAdapter.swapCursor(cursor);
                }
                return false;
            }

//                if(searchMenuItem.collapseActionView() == true){
//
//                }

        });












//        MenuItem searchMenuItem = menu.findItem(R.id.search);
//
//        MenuItemCompat.setOnActionExpandListener(searchMenuItem , new MenuItemCompat.OnActionExpandListener() {
//    @Override
//    public boolean onMenuItemActionExpand(MenuItem menuItem) {
//        Log.v("sadaghfg hfghghd","-------------");
//
//
//        //   searchMenuItem
//
//        return false;
//    }
//
//    @Override
//    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
//        request_butt.setVisibility(View.GONE);
//        return false;
//    }
//});
//        MenuItemCompat.setActionView(searchMenuItem, search);





        //  SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//            SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
//            search.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
//         //   searchMenuItem
//            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//                @Override
//                public boolean onQueryTextSubmit(String s) {
//                    Log.d("----", "onQueryTextSubmit ");
//                    cursor=studentRepo.getKodListByKeyword(s);
//                    if (cursor==null){
//                        request_butt.setVisibility(View.VISIBLE);
//                        Toast.makeText(getActivity(),"No records found!",Toast.LENGTH_LONG).show();
//                    }else{
//                        Toast.makeText(getActivity(), cursor.getCount() + " records found!",Toast.LENGTH_LONG).show();
//                    }
//                    customAdapter.swapCursor(cursor);
//
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String s) {
//                    Log.d("---", "onQueryTextChange ");
//                    cursor=studentRepo.getKodListByKeyword(s);
//                    if (cursor!=null){
//                        customAdapter.swapCursor(cursor);
//                    }
//                    return false;
//                }
//
////                if(searchMenuItem.collapseActionView() == true){
////
////                }
//
//            });
//            search.setOnCloseListener(new SearchView.OnCloseListener() {
//                @Override
//                public boolean onClose() {
//                    Log.v("sadasdsad","----------------");
//
//                    return true;
//                }
//            });
//            request_butt.setVisibility(View.INVISIBLE);
//
//        }

        // return true;

    }
}
