/*  Shriya Kagolanu
 * 11th grade Paley
 *  Stanford Nifty Fractal Sound
 *  http://nifty.stanford.edu/2015/matthews-raymond-packet-sniffing/
 *  PAcketSniffer to parse a pcap file that captured the packets sniffed in a network
 * The out put file shows that if the site is not encrypted, people can sniff
 * the entire content of what a person is accessing on their device in a network. If the URLs are ecrypted
 * then the sniffer can only see TCP/UDP header data like source port and IP aheaders and not the actual content of the page
 * If the URL is not encrypted sniffer can see passwords, full content of the page you are browsing even though you
 * are password protected on the site you are browsing
 * The output file shows the content of sniffed data, for both encrypted and unencrypted URLs
 *
 *
 */

import io.pkts.PacketHandler;
import io.pkts.Pcap;
import io.pkts.buffer.Buffer;
import io.pkts.packet.Packet;
import io.pkts.packet.TCPPacket;
import io.pkts.packet.UDPPacket;
import io.pkts.protocol.Protocol;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {


        Pcap pcap = Pcap.openStream("cyber_lab.pcap");

        pcap.loop(new PacketHandler() {
            public boolean nextPacket(Packet packet) throws IOException {
                //TCP is transmission control and point to point and guaranteed packet delivery
                if (packet.hasProtocol(Protocol.TCP)) {

                    TCPPacket tcpPacket = (TCPPacket) packet.getPacket(Protocol.TCP);

                    int dstport = tcpPacket.getDestinationPort();
                    int srcport = tcpPacket.getSourcePort();

                    Buffer buffer = tcpPacket.getPayload();
                    if (buffer != null) {
                        System.out.println("Destination Port: " + dstport + "TCP Source Port " + srcport);
                        System.out.println("TCP: " + buffer);


                    }
                } else if (packet.hasProtocol(Protocol.UDP)) {

                    //user data gram -- unguaranteed
                    UDPPacket udpPacket = (UDPPacket) packet.getPacket(Protocol.UDP);
                    Buffer buffer = udpPacket.getPayload();
                    if (buffer != null) {

                        int dstport = udpPacket.getDestinationPort();
                        int srcport = udpPacket.getSourcePort();
                        System.out.println("Destination Port: " + dstport + "UDP Source Port " + srcport);
                        System.out.println("UDP: " + buffer);

                    }
                }
                return true;
            }
        });
    }


}
