//Server
import java.util.Scanner;
import java.net.*;
import java.io.*;

public class tcpserver {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = null;
        Socket s = null;
        
        try {
            ss = new ServerSocket(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        while (true) {
            try {
                System.out.println("Server Ready....");
                s = ss.accept();
                System.out.println("Client Connected...");
                
                InputStream istream = s.getInputStream();
                Scanner fread = new Scanner(new InputStreamReader(istream));
                String fileName = fread.nextLine();
                System.out.println("Reading contents of " + fileName);
                
                Scanner contentRead = new Scanner(new FileReader(fileName));
                OutputStream ostream = s.getOutputStream();
                PrintWriter pwrite = new PrintWriter(ostream, true);

                while (contentRead.hasNext()) {
                    pwrite.println(contentRead.nextLine());
                }
                pwrite.close();
                s.close();
                fread.close();
                contentRead.close();
            } 
            catch (FileNotFoundException e) {
                System.out.println("File Not Found");
                OutputStream ostream = s.getOutputStream();
                PrintWriter pwrite = new PrintWriter(ostream, true);
                System.out.println("File Not Found");
                pwrite.close();
                ss.close();
            }
        }
    }
}


//Client

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class tcpclient {
    public static void main(String[] args) {
        Socket s;
        while (true) {
            try {
                s = new Socket("localhost", 2000);
                OutputStream ostream = s.getOutputStream();
                System.out.println("Enter filename");
                Scanner input = new Scanner(System.in);
                String fname = input.nextLine();
                if(fname == "END"){
                    System.out.println("Ending...");
                    input.close();
                    System.exit(0);
                }
                PrintWriter pwrite = new PrintWriter(ostream, true);
                pwrite.println(fname);
                
                InputStream istream = s.getInputStream();
                Scanner cRead = new Scanner(new InputStreamReader(istream));
                
                if (cRead.hasNext()) {
                    while (cRead.hasNext()) {
                        System.out.println(cRead.nextLine());
                    }
                } else {
                    System.out.println("No content received from server.");
                }
                pwrite.close();
                cRead.close();
                s.close();

            } catch (Exception e) {
                System.out.println("File Reading Ended...");
                System.exit(0);
            }
        }
    }
}
