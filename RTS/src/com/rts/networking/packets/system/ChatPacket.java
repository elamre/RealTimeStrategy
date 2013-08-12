package com.rts.networking.packets.system;

import com.rts.networking.packets.Packet;
import com.rts.networking.packets.PacketManager;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChatPacket extends Packet {
    String text;

    public ChatPacket() {
        super();
    }

    public ChatPacket(int connectionId, String text) {
        super(PacketManager.getPacketId(new ChatPacket()), connectionId);
        this.text = text;
    }

    public void readPacketSpecific(DataInputStream in) throws IOException {
        text = in.readUTF();
    }

    public void writePacketSpecific(DataOutputStream out) throws IOException {
        out.writeUTF(text);
    }

    public String getText() {

        return text;
    }
}
