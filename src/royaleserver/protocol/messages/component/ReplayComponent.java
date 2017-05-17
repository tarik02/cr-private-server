package royaleserver.protocol.messages.component;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;

public class ReplayComponent extends Component {
	public byte id;

	public ReplayComponent() {
		id = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putByte(id);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		id = stream.getByte();
	}
}
