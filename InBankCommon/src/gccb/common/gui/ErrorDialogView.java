package gccb.common.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * La finestra utilizzata per visualizzare gli errori
 * @author Francesco Capozzo
 *
 */
public class ErrorDialogView extends JDialog {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 3888981420098223246L;
	private static int result_;
	public final static int OK = 0;
	public final static int STACK = 1;
	
	private static Exception e_;

    /**
     * Crea una nuova finestra di errore impostando il messaggio di errore
     * @param s il messaggio di errore da visualizzare
     * @param stack indica se attivare o meno il pulsante per la visualizzazione dello
     * stacktrace
     */
    private ErrorDialogView(String s, boolean stack) {
        initComponents();    
        jTextArea.setText(s);
        stackButton.setVisible(stack);
        setModal(true);
        setLocationRelativeTo(null);
    }
    
    /**
     * Visualizza il messaggio di errore 
     * @param e l'eccezione che verrà visualizzata
     * @return il pulsante di errore
     */
    public static int showError(Exception e)
    {
    	result_ = 0;
    	e_ = e;
    	new ErrorDialogView(e.toString(), true).setVisible(true);
    	return result_;
    }
 
    /**
     * Visualizza un messaggio di notifica (non viene attivato lo stacktrace)
     * @param text il messaggio da visualizzare
     * @return il pulsante cliccato
     */
    public static int showNotice(String text)
    {
    	result_ = 0;
    	new ErrorDialogView(text, false).setVisible(true);
    	return result_;
    }
    
    private void initComponents()
    {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Errore");
        this.setSize(165, 300);
        this.setContentPane(getJPanel());
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                exitForm(evt);
            }
        });
        pack();
    }
    
    private void exitForm(java.awt.event.WindowEvent evt) {
        this.dispose();
    }
    
 
    
    private JPanel jPanel = null;
	private JScrollPane jScrollPane = null;
	private JTextArea jTextArea = null;
	private JPanel buttonPanel = null;
	private JButton okButton = null;
	private JButton stackButton = null;
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;  // Generated
			gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;  // Generated
			gridBagConstraints1.gridy = 1;  // Generated
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;  // Generated
			gridBagConstraints.gridy = 0;  // Generated
			gridBagConstraints.weightx = 1.0;  // Generated
			gridBagConstraints.weighty = 1.0;  // Generated
			gridBagConstraints.insets = new java.awt.Insets(4,4,4,4);  // Generated
			gridBagConstraints.gridx = 0;  // Generated
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());  // Generated
			jPanel.add(getJScrollPane(), gridBagConstraints);  // Generated
			jPanel.add(getButtonPanel(), gridBagConstraints1);  // Generated
		}
		return jPanel;
	}
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setPreferredSize(new java.awt.Dimension(600,200));  // Generated
			jScrollPane.setViewportView(getJTextArea());  // Generated
		}
		return jScrollPane;
	}
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setEditable(false);  // Generated
			jTextArea.setLineWrap(true);  // Generated
		}
		return jTextArea;
	}
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getOkButton(), null);  // Generated
			buttonPanel.add(getStackButton(), null);  // Generated
		}
		return buttonPanel;
	}
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText("Ok");
			okButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					doOk();
				}
			});
		}
		return okButton;
	}
	private void doOk()	{
		result_ = OK;
		this.dispose();
	}
	private JButton getStackButton() {
		if (stackButton == null) {
			stackButton = new JButton();
			stackButton.setText("Mostra Stacktrace");
			stackButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					doStack();
				}
			});
		}
		return stackButton;
	}

	private void doStack()	{
		result_ = STACK;
		// mostra lo stacktrace
        java.io.ByteArrayOutputStream bao = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(bao);
        e_.printStackTrace(ps);
        ErrorDialogView.showNotice(bao.toString());
	}
}