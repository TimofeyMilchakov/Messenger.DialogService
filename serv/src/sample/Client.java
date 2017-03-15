package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by tttt on 13.10.2016.
 */
public class Client
{
    public Socket socket;
    public BufferedReader in;
    public PrintWriter out;
    public String mes;
    public boolean checkMes;
    public Client(Socket s) throws IOException
    {
        checkMes = false;
        mes = null;
        this.socket=s;
        in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        out = new PrintWriter(this.socket.getOutputStream(), true);
    }
}
