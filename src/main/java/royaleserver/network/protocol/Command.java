package royaleserver.network.protocol;

public abstract class Command {
	public final short id;

	protected Command(short id) {
		this.id = id;
	}
}
