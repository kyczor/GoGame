/**
 * Created by Karola on 2016-12-21.
 */
public enum whosefield {
	empty, black, white, temp;

	public int toInt() {
		switch (this) {
		case empty:
			return 0;
		case black:
			return 1;
		case white:
			return 2;
		case temp:
			return 3;
		}
		return -1;
	}

	public static whosefield fromInt(int x) {
		switch (x) {
		case 0:
			return empty;
		case 1:
			return black;
		case 2:
			return white;
		case 3:
			return temp;
		}
		return null;
	}

}