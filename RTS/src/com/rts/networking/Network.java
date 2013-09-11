package com.rts.networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.rts.networking.packets.*;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/11/13
 * Time: 8:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class Network {
    // This registers objects that are going to be sent over the network.
    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(ChatMessage.class);
        kryo.register(EntityCreation.class);
        kryo.register(EntityList.class);
        kryo.register(EntityPosChange.class);
        kryo.register(EntityRequest.class);
        kryo.register(MapTransfer.class);
        kryo.register(PlayerConnected.class);
    }
}
