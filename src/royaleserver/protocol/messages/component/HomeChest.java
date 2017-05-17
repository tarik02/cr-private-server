package royaleserver.protocol.messages.component;

import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;
import royaleserver.utils.SCID;

public class HomeChest extends Component {
    public static final byte STATUS_STATIC = 0;
    public static final byte STATUS_OPENED = 1;
    public static final byte STATUS_OPENING = 8;

    public static final SCID CHEST_SUPER_MAGICAL_TRAINING_CAMP = new SCID(19, 45);

    public boolean first;
    public SCID chestID;
    public int slot; // from 1
    public byte status;
    public int ticksToOpen; // Remains ticks to open
    public int openTicks; // Ticks to open from zero

    public HomeChest() {
        first = false;
        chestID = CHEST_SUPER_MAGICAL_TRAINING_CAMP;
        slot = 1;
        status = STATUS_STATIC;
        ticksToOpen = 0;
        openTicks = 0;
    }

    @Override
    public void encode(DataStream stream) {
        super.encode(stream);

        // means, that now will chest
        stream.putRrsInt32(0);
        stream.putRrsInt32(4);

        // startChest place?
        if (first)
            stream.putRrsInt32(1);

        stream.putSCID(chestID);

        stream.putRrsInt32(status);
        if (status == STATUS_OPENING) {
            stream.putRrsInt32(ticksToOpen);
            stream.putRrsInt32(openTicks);

            // timestamp
            stream.putRrsInt32((int)System.currentTimeMillis());
        }

        stream.putRrsInt32(slot);

        stream.putRrsInt32(1);
        stream.putRrsInt32(slot - 1);
        stream.putRrsInt32(0);
    }

    @Override
    public void decode(DataStream stream) {
        super.decode(stream);
    }
}

