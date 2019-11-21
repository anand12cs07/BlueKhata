package com.everyrupee.ui.tags;

import androidx.databinding.ObservableField;

import com.everyrupee.data.model.db.Tag;

public class TagItemViewModel{
    private ObservableField<Boolean> isTagEditable = new ObservableField<>(false);
    private ObservableField<Boolean> isTagEditable1 = new ObservableField<>(false);
    private ObservableField<String> tagTitle;

    public TagItemViewModel(Tag tag){
        tagTitle = new ObservableField<>(tag.getTagTitle());
    }

    public void onEditClick() {
        isTagEditable1.set(!isTagEditable1.get());
    }

    public void onDeleteClick(){

    }

    public ObservableField<String> getTagTitle() {
        return tagTitle;
    }

    public void setIsTagEditable(Boolean isTagEditable) {
        this.isTagEditable.set(isTagEditable);
    }

    public ObservableField<Boolean> getIsTagEditable() {
        return isTagEditable;
    }

    public void setIsTagEditable1(Boolean isTagEditable1) {
        this.isTagEditable1.set(isTagEditable1);
    }

    public ObservableField<Boolean> getIsTagEditable1() {
        return isTagEditable1;
    }
}
