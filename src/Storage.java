import javax.imageio.ImageIO;
import java.awt.image.*;
import java.util.*;
import java.io.*;

public class Storage {
    static String qrFileName = "qrfile.txt";
    static File qrFile;
    static List<String> qrFiles = new ArrayList<String>();
    public int selectionCount(){return qrFiles.size();}
    public String[] selection(){
        if(qrFiles.size() == 0)
            return new String[]{"Nothing here!", "Library is empty at the moment..."};
        else{
            String[] s = new String[qrFiles.size()];
            for(int i = 0; i < s.length; i++)
                s[i] = removeFileExt(qrFiles.get(i));
            return s;
        }
    }
    static Dictionary dic = new Hashtable();

    //Square codes, so just one value
    private static final int resolution = 21;

    public Storage(){
        load();
    }

    private void load() {
        qrFile = new File(qrFileName);
        try {
            if (qrFile.createNewFile())
                Menu.print("QR File created!");
            else
                Menu.print("QR File found!");
        } catch (IOException e) { Menu.print(e); }

        try {
            Scanner scan = new Scanner(qrFile);
            while(scan.hasNextLine()){
                String s = scan.nextLine();
                if(s.endsWith(".txt")){
                    qrFiles.add(s);
                    Menu.print("Added [" + s + "] to list.");
                }
                else Menu.print("Unexpected token found in list.");
            }
            if(qrFiles.size() > 0){
                for(int i = 0; i < qrFiles.size();i++){
                    String s = qrFiles.get(i);
                    File t = new File(s);
                    if(t.exists()){
                        File p = new File(txtPngSwitch(s));
                        if(p.exists()){
                            //Picture's boolean array gives you access to log file.
                            dic.put(qrToBool(p), t);
                        }
                        else Menu.print("File [" + s + "] found, no png file.");
                    }
                    else Menu.print("File [" + s + "] not found.");
                }
            }
            Menu.print("Loading complete!");
        }
        catch (FileNotFoundException e) { Menu.print(e); }
    }

    private static String removeFileExt(String s){
        if(s.charAt(s.length() - 4) == '.')
            return s.substring(0, s.length() - 4);
        else {
            Menu.print("String not valid!");
            return null;
        }
    }

    private static String txtPngSwitch(String s) {
        boolean txt = s.endsWith(".txt");
        boolean png = s.endsWith(".png");
        s = removeFileExt(s);
        if (txt) return s + ".png";
        if(png) return s + ".txt";
        else {
            Menu.print("?");
            return null;
        }
    }

    public static boolean[][] qrToBool(File qr){
        try {
            BufferedImage img = ImageIO.read(qr);
            int scale = img.getTileHeight() / resolution;
            boolean[][] data = new boolean[resolution][];
            for(int i = 0; i < resolution; i++){
                data[i] = new boolean[resolution];
                for(int j = 0; j < resolution; j++){
                    int x = i * scale;
                    int y = j * scale;

                    int n = img.getRGB(x, y);
                    //Menu.print("Value: " + n);
                    boolean b = true;
                    if(n == -1) b = false;
                    data[i][j] = b;
                }
            }
            return data;
        }
        catch (IOException e) { Menu.print(e); }
        return null;
    }

}
