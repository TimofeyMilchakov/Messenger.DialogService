package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Controller {


    public TextArea te;
    public TextArea controll;
    public ArrayList<Client> clients = new ArrayList<>();
    ServerSocket serverSocket;


//    public void start(ActionEvent actionEvent) throws IOException {
//        ServerSocket server = new ServerSocket (3128,0, InetAddress.getByName("localhost"));
//        while (true)
//        {
//            Socket socket = server.accept();
//            Socket socket1 = server.accept();
//            Integer s = socket1.getPort();
//            text.setText(s.toString() + " ���� ���������");
//            BufferedReader in = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
//            PrintWriter out = new PrintWriter(socket1.getOutputStream(), true);
//            String input;
//            while(true)
//            {
//                if((input = in.readLine())!=null)
//                {
//                    out.println("cl: "+input);
//                    System.out.println("S: "+input);
//                    text2.setText(input);
//                }
//                else
//                {
//                    break;
//                }
//
//            }
//            socket1.close();
//            break;
//
//        }
//        server.close();
//
//    }

    public void creat(ActionEvent actionEvent) throws IOException {
        serverSocket = new ServerSocket(3127, 0, InetAddress.getByName("localhost"));
//        cl1=serverSocket.accept();
//        cl2=serverSocket.accept();
//        te.setText(String.valueOf(cl1.getLocalPort()));
//        te.setText(te.getText() + "\n" +String.valueOf(cl2.getLocalPort()));
//        BufferedReader in1 = new BufferedReader(new InputStreamReader(cl1.getInputStream()));
//        PrintWriter out1 = new PrintWriter(cl1.getOutputStream(), true);
//        BufferedReader in2 = new BufferedReader(new InputStreamReader(cl2.getInputStream()));
//        PrintWriter out2 = new PrintWriter(cl2.getOutputStream(), true);

        Thread myMesseger = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    for (int i = 0; i < clients.size(); i++)
                    {
                        boolean delet = false;
                     try {
                         String outIn = null;
                         while (clients.get(i).in.ready())
                         {
                             boolean check = true;
                             outIn = clients.get(i).in.readLine();
                             if (outIn.startsWith("killMePleasBro"))
                             {
                                 delet=true;
                                 te.setText(te.getText() + "\n ------------------ \n");
                                 break;

                             }
                             controll.setText(outIn + "\n" + controll.getText());
                             for (int j = 0; j < clients.size(); j++)
                             {
                                 if (j != i)
                                 {
                                     clients.get(j).out.println(outIn);
                                     check=false;
                                     if(clients.get(i).checkMes)
                                     {
                                         clients.get(j).out.println(clients.get(i).mes);
                                         clients.get(i).mes=null;
                                     }
                                 }
                             }
                             if(check)
                             {
                                 clients.get(i).mes= clients.get(i).mes+ "\n" +outIn;
                             }
                              clients.get(i).checkMes=check;
                         }
                         if(delet)
                         {
                             clients.remove(i);
                             for (int k = 0; k<clients.size();k++) {
                                 te.setText(te.getText() + "\n" + String.valueOf(clients.get(k).socket.getLocalPort()));
                             }
                         }

                     }
                     catch (IOException e)
                     {
                         e.printStackTrace();
                     }
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        myMesseger.setDaemon(true);
        myMesseger.start();
        getClients = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    if(clients.size()<2)
                    {
                        Socket s1;
                        Client c1;
                        try {
                            s1 = serverSocket.accept();
                            c1 = new Client(s1);
                            clients.add(c1);
                            te.setText(te.getText() + "\n" + String.valueOf(c1.socket.getLocalPort()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        getClients.setDaemon(true);
        getClients.start();
    }
    Thread getClients;
    public void destroi(ActionEvent actionEvent) throws IOException
    {
        getClients.stop();
        serverSocket.close();

    }
}
