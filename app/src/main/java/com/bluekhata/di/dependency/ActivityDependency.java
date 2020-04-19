package com.bluekhata.di.dependency;

import com.bluekhata.di.PerActivity;

import javax.inject.Inject;

@PerActivity
public final class ActivityDependency {
    @Inject
    ActivityDependency() {
    }
}
