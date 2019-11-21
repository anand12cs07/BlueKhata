package com.everyrupee.di.dependency;

import com.everyrupee.di.PerFragment;

import javax.inject.Inject;

@PerFragment
public final class FragmentDependency {
    @Inject
    FragmentDependency() {
    }
}
