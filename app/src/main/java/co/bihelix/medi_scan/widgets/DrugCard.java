package co.bihelix.medi_scan.widgets;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import co.bihelix.medi_scan.R;

public class DrugCard extends ArrayAdapter<String> {

    private final Activity context;
    private final String name;
    private final String company;
    private final String desc;
    private final String se;

    public DrugCard(Activity context, String name, String company, String desc, String se) {
        super(context, R.layout.card_drug);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.name = name;
        this.company = company;
        this.desc = desc;
        this.se = se;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.card_drug, null, true);

//        ImageView ivDrug = rowView.findViewById(R.id.iv_drug);
        TextView tvName = rowView.findViewById(R.id.tv_name);
        TextView tvCompany = rowView.findViewById(R.id.tv_company);
        TextView tvDesc = rowView.findViewById(R.id.tv_desc);
        TextView tvSe = rowView.findViewById(R.id.tv_se);

//        ivDrug.setImageResource(imgid[position]);
        tvName.setText(name);
        tvCompany.setText(company);
        tvDesc.setText(desc);
        tvSe.setText(se);

        return rowView;

    }

    ;
}
