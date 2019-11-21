package com.everyrupee.ui.tags;

import com.everyrupee.data.model.db.Tag;

public interface TagItemListener {
    void onEdit(Tag tag);
    void onDelete(Tag tag);
}
