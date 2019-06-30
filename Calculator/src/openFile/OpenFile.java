//import java.awt.Desktop;
package openFile;
import java.io.*;
//import java.lang.*;
public class OpenFile {
  // TODO Auto-generated method stub
   public static void main(String[] args) throws IOException {
     //text file, should be opening in default text editor
	  try {
          Runtime rt = Runtime.getRuntime();

          String command = "cmd /c cd c:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs & Wireshark.lnk -i Wi-Fi -k";
         // Process pr = rt.exec(command);
          rt.exec(command);
        } catch(Exception e) {
            System.out.println("error");
        e.printStackTrace();
     }
   }
}

