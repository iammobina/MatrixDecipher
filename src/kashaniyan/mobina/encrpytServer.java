package kashaniyan.mobina;

import java.net.*;
import java.io.*;
class encrpytServer {
    public static void main(String args[])throws Exception{
        String clientSentences;
        ServerSocket serverSocket = new ServerSocket(3333);
        System.out.println("Encryption Server has been started");

        while(true) {
            Socket socket = serverSocket.accept();
            BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());

            int nodes = inputFromClient.read();
            System.out.println("Encryption Server : Matrix recieved from client ");
            int adjMatrix[][] = new int[nodes][2];
            for (int i = 0; i < nodes; i++) {
                for (int j = 0; j < 2; j++){
                    adjMatrix[i][j] = inputFromClient.read();}
            }

            int[][] privatekey = {{1, 2}, {-1, 2}};
            int[][] multiple = new int[nodes][2];
            for (int rowI = 0; rowI < nodes; rowI++) {
                for (int colP = 0; colP < 2; colP++) {
                    for (int element = 0; element < 2; element++) {
                        multiple[rowI][colP] += adjMatrix[rowI][element] * privatekey[element][colP];
                    }
                }
            }

            StringBuilder finalSent = new StringBuilder();
            for (int i = 0; i < nodes; i++) {
                for (int j = 0; j < 2; j++) {
                    finalSent.append((char) multiple[i][j]);
                    outToClient.write(multiple[i][j]);
                    outToClient.flush();
                }
            }
            String finalText = finalSent.toString();
            outToClient.writeUTF( finalText+"\n");
            outToClient.flush();

        }
    }
}