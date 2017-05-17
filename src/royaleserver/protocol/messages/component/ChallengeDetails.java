package royaleserver.protocol.messages.component;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;

public class ChallengeDetails extends Component {
	public int id;

	public ChallengeDetails() {
		id = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsInt32(id);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		id = stream.getRrsInt32();
	}
}
