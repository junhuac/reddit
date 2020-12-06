package me.lime.viewmodel.ui.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.lime.viewmodel.ui.detail.DetailsFragment;
import me.lime.viewmodel.ui.list.ListFragment;

@Module
public abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract ListFragment provideListFragment();

    @ContributesAndroidInjector
    abstract DetailsFragment provideDetailsFragment();
}
