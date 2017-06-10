package royaleserver.database.entity;

import javax.persistence.*;

@Entity
@Table(name = "unlock_codes")
@NamedQueries({
		@NamedQuery(name = "UnlockCodeEntity.use", query = "DELETE FROM UnlockCodeEntity unlockCode WHERE unlockCode.code=:code")
})
public class UnlockCodeEntity {
	public static final int CODE_LENGTH = 12;

	@Id
	@Column(unique = true, nullable = false, length = CODE_LENGTH, updatable = false)
	private String code;

	public UnlockCodeEntity() {
	}

	public UnlockCodeEntity(String code) {
		assert code.length() == CODE_LENGTH;
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
