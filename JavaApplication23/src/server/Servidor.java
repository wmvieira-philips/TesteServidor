package server;

import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

public final class Servidor {

    private Socket cliente;
    private ServerSocket servidor;
    private HashMap<String, BufferedReader> map = new HashMap<String, BufferedReader>();
    private String remetente;
    private JTextArea text;

    public Servidor(JTextArea t) {
        this.text = t;
    }
    
    
    public void conectaServidor() {
        try {
            servidor = new ServerSocket(1234);
            text.setText("Servidor ouvindo a porta 1234");

            while (true) {
                cliente = servidor.accept();
                remetente = cliente.getInetAddress().getHostAddress();
                text.setText(text.getText() + "\nCliente conectado: " + remetente);
                
                map.put(remetente, new BufferedReader(new InputStreamReader(cliente.getInputStream())));
                recebeMensagem(remetente);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void recebeMensagem(String i) {

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
}
