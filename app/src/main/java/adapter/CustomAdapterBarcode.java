package adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import developer.mstudio.foodchk.R;

/**
 * Created by MS on 29.08.2016.
 */
public class CustomAdapterBarcode  extends CursorAdapter {

    TextView txtId, txtSklad, txtBarcode;
    private LayoutInflater mInflater;

    public CustomAdapterBarcode(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.barcode_item, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.txtId = (TextView) view.findViewById(R.id.txtId);
        holder.txtBarcode = (TextView) view.findViewById(R.id.txtBarcode);
        holder.txtSklad = (TextView) view.findViewById(R.id.txtSklad);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

//SKLAD
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.txtId.setText(cursor.getString(cursor.getColumnIndex("ID")));
        holder.txtSklad.setText(cursor.getString(cursor.getColumnIndex("SKLAD")));
        holder.txtBarcode.setText(cursor.getString(cursor.getColumnIndex("BARCODE")));

    }

    static class ViewHolder {
        TextView txtId;
        TextView txtSklad;
        TextView txtBarcode;
    }
}