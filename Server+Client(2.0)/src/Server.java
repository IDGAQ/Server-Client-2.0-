import java.io.*;
import java.net.*;
import java.util.*;

// 用Fixed ThreadPool Runs register() and login()
public class Server{
    public static void main(String[] args) {
        int choice = 0;

        // the System that stores <Username, Password>
        HashMap<String, String> theSystem = new HashMap<>();
        theSystem.put("admin", "123");
        theSystem.put("user", "abc");
        Serialize(theSystem,"/Users/lukelu/IdeaProjects/Server+Client(2.0)/src/theSystem.txt");

        // Deserialize from theSystem.txt
        Deserialize(theSystem, "/Users/lukelu/IdeaProjects/Server+Client(2.0)/src/theSystem.txt");


        register(theSystem);
        // multi-thread pool for all ports
        login(theSystem);

    }

    static void register(HashMap<String, String> theSystem){
        try {
            ServerSocket server = new ServerSocket(7777);
            System.out.println("Register Server is Ready, Waiting for User...");

            // Means User is Connected
            Socket socket = server.accept();
            System.out.println("One User is Connected! " + socket.getInetAddress().toString());

            // Listen1! get username
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String username = br.readLine();
            // Listen2! get password
            String password = br.readLine();
            System.out.println("Server Recieved: " + username + " " + password);

            OutputStream os = socket.getOutputStream();
            PrintStream ps = new PrintStream(os);

            if (theSystem.containsKey(username)) {
                // talk 1! username exist!
                ps.println("Username Exist Already!");
            } else {
                theSystem.put(username, password);
                //Serialize HashMap theSystem to theSystem.txt
                Serialize(theSystem, "/Users/lukelu/IdeaProjects/Server+Client(2.0)/src/theSystem.txt");
                // talk 1! password exist!
                ps.println("Registered Successful!");
            }


        } catch (Exception e) {
            System.out.println("Error Detected");
        }
    }


    static void login(HashMap<String, String> theSystem) {
        try {
            ServerSocket server = new ServerSocket(8888);
            System.out.println("Login Server is Ready, Waiting for User...");

            // Means User is Connected
            Socket socket = server.accept();
            System.out.println("One User is Connected! " + socket.getInetAddress().toString());

            // Talk! (1)
            OutputStream os = socket.getOutputStream();
            PrintStream ps = new PrintStream(os);
            ps.println("Welcome!");

            // Listen! (2)
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String text = br.readLine();
            System.out.println("Server Recieved: " + text);

            // 1: admin, 2: user
            int type = 0;

            String thePassword;

            // Talk! (2)
            ps.println("Enter Username, ");

            // while loop ( get name and store password)
            while (true) {
                // small read 1
                String username = br.readLine();
                System.out.print("Username Recieved: " + username + "\n");
                if (theSystem.containsKey(username)) {
                    thePassword = theSystem.get(username);
                    // small talk 1
                    ps.println("Username Found");
                    break;
                } else {
                    // small talk 1
                    ps.println("Username Not Found, Please Try Again");
                }
            }

            // small talk 2
            ps.println("Enter Password, ");

            // small read 2
            String password = br.readLine();
            System.out.print("Password Recieved: " + password + "\n");
            if (password.equals(thePassword)) {
                // small talk 3
                ps.println("Logged In Successful!");
            } else {
                // small talk 3
                ps.println("Wrong Password");
            }


            System.out.println("Server is Disconnected");
        } catch (Exception e) {
            System.out.println("Error Detected");
        }

    }

    static void Serialize(HashMap<String, String> map, String filename) {
        // Serialize
        try {
            MySerializeUtil.mySerialize(map, filename);
            System.out.println("Serialized Succsessful");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void Deserialize(HashMap<String, String> map, String filename) {
        // Deserialize
        try {
            Object obj = MySerializeUtil.myDeserialize(filename);
            if (obj instanceof HashMap) {
                HashMap<String, String> newSystem = (HashMap<String, String>) obj;
                System.out.println("Deserialized Succsessful\n");
                System.out.println("theSystem:");
                for (Map.Entry<String, String> StringmyExpressEntry : newSystem.entrySet()) {
                    System.out.print("Username: "+ StringmyExpressEntry.getKey()+" ");
                    System.out.print("Password: "+ StringmyExpressEntry.getValue()+" ");
                    System.out.println("\n");
                }
                map = newSystem;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }
    }
}
