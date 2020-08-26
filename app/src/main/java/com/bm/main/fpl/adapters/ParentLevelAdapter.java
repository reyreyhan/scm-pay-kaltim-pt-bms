package com.bm.main.fpl.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.models.DownlineModel;
import com.bm.main.fpl.models.DownlineModelResponse_valueLevel2;

import java.util.ArrayList;
import java.util.List;


public class ParentLevelAdapter extends BaseExpandableListAdapter {
    private final Context mContext;
//    ArrayList<DownlineModel.Response_value> data = new ArrayList<>();
//    ArrayList<DownlineModel.Response_value> filterData = new ArrayList<>();
//    ArrayList<DownlineModelResponse_valueLevel2> childData = new ArrayList<>();


   // Map<DownlineModel, List<DownlineModelResponse_valueLevel2>> mListData_SecondLevel_Map;
  //  private final Map<DownlineModelResponse_value, List<DownlineModelResponse_valueLevel2>> mListData_SecondLevel_Map;
   // private final Map<String, List<String>> mListData_ThirdLevel_Map;
//  ArrayList<DownlineModel> mListDataHeader = new ArrayList<>();

     List<DownlineModel.Response_value>ListTerbaru;
     ArrayList<ArrayList<DownlineModelResponse_valueLevel2>> ListChildTerbaru;

    public ParentLevelAdapter(Context mContext, @NonNull List<DownlineModel.Response_value> ListTerbaru) {
        this.mContext = mContext;
        //this.ListTerbaru=ListTerbaru;
        this.ListTerbaru = new ArrayList<>();
        this.ListTerbaru.addAll(ListTerbaru);
//        this.ListChildTerbaru=ListChildTerbaru;
//
//
       // this.ListTerbaru.addAll(ListTerbaru);

        for (int i = 0; i < ListTerbaru.size(); i++) {
            Log.d("parent", "ParentLevelAdapter: "+ListTerbaru.get(i).getId_outlet());
         //   this.ListTerbaru.add(ListTerbaru.get(i));
        }
//        this.data =mListDataHeader;
//        this.filterData =mListDataHeader;
       // this.mListDataHeader = mListDataHeader;
       //this.mListDataHeader.addAll(mListDataHeader);
       // mListData_SecondLevel_Map = new HashMap<>();

//        List<DownlineModelResponse_valueLevel2> mItemHeaders=new ArrayList<>();
//
//        int parentCount = mListDataHeader.size();
//        for (int i = 0; i < parentCount; i++) {
//
//
        //    mListData_SecondLevel_Map.putAll()
//        }
       // this.mListDataHeader.addAll(mListDataHeader);
        // SECOND LEVEL
      //  String[] mItemHeaders;
       // mListDataSubHeader.add();
      //  List<DownlineModelResponse_value> listChild;
//        mListData_SecondLevel_Map = new HashMap<>();
//        int parentCount = mListDataHeader.size();
//        for (int i = 0; i < parentCount; i++) {
//            mListDataSubHeader.add(mListDataHeader.get(i));
////           // String content = mListDataHeader.get(i);
//////            switch (content) {
//////                case "Level 1.1":
//////                    mItemHeaders = mContext.getResources().getStringArray(R.array.items_array_expandable_level_one_one_child);
//////                    break;
//////                case "Level 1.2":
//////                    mItemHeaders = mContext.getResources().getStringArray(R.array.items_array_expandable_level_one_two_child);
//////                    break;
//////                default:
//////                    mItemHeaders = mContext.getResources().getStringArray(R.array.items_array_expandable_level_two);
//////            }
////            listChild=
////            mListData_SecondLevel_Map.put(mListDataHeader.get(i), listChild);
//        }
        // THIRD LEVEL
      //  String[] mItemChildOfChild;
      //  List<String> listChild;
        //mListData_ThirdLevel_Map = new HashMap<>();
//        for (Object o : mListData_SecondLevel_Map.entrySet()) {
//            Map.Entry entry = (Map.Entry) o;
//            Object object = entry.getValue();
//            if (object instanceof List) {
//                List<String> stringList = new ArrayList<>();
//                Collections.addAll(stringList, (String[]) ((List) object).toArray());
//                for (int i = 0; i < stringList.size(); i++) {
//                    mItemChildOfChild = mContext.getResources().getStringArray(R.array.items_array_expandable_level_three);
//                    listChild = Arrays.asList(mItemChildOfChild);
//                    mListData_ThirdLevel_Map.put(stringList.get(i), listChild);
//                }
//            }
//        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }
@Override
public boolean areAllItemsEnabled()
{
    return true;
}
//    @Override
//    public DownlineModelResponse_valueLevel2 getChild(int groupPosition, int childPosition) {
//        return ListChildTerbaru.get(groupPosition).get(childPosition);
//    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Nullable
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, @Nullable View convertView, ViewGroup parent) {

      //  DownlineModelResponse_valueLevel2 ListChildTerbaru = (DownlineModelResponse_valueLevel2) ((ArrayList<DownlineModelResponse_valueLevel2>)getChild(groupPosition, childPosition)).get(0);
     //   ViewHolder holder= null;


      //  final CustomExpListView secondLevelExpListView = new CustomExpListView(this.mContext);
       // DownlineModel header=mListDataHeader.get(groupPosition);

      //  secondLevelExpListView.setAdapter(new SecondLevelAdapter(this.mContext, filterData.get(parentNode), child));
       // DownlineModel.Response_value parentNode=filterData.get(groupPosition);
       // String parentNode = (String) getGroup(groupPosition);
       // secondLevelExpListView.setAdapter(new SecondLevelAdapter(this.mContext,child.));
//       // secondLevelExpListView.setGroupIndicator(null);
//        secondLevelExpListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            int previousGroup = -1;
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                if (groupPosition != previousGroup)
//                    secondLevelExpListView.collapseGroup(previousGroup);
//                previousGroup = groupPosition;
//            }
//        });
      //  return secondLevelExpListView;

        DownlineModelResponse_valueLevel2 ListChildTerbaru = (DownlineModelResponse_valueLevel2)getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.drawer_list_item, parent, false);
        }
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        txtListChild.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        txtListChild.setText(ListChildTerbaru.getId_outlet2());
        return convertView;

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
        //return ListChildTerbaru.get(groupPosition).size();
    }

//    @Override
//    public Object getGroup(int groupPosition) {
//        Log.d("adapter", "getGroup: "+this.ListTerbaru.get(groupPosition));
//        return this.ListTerbaru.get(groupPosition);
//    }

    @Override
    public DownlineModel.Response_value getGroup(int groupPosition) {
        return this.ListTerbaru.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
       // return this.filterData.size();
      //  Log.i("adapter", "getGroupCount: "+this.ListTerbaru.size());
        return this.ListTerbaru.size();

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
       // String headerTitle = (String) getGroup(groupPosition);
      //  DownlineModel.Response_value headerTitle=filterData.get(groupPosition);
       // DownlineModel.Response_value ListTerbaru =this.ListTerbaru.get(groupPosition);
        String s = ListTerbaru.get(groupPosition).getId_outlet();
        Log.d("parent", "getGroupView: "+s);
      //  DownlineModel.Response_value x=ListTerbaru.getData().get(groupPosition);
       // if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.drawer_list_group, parent, false);
      //  }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setTextColor(Color.CYAN);
        //lblListHeader.setText(ListTerbaru.getId_outlet());
        for (int i = 0; i < ListTerbaru.size(); i++) {
           // Log.d("parent", "ParentLevelAdapter: "+ListTerbaru.get(i).getId_outlet());
            lblListHeader.setText(ListTerbaru.get(i).getId_outlet());
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


    static class ViewHolder{
        TextView begdate1, enddate1,nama, alamat, imageid;
    }
}
