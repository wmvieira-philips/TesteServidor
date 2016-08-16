package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Servidor {

    private Socket cliente;
    private ServerSocket servidor;
    private HashMap<String, BufferedReader> map = new HashMap<String, BufferedReader>();
    private String remetente;

    public void ConectaServidor() throws IOException {
        try {
            servidor = new ServerSocket(1234);
            System.out.println("Servidor ouvindo a porta 1234");

            while (true) {
                cliente = servidor.accept();
                remetente = cliente.getInetAddress().getHostAddress();
                System.out.println("Cliente conectado: " + remetente);
                map.put(remetente, new BufferedReader(new InputStreamReader(cliente.getInputStream())));
                recebeMensagem(remetente);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void recebeMensagem(String i) {

        Thread p = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String mensagem = map.get(remetente).readLine();
                    while (mensagem != null && !mensagem.isEmpty()) {
                        System.err.println(remetente + " diz: " + mensagem);
                        mensagem = map.get(remetente).readLine();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        p.start();
    }

    public static void main(String[] args) throws IOException {
        Servidor servidor1 = new Servidor();
    }
}
