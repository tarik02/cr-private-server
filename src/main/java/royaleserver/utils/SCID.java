package royaleserver.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public final class SCID implements UserType, Serializable {
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

	// Java

	@Override
	public int hashCode() {
		return (int)getValue();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof SCID) {
			return getValue() == ((SCID)o).getValue();
		}

		return super.equals(o);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new SCID(high, low);
	}

	@Override
	public String toString() {
		return (new StringBuilder())
				.append("SCID(high = ").append(high)
				.append(", low = ").append(low)
				.append(", value = ").append(getValue())
				.append(")").toString();
	}


	// Hibernate

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.BIGINT };
	}

	@Override
	public Class returnedClass() {
		return SCID.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == null || y == null) {
			return x == y;
		}

		return x.equals(y);
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
		final String value = rs.getString(names[0]);
		if (value == null) {
			return null;
		}

		return new SCID(Long.valueOf(value));
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.BIGINT);
			return;
		}

		st.setLong(index, ((SCID)value).getValue());
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable)value;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}
}
