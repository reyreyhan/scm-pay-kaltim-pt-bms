package com.bm.main.fpl.adapters;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.main.pos.R;
import com.bm.main.fpl.models.DownlineChildModel;
import com.bm.main.fpl.models.DownlineGroupModel;

import java.util.ArrayList;

/**
 * Created by sarifhidayat on 6/22/18.
 **/
public class ExpandListDownlineAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<DownlineGroupModel> groups;

   // ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    public ExpandListDownlineAdapter(Context context, ArrayList<DownlineGroupModel> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<DownlineChildModel> chList = groups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Nullable
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, @Nullable View convertView, ViewGroup parent) {

        DownlineChildModel child = (DownlineChildModel) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_child_downline, null);
        }

//        if (imageLoader == null)
//            imageLoader = MyApplication.getInstance().getImageLoader();

        //TextView tv = (TextView) convertView.findViewById(R.id.country_name);
//        NetworkImageView iv = (NetworkImageView) convertView
//                .findViewById(R.id.flag);

//        tv.setText(child.getName().toString());
//        iv.setImageUrl(child.getImage(), imageLoader);

        TextView textViewNamaBinaan2=convertView.findViewById(R.id.textViewNamaBinaan2);
        TextView textViewJumlahBinaan2=convertView.findViewById(R.id.textViewJumlahBinaan2);
        TextView textViewIdBinaan2=convertView.findViewById(R.id.textViewIdBinaan2);


        textViewNamaBinaan2.setText(child.getNama_pemilik2());
        textViewJumlahBinaan2.setText("Jumlah Binaan : " + child.getJumlah_downline2());
        textViewIdBinaan2.setText(child.getId_outlet2());

//        if(!child.getJumlah_downline2().equals("0")) {
//            viewHolder.imageViewDrop1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (viewHolder.linMainListBinaan2.getVisibility() == View.GONE) {
//                        viewHolder.linMainListBinaan2.setVisibility(View.VISIBLE);
//                        viewHolder.imageViewDrop1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_down_arrow));
//                        viewHolder.imageViewDrop1.getDrawingCache(true);
//                    } else {
//                        viewHolder.linMainListBinaan2.setVisibility(View.GONE);
//                        viewHolder.imageViewDrop1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_right_arrow));
//                        viewHolder.imageViewDrop1.getDrawingCache(true);
//                    }
//                }
//            });
//            viewHolder.imageViewDrop1Empty.setVisibility(View.GONE);
//            viewHolder.imageViewDrop1.setVisibility(View.VISIBLE);
//        }else{
//            viewHolder.linMainListBinaan2.setVisibility(View.GONE);
//            viewHolder.imageViewDrop1Empty.setVisibility(View.VISIBLE);
//            viewHolder.imageViewDrop1.setVisibility(View.GONE);
//        }



        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<DownlineChildModel> chList = groups.get(groupPosition).getItems();
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Nullable
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             @Nullable View convertView, ViewGroup parent) {
   //     NavDrawerItem itemHeader = (NavDrawerItem) getGroup(groupPosition);

        DownlineGroupModel group = (DownlineGroupModel) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.list_group_downline, null);
        }
//        TextView tv = (TextView) convertView.findViewById(R.id.group_name);
//        tv.setText(group.getName());
        TextView textViewNamaBinaan1=convertView.findViewById(R.id.textViewNamaBinaan1);
        TextView textViewJumlahBinaan1=convertView.findViewById(R.id.textViewJumlahBinaan1);
        TextView textViewIdBinaan1=convertView.findViewById(R.id.textViewIdBinaan1);

        ImageView imageViewDrop1=convertView.findViewById(R.id.imageViewDrop1);
        ImageView imageViewDrop1Empty=convertView.findViewById(R.id.imageViewDrop1Empty);

        textViewNamaBinaan1.setText(group.getNama_pemilik());
         textViewJumlahBinaan1.setText("Jumlah Binaan : "+group.getJumlah_downline());
         textViewIdBinaan1.setText(group.getId_outlet());


//        if(!group.getJumlah_downline().equals("0")) {
//            imageViewDrop1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (viewHolder.linMainListBinaan2.getVisibility() == View.GONE) {
//                        viewHolder.linMainListBinaan2.setVisibility(View.VISIBLE);
//                        viewHolder.imageViewDrop1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_down_arrow));
//                        viewHolder.imageViewDrop1.getDrawingCache(true);
//                    } else {
//                        viewHolder.linMainListBinaan2.setVisibility(View.GONE);
//                        viewHolder.imageViewDrop1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_right_arrow));
//                        viewHolder.imageViewDrop1.getDrawingCache(true);
//                    }
//                }
//            });
//            viewHolder.imageViewDrop1Empty.setVisibility(View.GONE);
//            viewHolder.imageViewDrop1.setVisibility(View.VISIBLE);
//        }else{
//            viewHolder.linMainListBinaan2.setVisibility(View.GONE);
//            viewHolder.imageViewDrop1Empty.setVisibility(View.VISIBLE);
//            viewHolder.imageViewDrop1.setVisibility(View.GONE);
//        }

//        ImageView iconExpand = (ImageView) convertView.findViewById(R.id.imageViewDrop1);
//        ImageView iconCollapse = (ImageView) convertView
//                .findViewById(R.id.icon_collapse);

        if (isExpanded) {
           // iconExpand.setVisibility(View.GONE);
           // iconCollapse.setVisibility(View.VISIBLE);
            imageViewDrop1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_up_arrow));
        } else {
//            iconExpand.setVisibility(View.VISIBLE);
//            iconCollapse.setVisibility(View.GONE);
            imageViewDrop1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_down_arrow));
        }

//        if (getChildrenCount(groupPosition) == 0) {
////            iconExpand.setVisibility(View.GONE);
////            iconCollapse.setVisibility(View.GONE);
//            //isExpanded=false;
//            imageViewDrop1.setVisibility(View.GONE);
//        }
        if (group.getJumlah_downline().equals("0")) {
//            iconExpand.setVisibility(View.GONE);
//            iconCollapse.setVisibility(View.GONE);
            //isExpanded=false;
            imageViewDrop1Empty.setVisibility(View.VISIBLE);
            imageViewDrop1.setVisibility(View.GONE);
        }else{
            imageViewDrop1Empty.setVisibility(View.GONE);
            imageViewDrop1.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
