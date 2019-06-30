import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Image;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.wb.swt.SWTResourceManager;
import openFile.*;

public class calc {
	public HttpURLConnection connection;
	protected Shell shlPingSniff;
	private Text myUrl;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			OpenFile.main(args);
			calc window = new calc();
			window.open(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open(String [] args) {
		Display display = Display.getDefault();
		createContents(args);
		//---------------------------------------------------------------
		//set Title Icon
		InputStream image = getClass().getClassLoader().getResourceAsStream("connect2.png");
		Image titleIcon = new Image(display, image);
		shlPingSniff.setImage(titleIcon); 
		//--------------------------------------------------------------
		
		shlPingSniff.open();
		shlPingSniff.layout();
	
		while (!shlPingSniff.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(String[] args) {
		boolean isOnTop = true;
		shlPingSniff = new Shell();
		shlPingSniff.setSize(352, 224);
		shlPingSniff.setText("eZ Website Pinger");
		int shellWidth = 352;
		int shellHeight = 224;
		//shlPingSniff.setVisible(true);
		//-------------------------------------------------------
		//Centers Window
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - shellWidth) / 2);
	    int y = (int) ((dimension.getHeight() - shellHeight) / 2);
	    shlPingSniff.setLocation(x, y);
	    //----------------------------------------------------
	    //----------------------------------------------------
	    //code for 'Always on Top' window
	    long handle = shlPingSniff.handle;
	    OS.SetWindowPos(handle, isOnTop ? OS.HWND_TOPMOST : OS.HWND_NOTOPMOST, x, y, shellWidth, shellHeight, 0);  
	    //--------------------------------------------------------
	    
		myUrl = new Text(shlPingSniff, SWT.BORDER);
		myUrl.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		myUrl.setToolTipText("google.com");
		myUrl.setBounds(44, 57, 246, 32);
	//	myUrl.setFocus();
		
		Label connectionStatus = new Label(shlPingSniff, SWT.NONE);
		connectionStatus.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		connectionStatus.setBounds(47, 139, 186, 30);
		connectionStatus.setText("");
		
		Label lblEnterUrl = new Label(shlPingSniff, SWT.NONE);
		lblEnterUrl.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		lblEnterUrl.setBounds(44, 33, 74, 20);
		lblEnterUrl.setText("Enter Url:");
		
		Button btnPing = new Button(shlPingSniff, SWT.NONE);
		btnPing.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String pingUrl = myUrl.getText();
				 pingUrl = "http://" + pingUrl;
				 
			 try {
				//Sniffer.main(args, pingUrl);
				String state = doHttpUrlConnectionAction(pingUrl);
				connectionStatus.setText("Status: " + state);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
		});
		btnPing.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		btnPing.setBounds(43, 95, 120, 38);
		btnPing.setText("Ping");
		//--------------------------------------------
		
		
		Button discBtn = new Button(shlPingSniff, SWT.NONE);
		discBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//TODO disconnect
				connection.disconnect();
				connectionStatus.setText("Status: disconnected");
			}
		});
		discBtn.setBounds(171, 95, 120, 38);
		discBtn.setText("Disconnect");
	}
	

public String doHttpUrlConnectionAction(String desiredUrl)
  throws Exception
  {
    URL url = null;
    try
    {
      // create the HttpURLConnection
      url = new URL(desiredUrl);
      connection = (HttpURLConnection) url.openConnection();
    
      connection.setReadTimeout(15*1000);
      
      if(connection != null)
      { 
    	  connection.connect();
    	 // currentConnection(connection);
         return "connected";
      }
     
    }
    catch (Exception e)
    {
     // e.printStackTrace();
      return "couldn't connect";
    //  throw e;

    }
    return "couldn't connect";
  }
}
