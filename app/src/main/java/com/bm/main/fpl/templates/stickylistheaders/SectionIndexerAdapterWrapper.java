package com.bm.main.fpl.templates.stickylistheaders;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.SectionIndexer;

class SectionIndexerAdapterWrapper extends
        AdapterWrapper implements SectionIndexer {
	
	@Nullable
    SectionIndexer mSectionIndexerDelegate;

	SectionIndexerAdapterWrapper(Context context,
                                 @NonNull StickyListHeadersAdapter delegate) {
		super(context, delegate);
		mSectionIndexerDelegate = (SectionIndexer) delegate;
	}

	@Override
	public int getPositionForSection(int section) {
		return mSectionIndexerDelegate.getPositionForSection(section);
	}

	@Override
	public int getSectionForPosition(int position) {
		return mSectionIndexerDelegate.getSectionForPosition(position);
	}

	@Override
	public Object[] getSections() {
		return mSectionIndexerDelegate.getSections();
	}

}
