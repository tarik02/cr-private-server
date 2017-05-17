package royaleserver.utils;

public final class SCID {
	private int high, low;

	public SCID() {
		this(0, 0);
	}

	public SCID(int high, int low) {
		this.high = high;
		this.low = low;
	}

	public SCID(long value) {
		this((int)(value / 1000000), (int)(value % 1000000));
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public int getLow() {
		return low;
	}

	public void setLow(int low) {
		this.low = low;
	}

	public long getValue() {
		return high * 1000000 + low;
	}
}
