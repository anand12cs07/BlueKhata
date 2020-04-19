package com.bluekhata.ui.tags;

import com.bluekhata.data.model.db.Tag;

public interface TagItemListener {
    void onEdit(Tag tag);
    void onDelete(Tag tag);
}
