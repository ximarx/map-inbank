package gccb.cliente;

import gccb.exception.handler.ExceptionHandlerManager;
import gccb.exception.handler.GuiHandler;
import gccb.settings.SettingsManager;

import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.UIManager;

public class InBankClienteApplet extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5491504519150803547L;
	private ClienteFrameController fc;
	
	/*
	 * (non-Javadoc)
	 * @see java.applet.Applet#getParameterInfo()
	 */
	public String[][] getParameterInfo() {
	    String[][] info = {
	      // Parameter Name     Kind of Value   Description
	        {"server_host_address",     "URL",          "Corrisponde alla proprietà server.host.address"},
	        {"server_host_port",         "int",          "Corrisponde alla proprietà server.host.port"},
	        {"server_service_name",      "string",          "Corrisponde alla proprietà server.service.name"},
	        {"ui_theme",      "string",          "Corrisponde alla proprietà ui.theme"}
	    };
	    return info;
	}    	

	/*
	 * (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	@Override
	public void init() {
		ArrayList<String> keys = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		String[][] paramsInfo = getParameterInfo();
		for ( String[] param : paramsInfo ) {
			keys.add(param[0].replace("_", "."));
			values.add(getParameter(param[0]));
		}
		SettingsManager.getDefaultInstance().loadAppletPreferences(
				keys.toArray(new String[keys.size()]),
				values.toArray(new String[values.size()]));
		try {
			UIManager.setLookAndFeel(SettingsManager.getDefaultInstance().get("ui.theme").trim());
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch ( Exception innere) {
				innere.printStackTrace();
			}
		} 
		
		fc =  new ClienteFrameController();
		ExceptionHandlerManager.registerNewDefaultEH(new GuiHandler(fc));
		fc.showMainFrame(false);
		//setLayout(new BorderLayout());
		//add(((YFrame) fc.getView()).getContentPane(), BorderLayout.CENTER);
	}
	
	@Override
	public void destroy() {
		fc = null;
	}
}
