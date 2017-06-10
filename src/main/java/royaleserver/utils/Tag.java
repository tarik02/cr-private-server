package royaleserver.utils;

import java.io.Serializable;

public final class Tag implements Comparable<Tag>, Serializable {
	private static final String TAG_CHARACTERS = "0289PYLQGRJCUV";
	private final long id;
	private transient String string = null;

	public Tag(long id) {
		this.id = id;
	}

	public Tag(String tag) {
		long parsedId = 0;

		for (char c : tag.toCharArray()) {
			int index = TAG_CHARACTERS.indexOf(c);
			parsedId *= TAG_CHARACTERS.length();
			parsedId += index;
		}

		final long high = parsedId % 256;
		final long low = (parsedId - high) >>> 8;

		id = (high << 32) | low;
	}

	public long id() {
		return id;
	}

	public String string() {
		if (string != null) {
			return string;
		}

		final long high = (id >> 32) & 0xFFFFFFFF;
		final long low = id & 0xFFFFFFFF;

		long parsedId = (low << 8) + high;
		final StringBuilder sb = new StringBuilder();

		while (parsedId != 0L) {
			int index = (int)(parsedId % TAG_CHARACTERS.length());
			sb.append(TAG_CHARACTERS.charAt(index));
			parsedId /= TAG_CHARACTERS.length();
		}

		return string = sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Tag)) {
			return false;
		}

		Tag tag = (Tag)o;

		return id == tag.id;
	}

	@Override
	public int hashCode() {
		return (int)(id ^ (id >>> 32));
	}

	@Override
	public String toString() {
		return string;
	}

	@Override
	public int compareTo(Tag tag) {
		return Long.compare(id, tag.id);
	}
}
