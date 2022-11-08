package kashaniyan.mobina;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputdata = br.readLine();
        int sentlength = br.readLine().length();
        int matrixrow = 0;

        if (sentlength % 2 == 0) {
            matrixrow = sentlength / 2;
        } else {
            matrixrow = sentlength / 2 + 1;
        }
        int[][] datas = new int[matrixrow][2];

        int askii;
        int index = 0;
        char[] chars = inputdata.toCharArray();
        for (int row = 0; row < matrixrow; row++) {
            for (int col = 0; col < 2; col++) {
                if (index != sentlength) {
                    askii = (int) chars[index];
                    datas[row][col] = askii;
                    index++;
                }
            }
        }

        int[][] privatekey = {{1, 2}, {-1, 2}};
        int[][] multiple = new int[matrixrow][2];
        for (int rowI = 0; rowI < matrixrow; rowI++) {
            for (int colP = 0; colP < 2; colP++) {
                for (int element = 0; element < 2; element++) {
                    multiple[rowI][colP] += datas[rowI][element] * privatekey[element][colP];
                }
            }
        }

        StringBuilder finalSent = new StringBuilder();
        for (int i = 0; i < matrixrow; i++) {
            for (int j = 0; j < 2; j++) {
                finalSent.append((char) multiple[i][j]);
            }
        }
        System.out.println("THE ENCRYPTED TEXT IS :"+finalSent);

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
        String decrypt ="";
        for (int i = 0; i < matrixrow; i++) {
            for (int j = 0; j < 2; j++) {
                decrypt += (char) dec[i][j];
            }
        }
        System.out.println("THE DECRYPTED TEXT IS (USING PRIVATE KEY) :"+decrypt);


        if(matrixrow == 2) {
            System.out.println("YOU CAN DECRYPT MESSAGE");
            float deter;
            int temps;
            float[][] invert = new float[2][2];
            deter = (datas[0][0] * datas[1][1]) - (datas[0][1] * datas[1][0]);
            temps = datas[0][0];
            datas[0][0] = datas[1][1];
            datas[1][1] = temps;
            datas[0][1] = -datas[0][1];
            datas[1][0] = -datas[1][0];
            for (int i = 0; i < 2; ++i) {
                for (int j = 0; j < 2; ++j)
                    invert[i][j] = datas[i][j] / deter;
            }


            float[][] decr = new float[matrixrow][2];
            for (int rowI = 0; rowI < matrixrow; rowI++) {
                for (int colP = 0; colP < 2; colP++) {
                    for (int element = 0; element < 2; element++) {
                        decr[rowI][colP] += invert[rowI][element] * (double) multiple[element][colP];
                    }
                }
            }
            for (int i = 0; i <matrixrow ; i++) {
                for (int j = 0; j <2 ; j++) {
                    System.out.println("PRIVATE KEY "+"At MATRIX ELEMENT "+i+j+" IS :"+(int) Math.round(decr[i][j]));
                }
            }
            String decrypt1 = "";
            for (int i = 0; i < matrixrow; i++) {
                for (int j = 0; j < 2; j++) {
                    decrypt1 += (char) dec[i][j];
                }
            }
            System.out.println("THE DECRYPTED TEXT IS (WITHOUT USING PRIVATE KEY) :" + decrypt1);
        }
    }
}