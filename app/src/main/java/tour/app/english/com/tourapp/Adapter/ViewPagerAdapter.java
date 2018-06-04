package tour.app.english.com.tourapp.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tour.app.english.com.tourapp.Fragment.FragmentAccomodation;
import tour.app.english.com.tourapp.Fragment.FragmentAirport;
import tour.app.english.com.tourapp.Fragment.FragmentRestaurant;

public class ViewPagerAdapter extends FragmentPagerAdapter{

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        FragmentAccomodation fragmentAccomodation = new FragmentAccomodation();
        FragmentAirport fragmentAirport = new FragmentAirport();
        FragmentRestaurant fragmentRestaurant = new FragmentRestaurant();

        if(position == 0)
            return fragmentAccomodation;
        if(position == 1)
            return  fragmentAirport;
        if(position == 2)
            return fragmentRestaurant;

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0)
            return "숙박";
        if(position == 1)
            return  "공항";
        if(position == 2)
            return  "음식점";

        return super.getPageTitle(position);
    }
}
