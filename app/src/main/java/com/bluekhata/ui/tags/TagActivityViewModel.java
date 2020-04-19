package com.bluekhata.ui.tags;

import androidx.lifecycle.MutableLiveData;
import androidx.databinding.ObservableField;

import com.bluekhata.data.DataManager;
import com.bluekhata.data.model.db.Tag;
import com.bluekhata.ui.base.BaseViewModel;
import com.bluekhata.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class TagActivityViewModel extends BaseViewModel<TagActivityNavigator> {
    private final MutableLiveData<List<Tag>> tagList = new MutableLiveData<>();
    private final MutableLiveData<Tag> tagAdded = new MutableLiveData<>();
    private final MutableLiveData<String> snackBarMessage = new MutableLiveData<>();

    private final ObservableField<String> tagTitle = new ObservableField<>("");

    public TagActivityViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void fetchTagList() {
        getCompositeDisposable().add(getDataManager()
                .loadAllTags()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Tag>>() {
                    @Override
                    public void accept(List<Tag> tags) throws Exception {
                        tagList.setValue(tags);
                    }
                }));
    }

    public void addTag(final Tag tag) {
        if (tag.getTagTitle().isEmpty()) {
            snackBarMessage.setValue("Title can't be empty");
            return;
        }
        getCompositeDisposable().add(getDataManager()
                .addTag(tag)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aBoolean) throws Exception {
                        tagAdded.setValue(tag);
                        snackBarMessage.setValue(tag.getTagTitle() + " added");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        snackBarMessage.setValue(tag.getTagTitle() + " failed to add");
                    }
                }));
    }

    public void updateTag(final Tag tag) {
        if (tag.getTagTitle().isEmpty()) {
            snackBarMessage.setValue("Title can't be empty");
            return;
        }
        getCompositeDisposable().add(getDataManager()
                .updateTag(tag)
                .flatMap(new Function<Boolean, ObservableSource<List<Tag>>>() {
                    @Override
                    public ObservableSource<List<Tag>> apply(Boolean aBoolean) throws Exception {
                        return getDataManager().loadAllTags();
                    }
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Tag>>() {
                    @Override
                    public void accept(List<Tag> tags) throws Exception {
                        tagList.setValue(tags);
                        snackBarMessage.setValue(tag.getTagTitle() + " is updated");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        snackBarMessage.setValue(tag.getTagTitle() + " is not updated");
                    }
                }));
    }

    public void deleteTag(final Tag tag) {
        getCompositeDisposable().add(getDataManager()
                .deleteTag(tag)
                .flatMap(new Function<Boolean, ObservableSource<List<Tag>>>() {
                    @Override
                    public ObservableSource<List<Tag>> apply(Boolean aBoolean) throws Exception {
                        return getDataManager().loadAllTags();
                    }
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Tag>>() {
                    @Override
                    public void accept(List<Tag> tags) throws Exception {
                        tagList.setValue(tags);
                        snackBarMessage.setValue(tag.getTagTitle() + " is deleted");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        snackBarMessage.setValue(tag.getTagTitle() + " is not deleted");
                    }
                }));
    }

    public MutableLiveData<List<Tag>> getTagList() {
        return tagList;
    }

    public MutableLiveData<Tag> getTagAdded() {
        return tagAdded;
    }

    public MutableLiveData<String> getSnackBarMessage() {
        return snackBarMessage;
    }

    public ObservableField<String> getTagTitle() {
        return tagTitle;
    }
}
