package com.everyrupee.di.dependency;

import com.everyrupee.di.PerActivity;

import javax.inject.Inject;

@PerActivity
public final class ActivityDependency {
    @Inject
    ActivityDependency() {
    }
}
