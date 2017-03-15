package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class Controller {
    public TextField text;
    public TextArea Messedger;
    public TextArea setMessedger;
    public Button ot;
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    public String name;
    public boolean e = true;


//    public void start(ActionEvent actionEvent) throws IOException {
//        Socket s = new Socket ("localhost", 3128);
//        String s1 = text.getText();
//         in = new BufferedReader(new InputStreamReader(s.getInputStream()));
//         out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
//        String fuser, fserver;
//        BufferedReader inu  = new BufferedReader
//                (new InputStreamReader(System.in));
//        while((fuser = inu.readLine())!= null){
//            out.println(fuser);
//            fserver = in.readLine();
//            System.out.println(fserver);
//            if(fuser.equalsIgnoreCase("exit chat")) break;
//            if(fuser.equalsIgnoreCase("close chat")) break;
//        }
//
//    }

    Thread thread;
    public void connect(ActionEvent actionEvent) throws IOException {
        socket = new Socket("localhost",3127);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        ot.setVisible(true);
        name = setMessedger.getText();
        setMessedger.setText(null);
        thread = new Thread(new Runnable() {
            @Override
            public void run()
            {

                while (true)
                {
                    String ms = null;
                    try {
                        while (in.ready()) {
                            ms = in.readLine();
                            Messedger.setText(  ms + "\n" + Messedger.getText());
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


//                    try {
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        });
        thread.setDaemon(true);
        if(e) {

            thread.start();
            e=false;
        }



    }

    public void buttonMessedger(ActionEvent actionEvent)
    {
        String ms = null;
        ms = setMessedger.getText();
        setMessedger.setText(null);
        Messedger.setText( name + ": " + ms +"\n"+ Messedger.getText() );
        out.println(name + ": "+ ms);
    }

    public void disConnect(ActionEvent actionEvent) throws IOException {
        out.println("killMePleasBro");
       socket=null;
        ot.setVisible(false);



    }
}
