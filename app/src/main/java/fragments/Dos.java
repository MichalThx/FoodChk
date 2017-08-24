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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.CustomAdapterSklad;
import developer.mstudio.foodchk.MainActivity;
import developer.mstudio.foodchk.R;
import helper.daneDBHelper;
import ocr.OcrCaptureActivity;
import query.Searching;
import szablon.Kod;
import szablon.Sklad;

/**
 * Created by MS on 29.08.2016.
 */
public class Dos extends Fragment {
    private CustomAdapterSklad customAdapter;
    ListView listView;
    Cursor cursor;
    ImageButton request_butt;
    ImageButton barcode_butt;

    //  ImageButton info_butt;
    Searching studentRepo ;
    String doSklad;
    private List<Sklad> sklads;
    private List<Kod> kods;
    public static int [] prgmImages={R.mipmap.grade_1,R.mipmap.grade_2,R.mipmap.grade_3,R.mipmap.grade_4,R.mipmap.grade_5,R.mipmap.grade_6,R.mipmap.grade_7,R.mipmap.grade_8,R.mipmap.grade_9};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.skladniki, container, false);

        String value = getArguments().getString("123");
        if(value!=null){
            //Log.v("123-----",value);
        }else{
            //Log.v("123","2------");
        }
        if(MainActivity.wuszukiwanie.isEmpty()){
            //Log.v("123","-------");
        }else{
            //Log.v("123",MainActivity.wuszukiwanie);
        }


        Log.v("========>","1------");
        sklads = getSklads();
        //  TextView tv = (TextView) v.findViewById(R.id.textView2);
        try {
            studentRepo = new Searching(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((MainActivity) getActivity()).setActionBarTitle("");
        cursor=studentRepo.getSkladList();

        customAdapter = new CustomAdapterSklad(getActivity(), cursor, 0,prgmImages);
        listView = (ListView) v.findViewById(R.id.listView2);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                doSklad = String.valueOf(sklads.get(i).getSklNAZ());
                // Log.v("list", String.valueOf(kods.get(i).getKodSKL()));
            }
        });
        request_butt = (ImageButton) v.findViewById(R.id.s_request);
        request_butt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.v("button","----");
                        request_butt.setVisibility(View.GONE);

                    }
                });

        barcode_butt = (ImageButton) v.findViewById(R.id.s_scanner);
        barcode_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Log.v("button","clicked");
                Intent intent = new Intent(getActivity(), OcrCaptureActivity.class);
                //  Log.v("===", "1");
                intent.putExtra(OcrCaptureActivity.AutoFocus, true);
                //Log.v("===", "2");
                startActivityForResult(intent, 9001);
                //Log.v("===", "3");

            }
        });

//        sklads = getSklads();
//        kods = getKods();
        //     setHasOptionsMenu(true);
        //  TextView tv = (TextView) v.findViewById(R.id.textView2);t

        setHasOptionsMenu(true);
        Log.v("========>","2------");
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        return v;
    }
    public static Dos newInstance(String text) {
        Log.v("========>","3------");
        Dos f = new Dos();
        Bundle b = new Bundle();
        // b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    private List<Sklad> getSklads() {
        List<Sklad> sklads = new ArrayList<>();

        daneDBHelper db = new daneDBHelper(getActivity().getApplicationContext());

        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }


        if (db.open()) {
            sklads = db.getSklads();
        }

        return sklads;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.v("========>","4------");
        super.onActivityResult( requestCode, resultCode,  data);
        // Log.v("----","1");
        if (resultCode == CommonStatusCodes.SUCCESS) {
            //  Log.v("----","2");
            if (data != null) {
                //  Log.v("----","3");
                String ocr = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                //  statusMessage.setText(R.string.barcode_success);
                //  Value.setText(barcode.displayValue);
                //onQueryTextSubmit(barcode.displayValue);
                // Log.v("----","4");
                String [] tablica = ocr.split("\\s+");
                int licznik=1;
//                String text=tablica[0].trim();
//                for(String w :tablica){
//                    String temp = w.trim();
//                    tablica[licznik]=temp;
//                    licznik++;
//                    Log.v("++++++++++++=+",temp);
//                    //text+=","+tablica[licznik];
//                }
                String text="";
                for(int i=0; i<tablica.length;i++){
                    String temp = tablica[i].trim();
                    Log.v("++++++++++++=+",temp);
                    text+=temp+",";
                }
                Log.v("++++++++++++=+",text);

                //PRZECINKI!!
                cursor=studentRepo.getSkladListByKeyword(text);


                if (cursor==null){
                    request_butt.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(),"No records found!",Toast.LENGTH_LONG).show();
                }else{
                    customAdapter = new CustomAdapterSklad(getActivity(), cursor, 0,prgmImages);
                    listView.setAdapter(customAdapter);
                }



                //cursor.
                // customAdapter.swapCursor(cursor);
                //  Log.v("----","5");
                //  Log.d("-------------------", "Barcode read: " + barcode.displayValue);
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
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {

        Log.v("========>","5------");
        //Log.v("---", "menu");
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_search, menu);


        SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
        //MenuItemCompat searchMenuItem= (MenuItemCompat) menu.findItem(R.id.search);
        search.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("----", "onQueryTextSubmit ");
                cursor = studentRepo.getSkladListByKeyword(s);
                if (cursor == null) {
                    request_butt.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "No records found!", Toast.LENGTH_LONG).show();
                } else {
                    request_butt.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), cursor.getCount() + " records found!", Toast.LENGTH_LONG).show();
                }
                customAdapter.swapCursor(cursor);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                request_butt.setVisibility(View.GONE);

                Log.d("--ssss-", s);
                cursor = studentRepo.getSkladListByKeyword(s);
                if (cursor != null) {
                    customAdapter.swapCursor(cursor);
                }
                return false;
            }


//                if(searchMenuItem.collapseActionView() == true){
//
//                }

        });
        if(MainActivity.wyszuk==1){
            search.setQuery(MainActivity.wuszukiwanie,false);
            Log.v("========++++", String.valueOf(MainActivity.wyszuk));
            //search.setIconified(false);
            //SearchView sView = (SearchView) item.getActionView();

            MainActivity.wyszuk++;

            // search.setset

        }
//        if(czy){
//                Log.v("========",">>>>>>>>>>");
//                cursor = studentRepo.getSkladListByKeyword(MainActivity.wuszukiwanie);
//                if (cursor==null){
//                    request_butt.setVisibility(View.VISIBLE);
//                    Toast.makeText(getActivity(),"No records found!",Toast.LENGTH_LONG).show();
//                }else{
//                    request_butt.setVisibility(View.GONE);
//                    Toast.makeText(getActivity(), cursor.getCount() + " records found!",Toast.LENGTH_LONG).show();
//                }
//                Log.v("========","<<<<<<<<<");
//                customAdapter = new CustomAdapterSklad(getActivity(), cursor, 0,prgmImages);
//                listView.setAdapter(customAdapter);
//                czy=false;
//            Log.v("========","1");
//
//        }

        Log.v("========","2");





    }

}