package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.server.components.Deck;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;

public final class HomeDataVisited extends HomeData {
	public static final short ID = Messages.HOME_DATA_VISITED;

	public int place;
	public Deck deck;

	public HomeDataVisited() {
		super(ID);
	}

	@Override
	public ServerMessage create() {
		return new HomeDataVisited();
	}

	@Override
	public void encode(DataStream stream) {
		stream.putRrsInt32(250);
		stream.putRrsInt32(place);

		stream.putByte((byte)0xFF); // It seems that's always before deck
		deck.encode(stream);

		stream.putBLong(homeId);

		stream.putByte((byte)0);
		stream.putByte((byte)0);

		super.encode(stream);

		// Unknown
		stream.putRrsInt32(1);

		// ?Crowns earned => ladder?
		stream.putRrsInt32(0);

		// Tutorial Step (06 = setName , 08 = lastDone)
		stream.putRrsInt32(8);

		// Tournament?
		stream.putRrsInt32(0);

		// Unexplored data
		stream.put(Hex.toByteArray("0419bbb1bb0519bbb1bb0519bbb1bb0500000004587365740008ae3480080000000000001f000000000007120501ade8030502b0070503000504000505ade803050cbb0c050d00050e00050fad080510a5030511b2030512a7030513aa030516b70b051998dee2de09051a0d051c00051d8e88d544001e3c001a3c0192e7013c0292e7013c0392e7013c040a3c050a3c060a3c0785013c0885013c0985013c0a013c0b8e0c3c0c8e0c3c0d8e0c3c0e013c0f013c10013c11960b3c120b3c130b3c140b3c15b9013c16b9013c17b9013c180e3c190e3c1a0e3c1b92e7013c1c92e7013c1d92e701173c00013c01013c02013c03013c04013c05013c06013c07013c08013c09013c0a013c0e013c11013c12013c13013c15013c16013c17013c18013c19013c1a013c1b013c1c01090506b13505078a0305088501050988f3df19050a92e701050b1f05140b05158d1b051b0a88011a00001a01001a02001a03001a04001a05001a06001a07001a08001a09001a0a001a0b001a0c001a0d001a0e001a0f001a10001a11001a12001a13001a14001a15001a16001a17001a18001a19001a1a001a1b001a1c001a1d2a1a1e001a1f001a20001a21001a22001a23001a24001a25001a26001a27001a28001a29001a2aab021a2b001a2d001a2e001b00001b01001b02001b03001b04001b05001b06001b07001b08001b09001b0a001c00001c01001c02001c03001c04001c05001c06001c07001c08001c09001c0a001c0b001c0c001c0d001c100900a101a1018689020aa84109009fe0030000000c4241434b20494e2055535352920204b31f8c1b00920d970d019303170000"));
	}
}
