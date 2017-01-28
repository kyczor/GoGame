package models;


public enum Status {
    black, white, empty, checked;

    public int toInt() {
        switch (this) {
            case empty:
                return 0;
            case black:
                return 1;
            case white:
                return 2;
            case checked:
                return 3;
        }
        return -1;
    }

    public static Status fromInt(int x) {
        switch (x) {
            case 0:
                return empty;
            case 1:
                return black;
            case 2:
                return white;
            case 3:
                return checked;
        }
        return null;
    }
}
