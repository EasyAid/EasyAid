package com.easy.aid.Class.Card;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import com.easy.aid.Class.NetVariables;
import com.easy.aid.Class.Ricetta;

import java.util.ArrayList;
import java.util.List;

public class CardFragmentPagerAdapter extends FragmentStatePagerAdapter implements CardAdapter {

    private List<CardFragment> fragments;
    private float baseElevation;
    private NetVariables global;
    private List<Ricetta> ordine;

    public CardFragmentPagerAdapter(FragmentManager fm, float baseElevation, NetVariables g, List<Ricetta> o) {
        super(fm);
        fragments = new ArrayList<>();
        this.baseElevation = baseElevation;
        global = g;
        ordine = o;

        for(int i = 0; i< global.ricette.size(); i++){
            addCardFragment(new CardFragment());
        }

    }

    @Override
    public float getBaseElevation() {
        return baseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return fragments.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return CardFragment.getInstance(position, global, ordine, fragments, this);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        fragments.set(position, (CardFragment) fragment);
        return fragment;
    }

    public void addCardFragment(CardFragment fragment) {
        fragments.add(fragment);
    }

    public void clearFragments() {
        fragments.clear();
        notifyDataSetChanged();
    }

    public void deletePage(int position)
    {
        // Remove the corresponding item in the data set
        fragments.remove(position);
        // Notify the adapter that the data set is changed
        notifyDataSetChanged();
    }

}
