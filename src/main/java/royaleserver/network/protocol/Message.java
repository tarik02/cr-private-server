package royaleserver.network.protocol;

public abstract class Message {
	public final short id;

	protected Message(short id) {
		this.id = id;
	}
}
