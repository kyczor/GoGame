package models;

import java.awt.*;
import java.util.Map;

public interface Logic {
    int getSize();
    int getEmptyNumber();
    void setEmptyNumber(int value);
    int getFriendsNumber();
    void setFriendsNumber(int value);
    Status getField(int y, int x);
    void move(int y, int x, Status color);
    void chains(int y, int x, Status color);
    void newChain(int y, int x, Status color);
    void connectChains(int y, int x, Status color);
    void oneFriendChain(int y, int x, Status color, Point[] friend);
    void manyFriendsChain(int y, int x, Status color, Point[] friend);
    boolean checkBreath(int y, int x, Status color, boolean remove);
    void removeList(int index, Map<Integer, java.util.List<Point>> map, java.util.List<java.util.List<Point>> list);
    void cleanEmpty();
    boolean isEmpty(int y, int x);
    boolean ko(int y, int x, Status color);
    void lookAround(int y, int x, Status color, boolean markField);
}
