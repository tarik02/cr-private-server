package royaleserver.protocol.messages.component;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;

import royaleserver.utils.SCID;

public class SpellList extends Component {
	public SCID card;
	public int unknown_1;

	public SpellList() {
		card = new SCID();
		unknown_1 = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putSCID(card);
		stream.putRrsInt32(unknown_1);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		card = stream.getSCID();
		unknown_1 = stream.getRrsInt32();
	}
}
