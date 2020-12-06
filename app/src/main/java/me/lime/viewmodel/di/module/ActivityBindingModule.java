package me.lime.viewmodel.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.lime.viewmodel.ui.main.MainActivity;
import me.lime.viewmodel.ui.main.MainFragmentBindingModule;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = {MainFragmentBindingModule.class})
    abstract MainActivity bindMainActivity();
}
