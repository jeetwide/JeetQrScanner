package com.jituscanner.utils;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by jeetendra.achtani on 08-03-2018.
 */

public class SwipeHelper {


    private void initSwipe()
    {
        try{
            SwipeHelper swipeHelper = new SwipeHelper(getActivity(), recyclerView) {
                @Override
                public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                    underlayButtons.add(new SwipeHelper.UnderlayButton(
                            "Delete",
                            0,
                            Color.parseColor("#6e8bad"),
                            new SwipeHelper.UnderlayButtonClickListener() {
                                @Override
                                public void onClick(int pos) {
                                    // TODO: onDelete

                                    App.showLog("=Delete====pos=="+pos);

                                    if(dataListAdapter !=null)
                                    {
                                        dataListAdapter.removeItem(pos);
                                    }
                                }
                            }
                    ));

                   /* underlayButtons.add(new SwipeHelper.UnderlayButton(
                            "Transfer",
                            0,
                            Color.parseColor("#FF9502"),
                            new SwipeHelper.UnderlayButtonClickListener() {
                                @Override
                                public void onClick(int pos) {
                                    // TODO: OnTransfer
                                    App.showLog("=Transfer====pos=="+pos);
                                }
                            }
                    ));*/
                }
            };
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

// Add on Adapter class


    public void removeItem(int position) {
        try {

            mArrListmPEArticleModel.remove(position);
            notifyItemRemoved(position);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
