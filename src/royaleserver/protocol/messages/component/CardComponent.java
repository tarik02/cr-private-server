package royaleserver.protocol.messages.component;

import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;

public class CardComponent extends Component {

    public String[] cards;

    public CardComponent() {
    }

    public void GenerateCards()
    {

    }

    @Override
    public void encode(DataStream stream) {
        super.encode(stream);


    }
}
