package me.lime.viewmodel.ui.main;

import android.os.Bundle;

import me.lime.viewmodel.R;
import me.lime.viewmodel.base.BaseActivity;
import me.lime.viewmodel.ui.list.ListFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.screenContainer, new ListFragment()).commit();
    }
}
