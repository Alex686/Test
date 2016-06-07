package com.alex.test.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alex.test.R;
import com.alex.test.forsaiku.IRepositoryObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class HistoryListAdapter extends BaseAdapter {
    IRepositoryObject iRepositoryhistory;//содержимое директории истории




    private class Pair {
        IRepositoryObject item;
        int level;

        Pair (IRepositoryObject item, int level) {
            this.item = item;
            this.level = level;
        }
    }


    private LayoutInflater mLayoutInflater; // 1
    private ArrayList<Pair> hierarchyArray; // 2

    private ArrayList<IRepositoryObject> originalItems; // 3
    private LinkedList<IRepositoryObject> openItems; // 4

    public HistoryListAdapter(Context ctx, ArrayList<IRepositoryObject> items) {
        mLayoutInflater = LayoutInflater.from(ctx);
        originalItems = items;

        hierarchyArray = new ArrayList<>();

        openItems = new LinkedList<>();

        generateHierarchy(); // 5
    }

    @Override
    public int getCount() {
        return hierarchyArray.size();
    }

    @Override
    public Object getItem(int position) {
        return hierarchyArray.get(position).item;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mLayoutInflater.inflate(R.layout.item, null);
        TextView title = (TextView)convertView.findViewById(R.id.tvDescr);

        Pair pair = hierarchyArray.get(position);

        title.setText(pair.item.getName());
        title.setCompoundDrawablesWithIntrinsicBounds(pair.item.getimage(), 0, 0, 0);
        title.setPadding(pair.level * 25, 0, 0, 0); // 1
        return convertView;
    }

    private void generateHierarchy() {
        hierarchyArray.clear(); // 1
        generateList(originalItems, 0); // 1
    }

    private void generateList(List<IRepositoryObject> items, int level) { // 3
        for (IRepositoryObject i : items) {
            hierarchyArray.add(new Pair(i, level));
            if (openItems.contains(i))
                generateList(i.getRepoObjects(), level + 1); // 2
        }
    }

    public void clickOnItem (int position) {

        IRepositoryObject i = hierarchyArray.get(position).item;
        if (i.getType() == IRepositoryObject.Type.FOLDER) {
            iRepositoryhistory =i;
            if (!closeItem(i)) // 1
                openItems.add(i);

            generateHierarchy();
            notifyDataSetChanged();
        } else
        {
            iRepositoryhistory =i ;

            // TODO: 02.06.2016  
            return;
        }
    }

    private boolean closeItem (IRepositoryObject i) {
        if (openItems.remove(i)) { // 2
            for (IRepositoryObject c : i.getRepoObjects()) // 3
                closeItem(c);
            return true;
        }
        return false;
    }


}