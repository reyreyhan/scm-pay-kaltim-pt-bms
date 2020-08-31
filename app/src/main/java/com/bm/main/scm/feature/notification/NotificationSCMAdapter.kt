package com.bm.main.scm.feature.notification

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.view.ActionMode

class NotificationSCMAdapter(itemList: List<NotificationSCM>) : BaseNotificationSCMAdapter(itemList) {
    var holder: NotificationViewHolder? = null
    var actionMode: ActionMode? = null

    override fun onPlaceSubheaderBetweenItems(position: Int): Boolean {
        val notifDate: String = itemList[position].date!!
        val nextNotifDate: String = itemList[position + 1].date!!
        return notifDate != nextNotifDate
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onBindItemViewHolder(holder: NotificationViewHolder, position: Int) {
        this.holder = holder
        val notif = itemList[position]
        holder.bind(notif)
    }

    override fun onBindSubheaderViewHolder(
        subheaderHolder: SubheaderHolder,
        nextItemPosition: Int
    ) {
        super.onBindSubheaderViewHolder(subheaderHolder, nextItemPosition)
        //final Context context = subheaderHolder.itemView.getContext();
        val nextNotif = itemList[nextItemPosition]
        //final int sectionSize = getSectionSize(getSectionIndex(subheaderHolder.getAdapterPosition()));
        val date: String = nextNotif.date!!/*.substring(0, 10)*/
//        val dateFormatted: String = NotificationUtils.dateConverter(date)
        subheaderHolder.bind(nextNotif)
    }

//    val nextPage: Int
//        get() {
//            page = page + NotificationUtils.NOTIF_OFFSET
//            return page
//        }

}
