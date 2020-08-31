package com.bm.main.scm.feature.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.zhukic.sectionedrecyclerview.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_notification_scm_content.view.*
import kotlinx.android.synthetic.main.item_notification_scm_header.view.*

abstract class BaseNotificationSCMAdapter (val itemList: List<NotificationSCM>) :
    SectionedRecyclerViewAdapter<BaseNotificationSCMAdapter.SubheaderHolder, BaseNotificationSCMAdapter.NotificationViewHolder>() {

//    protected var itemClick: OnItemClick? = null
//
//    fun setItemClick(itemClick: OnItemClick?) {
//        this.itemClick = itemClick
//    }

    class SubheaderHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(item:NotificationSCM){
            itemView.tv_header.text = item.date
        }
    }

    class NotificationViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(item:NotificationSCM){
            itemView.tv_content.text = item.content
        }
    }

    override fun onCreateItemViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationViewHolder {
        return NotificationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification_scm_content, parent, false)
        )
    }

    override fun onCreateSubheaderViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubheaderHolder {
        return SubheaderHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification_scm_header, parent, false)
        )
    }

    @CallSuper
    override fun onBindSubheaderViewHolder(
        subheaderHolder: SubheaderHolder,
        nextItemPosition: Int
    ) {
        val isSectionExpanded: Boolean =
            isSectionExpanded(getSectionIndex(subheaderHolder.adapterPosition))

        /*
        if (isSectionExpanded) {
            subheaderHolder.mArrow.setImageDrawable(ContextCompat.getDrawable(subheaderHolder.itemView.getContext(), R.drawable.ic_arrow_up_black_24dp));
        } else {
            subheaderHolder.mArrow.setImageDrawable(ContextCompat.getDrawable(subheaderHolder.itemView.getContext(), R.drawable.ic_arrow_down_black_24dp));
        }*/
    }

    override fun getItemSize(): Int = itemList.size

    interface OnItemClick {
        fun onItemClick(
            view: View?,
            inbox: NotificationSCM?,
            position: Int)
    }
}