package com.tarik02.clashroyale.server.utils;

public final class Bitset {
	private byte value;

	public Bitset() {
		this((byte)0);
	}

	public Bitset(byte value) {
		this.value = value;
	}

	public byte get(int index) {
		return (byte)((value >> index) & 0x01);
	}

	public Bitset set(int index, boolean value) {
		if (value) {
			this.value |= 1 << index;
		} else {
			this.value &= ~(1 << index);
		}

		return this;
	}

	public byte getValue() {
		return value;
	}
}
