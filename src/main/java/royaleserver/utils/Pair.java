package royaleserver.utils;

public final class Pair<A, B> {
	private A first;
	private B second;

	public Pair(A first, B second) {
		super();
		this.first = first;
		this.second = second;
	}

	public final A first() {
		return first;
	}

	public final B second() {
		return second;
	}

	public int hashCode() {
		int hashFirst = first != null ? first.hashCode() : 0;
		int hashSecond = second != null ? second.hashCode() : 0;

		return (hashFirst + hashSecond) * hashSecond + hashFirst;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Pair)) {
			return false;
		}

		Pair<?, ?> pair = (Pair<?, ?>)o;

		return first != null ? first.equals(pair.first) : pair.first == null;
	}

	public final String toString() {
		return "(" + first + ", " + second + ")";
	}
}
