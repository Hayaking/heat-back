package com.example.botheat.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author haya
 */
public class SocketClient {

    private ExecutorService pool = Executors.newSingleThreadExecutor();
    private String gprsStr =
            "13612340006,13612340007,13612340008,13612340009,13612340010,13612340011,13612340012,13612340013,13612340014,13612340015,13612340016,13612340017,13612340018,13612340019,13612340020,13612340021,13612340022,13612340023,13612340024,13612340025,13612340026,13903714072";

    public SocketClient() {
        for (String phone : gprsStr.split( "," )) {
            pool.execute( new IncomingReader( phone ) );
        }
    }

    public static void main(String[] args) {
        new SocketClient();
    }

    public static class IncomingReader implements Runnable {
        private DataInputStream input;
        private DataOutputStream output;
        private Socket socket;
		//发送手机号码
        private byte[] initData = new byte[15];
        private byte[] inData = new byte[8];
        private byte[] outData;

        public IncomingReader(String phone) {
            byte[] header = HexUtil.stringToBytes( "FF0D0901" );
            System.arraycopy( header, 0, initData, 0, header.length );
            byte[] phoneByte = phone.getBytes();
            System.arraycopy( phoneByte, 0, initData, 4, phoneByte.length );
            try {
                socket = new Socket( "127.0.0.1", 5005 );
                input = new DataInputStream( socket.getInputStream() );
                output = new DataOutputStream( socket.getOutputStream() );
                output.write( initData, 0, initData.length );
                output.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    input.read( inData );
                    String inStr = HexUtil.bytesToString( inData );
                    System.out.println( inStr );
                    initOutData( inStr.substring( 0, 2 ) );
                    //if(HexUtil.bytesToString(inData).equals("0A030000001444BE")){
                    output.write( outData, 0, outData.length );
                    output.flush();
                    //}
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        private void initOutData(String addr) {
            outData = HexUtil.stringToBytes(
                    addr + "0328" +
                            HexUtil.floatToHexStr( (float) (Math.random() * 20) ) +  //流量
                            HexUtil.floatToHexStr( (float) (Math.random() * 300) ) + //温度
                            HexUtil.floatToHexStr( (float) (Math.random() * 1.6) ) + //压力
                            "228A0084" + "2ADBFAFF" + "EF17EBA7" + "BAF5BE8E" +
                            "4000454E" + "80004509" + "80004489" +  //1100,1200,1300(日,月,总)
                            "0000"
            );
            String crc = HexUtil.crc16( Arrays.copyOf( outData, 43 ) );
            //CRC-L
            outData[43] = (byte) Integer.parseInt( crc.substring( 2 ), 16 );
            //CRC-H
            outData[44] = (byte) Integer.parseInt( crc.substring( 0, 2 ), 16 );
        }
    }

}
