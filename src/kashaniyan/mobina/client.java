package kashaniyan.mobina;

import java.net.*;
import java.io.*;
class client{
    public static void main(String args[])throws Exception{
        String sentences , encrypted;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket=new Socket("localhost",3333);
        Socket decryptSocket  = new Socket("localhost",4444);

        DataOutputStream outToServer=new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

        DataOutputStream outTodecryptionServer=new DataOutputStream(decryptSocket.getOutputStream());
        DataInputStream inFromdecryptionServer = new DataInputStream(decryptSocket.getInputStream());

        sentences = br.readLine();

        int sentlength = sentences.length();
        int matrixrow = 0;

        if (sentlength % 2 == 0) {
            matrixrow = sentlength / 2;
        } else {
            matrixrow = sentlength / 2 + 1;
        }
        int[][] datas = new int[matrixrow][2];

        int askii;
        int index = 0;
        char[] chars = sentences.toCharArray();
        for (int row = 0; row < matrixrow; row++) {
            for (int col = 0; col < 2; col++) {
                if (index != sentlength) {
                    askii = (int) chars[index];
                    datas[row][col] = askii;
                    index++;
                }
            }
        }

        outToServer.write(matrixrow);
        outTodecryptionServer.write(matrixrow);
        outToServer.flush();
        outTodecryptionServer.flush();
        for (int i = 0; i < matrixrow; i++) {
            for (int j = 0; j < 2; j++) {
                outToServer.write(datas[i][j]);
                outToServer.flush();
            }

        }


        int[][] encryptedMatrix =new int[matrixrow][2];
        for (int i = 0; i < matrixrow; i++) {
            for (int j = 0; j < 2; j++) {
                encryptedMatrix[i][j] = inFromServer.read();
                outTodecryptionServer.write(datas[i][j]);
                outTodecryptionServer.flush();
            }
        }
        encrypted = inFromServer.readUTF();
        System.out.print("Encrypted From Server "+encrypted);


        String decrypt = inFromdecryptionServer.readUTF();
        System.out.print("Decrypted From DecryptionServer "+decrypt);

        clientSocket.close();
        decryptSocket.close();
    }
}