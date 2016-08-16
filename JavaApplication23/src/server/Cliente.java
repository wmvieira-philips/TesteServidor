package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Cliente {

    public static void main(String[] args) {
        try {
            Socket s = new Socket("10.125.1.32", 1234);

            
            
            InputStreamReader p = new InputStreamReader(System.in);
            BufferedReader red = new BufferedReader(p);

            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            String txt = red.readLine();

            while (txt != null && !txt.isEmpty()) {
                out.println(txt);
                txt = red.readLine();
            }
            red.close();
            p.close();
            out.close();
            s.close();
        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }
}
