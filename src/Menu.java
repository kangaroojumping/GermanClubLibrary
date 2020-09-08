import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    public static boolean debug = true;
    public static boolean inUse = false;
    public static void print(String msg){ if(debug || inUse) System.out.println(msg); }
    public static void print(String[] msgs){ for(String s : msgs) print(s); }
    public static void print(){print("");}
    public static void print(Exception e){ print(e.toString()); }
    public static void printNumbered(String[] msgs){
        for(int i = 0; i < msgs.length; i++)
            msgs[i] = "#" + (i + 1) + ": " + msgs[i];
        print(msgs);
    }

    public static void main(String[] args){
        inUse = true;
        defaultFunction();
    }

    private static void defaultFunction(){
        Storage storage = new Storage();
        String[] selection = storage.selection();
        boolean empty = storage.selectionCount() == 0;
        print();
        print("Welcome to the German Club Library!");
        print("This is our selection at the moment:");
        print("~");
        if(empty)
            print(selection);
        else
            printNumbered(selection);
        print("~");
        print();
        if(!empty){
            print("Please make a selection, using the index number of the entry you are interested in.");
            Scanner console = new Scanner(System.in);
            int n = -1;
            while(n < 0){
                try{
                    n = console.nextInt();
                    if(n < 1 || n > storage.selectionCount()){
                        print("Select a title that exists, please.");
                        n = -1;
                    }
                }
                catch(InputMismatchException e) {print("Type just a number, please.");}
            }
            n--;
            print("You've chosen: " + storage.selection()[n]);
        }
        else{
            print("Welp. I guess there's nothing here for you.");
            print("Goodbye.");
        }
    }

    private static void testQR(){
        File testFile = new File("testFile.png");
        if(testFile.exists()){
            var data = Storage.qrToBool(testFile);

            for(int i = 0; i < data.length; i++)
                for(int j = 0; j < data[i].length; j++)
                    print("[" + i + ", " + j + "]: " + data[i][j]);

        }
        else print("File not found.");
    }
}
