package com.martiandeveloper.wordpool.model;

public class NavigationMenu {

    private final String name;
    private final int image;
    private final boolean hasChildren;

    public NavigationMenu(String name, int image, boolean hasChildren) {
        this.name = name;
        this.image = image;
        this.hasChildren = hasChildren;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }
}
