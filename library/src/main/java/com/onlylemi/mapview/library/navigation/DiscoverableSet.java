package com.onlylemi.mapview.library.navigation;

import java.util.Comparator;
import java.util.TreeSet;

public class DiscoverableSet {

    private TreeSet<IDiscoverable> set;

    public DiscoverableSet() {
        set = new TreeSet<>(new DiscoverableComparator());
    }

    public void add(IDiscoverable discoverable) {
        set.add(discoverable);
    }

    public IDiscoverable pop() {
        return set.pollFirst();
    }

    public int size() {
        return set.size();
    }

    class DiscoverableComparator implements Comparator<IDiscoverable> {

        @Override
        public int compare(IDiscoverable left, IDiscoverable right) {
            if(left.getCost() < right.getCost()) {
                return -1;
            } else if(left.getCost() > right.getCost()) {
                return 1;
            }
            return 0;
        }

    }
}
