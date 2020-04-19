package com.bluekhata.di.dependency;

import com.bluekhata.di.PerFragment;

import javax.inject.Inject;

@PerFragment
public final class FragmentDependency {
    @Inject
    FragmentDependency() {
    }
}
