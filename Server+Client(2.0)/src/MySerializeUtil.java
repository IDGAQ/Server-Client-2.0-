import java.io.*;
import java.util.*;

public class MySerializeUtil{

    /**
     *
     */
    public static void mySerialize(Object obj, String fileName) throws IOException{

        OutputStream out = new FileOutputStream(fileName);
        ObjectOutputStream objOut = new ObjectOutputStream(out);
        objOut.writeObject(obj);
        objOut.close();
        out.close();


    }

    /**
     *
     */
    public static Object myDeserialize(String fileName) throws IOException, ClassNotFoundException{

        InputStream in = new FileInputStream(fileName);
        ObjectInputStream objIn = new ObjectInputStream(in);
        Object obj = objIn.readObject();
        objIn.close();
        in.close();
        return obj;

    }


}