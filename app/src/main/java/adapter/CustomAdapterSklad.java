package adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import developer.mstudio.foodchk.R;


public class CustomAdapterSklad extends CursorAdapter {

    TextView txtsid, txtsnaz, txtswar, txtslin;
    ImageView pic;
    ImageButton imageButton;
    private LayoutInflater mInflater;
    int [] imageId;


    public CustomAdapterSklad(Context context, Cursor c, int flags, int[] prgmImages) {
        super(context, c, flags);
        imageId=prgmImages;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View   view    =    mInflater.inflate(R.layout.skladniki_item, parent, false);
        ViewHolder holder  =   new ViewHolder();
        holder.txtsid   =   (TextView)  view.findViewById(R.id.txtsid);
        holder.txtsnaz  =   (TextView)  view.findViewById(R.id.txtsnaz);
        //  holder.txtswar  =   (TextView)  view.findViewById(R.id.txtswar);
        holder.txtslin  =   (TextView)  view.findViewById(R.id.txtslin);
        holder.pic = (ImageView) view.findViewById(R.id.pic);
        holder.imageButton =(ImageButton) view.findViewById(R.id.imageButton);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
//SKLAD
        ViewHolder holder  =   (ViewHolder)    view.getTag();
        holder.txtsid.setText(cursor.getString(cursor.getColumnIndex("ID")));
        holder.txtsnaz.setText(cursor.getString(cursor.getColumnIndex("NAZWA")));
        // holder.txtswar.setText(cursor.getString(cursor.getColumnIndex("WARTOSC")));
        holder.txtslin.setText(cursor.getString(cursor.getColumnIndex("LINK")));
        final String temp = "http://" +cursor.getString(cursor.getColumnIndex("LINK"));
        //Log.v("log",cursor.getString(cursor.getColumnIndex("WARTOSC")));
        if(cursor.getString(cursor.getColumnIndex("WARTOSC")).isEmpty()){
            holder.pic.setImageResource(imageId[8]);
        }else{
            holder.pic.setImageResource(imageId[Integer.parseInt(cursor.getString(cursor.getColumnIndex("WARTOSC")))-1]);
        }

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(temp)));

            }
        });
    }


    static class ViewHolder {
        TextView txtsid;
        TextView txtsnaz;
        // TextView txtswar;
        TextView txtslin;
        ImageView pic;
        ImageButton imageButton;

    }
}