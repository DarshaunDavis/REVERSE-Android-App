package com.lislalcorporation.reverse;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ManagerAdapter extends FragmentStateAdapter {
    public ManagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new ReviewManagerFragment();
            case 2:
                return new ResolvedManagerFragment();
            case 3:
                return new EmployeeManagerFragment();
            default:
                return new ManagerFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
