import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) {

        display();
    }

    static void display() {
        int Input = 0;

        while(Input!=3) {
            System.out.println("Enter 1 to Register, 2 to Log in, other key to Quit");
            Scanner input = new Scanner(System.in);

            Input = input.nextInt();

            if (Input == 1) {
                register();
            } else if (Input == 2) {
                login();
            } else {
                Input = 3;
            }
        }
    }

    static void register() {

        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter Your Username");
            String theUsername = input.nextLine();
            System.out.println("Ente Your Password");
            String thePassword = input.nextLine();

            try {
                Socket socket = new Socket("127.0.0.1", 7777);
                //send send

                // Talk! (1)
                OutputStream os = socket.getOutputStream();
                PrintStream ps = new PrintStream(os);
                ps.println(theUsername);
                // Talk! (1)
                ps.println(thePassword);

                // Listen! (1)
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String text = br.readLine();
                System.out.println(text+"\n");


            } catch (Exception e) {
                System.out.println("Error Detected");
            }

            break;
        }
    }

    static void login() {
        try {
            //login socket
            Socket socket = new Socket("127.0.0.1", 8888);


            // Listen! (1)
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String text = br.readLine();

            System.out.print("User Recieved: " + text + "\n");

            // Talk! (2)
            OutputStream os = socket.getOutputStream();
            PrintStream ps = new PrintStream(os);
            ps.println("Hello Server!");

            //Listen(2)
            System.out.print(br.readLine());

            //while loop to input
            Scanner input = new Scanner(System.in);

            while (true) {
                System.out.println("Input Here: ");
                String theInput = input.nextLine();
                // small send 1
                ps.println(theInput);

                //small read 1
                String text2 = br.readLine();
                System.out.println(text2);
                if (text2.equals("Username Found")) break;
            }

            //small read 2
            System.out.print(br.readLine());

            System.out.println("Input Here: ");
            String theInput2 = input.nextLine();
            //small talk 2
            ps.println(theInput2);

            //small read 3
            System.out.print(br.readLine());


            System.out.print("User is Disconnected");
        } catch (Exception e) {
            System.out.println("Error Detected");
        }
    }
}