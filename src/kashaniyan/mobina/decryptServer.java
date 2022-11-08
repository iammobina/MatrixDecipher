package kashaniyan.mobina;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class decryptServer {
    public static void main(String[] args) throws IOException {
        String decryptSentence;
        ServerSocket serverSocket = new ServerSocket(4444);
        System.out.println("Decryption Server  has been started");

        while(true) {
            Socket socket = serverSocket.accept();
            BufferedReader inputFromClient1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream outToClient1 = new DataOutputStream(socket.getOutputStream());

            int matrixrow = inputFromClient1.read();
            System.out.println("Decryption Server : Matrix recieved from client!");
            int multiple[][] = new int[matrixrow][2];
            for (int i = 0; i < matrixrow; i++) {
                for (int j = 0; j < 2; j++){
                    multiple[i][j] = inputFromClient1.read();}
            }

            int[][] privatekey = {{1, 2}, {-1, 2}};
            float det;
            int temp;
            float[][] inverse = new float[2][2];
            det = (privatekey[0][0] * privatekey[1][1]) - (privatekey[0][1] * privatekey[1][0]);
            temp = privatekey[0][0];
            privatekey[0][0] = privatekey[1][1];
            privatekey[1][1] = temp;
            privatekey[0][1] = - privatekey[0][1];
            privatekey[1][0] = - privatekey[1][0];
            for(int i = 0; i < 2; ++i) {
                for (int j = 0; j < 2; ++j)
                    inverse[i][j] = privatekey[i][j] / det;
            }
            float[][] dec = new  float[matrixrow][2];
            for (int rowI = 0; rowI < matrixrow; rowI++) {
                for (int colP = 0; colP < 2; colP++) {
                    for (int element = 0; element < 2; element++) {
                        dec[rowI][colP] +=  multiple[rowI][element] * (double) inverse[element][colP];
                    }
                }
            }
            StringBuilder decrypt = new StringBuilder();
            for (int i = 0; i < matrixrow; i++) {
                for (int j = 0; j < 2; j++) {
                    decrypt.append((char) multiple[i][j]);
                }
            }
            outToClient1.writeUTF( decrypt.toString()+"\n");
            outToClient1.flush();
        }
    }
}
