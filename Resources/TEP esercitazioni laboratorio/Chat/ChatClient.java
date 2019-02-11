import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChatClient extends JFrame
{
  public ChatClient()
  {
	super("Chat Client");
	
	//setto la grandezza della finestra 
	this.setSize(new Dimension(500,300));
	//setto la posizione della finestra. in questo modo la mette al centro dello schermo
	this.setLocationRelativeTo(null);
	//setto la proprietà enable
	this.setEnabled(true);
	//setto il colore di sfondo
	this.setBackground(Color.blue);	
	
	//instanzio il pannello grafico per l'inserimento e la visualizzazione
	//dei messaggi
	PannelloChatClient pan = new PannelloChatClient();
	
	this.getContentPane().add(pan);
	
	this.setVisible(true);			
  }	
}

class PannelloChatClient extends JPanel implements ActionListener
{
  private List lista;
  private JTextField textNuovo;
  private ThreadChatClient gestioneServizio;
  private String ipServer="localhost";
  private int porta      =6789;
  
  private int cred  =  34;
  private int cgreen= 139;
  private int cblue =  34;
		
  public PannelloChatClient()
  {
	super();
	this.setBackground(new Color(cred,cgreen,cblue));	
		  
	//pannello lista messaggi  
	JPanel panLista = new JPanel(new BorderLayout(20,5));
	panLista.setBackground(new Color(cred,cgreen,cblue));
		  
	lista = new List();
	lista.setBackground(Color.lightGray);       
	lista.setSize(100,50);
	lista.setVisible(true);
		  
	JLabel chat1 = new JLabel("   Chat ",JLabel.CENTER);	
    chat1.setForeground(new Color(144,238,144));	  
    JLabel chat2 = new JLabel(" Chat   ",JLabel.CENTER);	
    chat2.setForeground(new Color(144,238,144));		    
		  
	panLista.add(chat1,BorderLayout.WEST);
	panLista.add(lista,BorderLayout.CENTER);
	panLista.add(chat2,BorderLayout.EAST);
  
	//pannello inserimento nuovo mesaggio
	JPanel nuovoMex = new JPanel(new BorderLayout(20,5));
	nuovoMex.setBackground(new Color(cred,cgreen,cblue));
	  
	JLabel labNuovo = new JLabel("Nuovo Messaggio -> ",JLabel.CENTER);
	labNuovo.setForeground(new Color(255,255,0));		    
	
	textNuovo = new JTextField("");
	
	JButton buttonInvia = new JButton("Invia");			    
	buttonInvia.addActionListener(this);
		    
	nuovoMex.add(labNuovo,BorderLayout.WEST);
	nuovoMex.add(textNuovo,BorderLayout.CENTER);
	nuovoMex.add(buttonInvia,BorderLayout.EAST); 
				
	this.setLayout(new BorderLayout(0,5));
	add(panLista,BorderLayout.CENTER);
	add(nuovoMex,BorderLayout.SOUTH);		
	
	connettiAlServer();
  }//fine costruttore classe PannelloChat

	
  public void connettiAlServer()
  {
	//instanzio il Thread per le connessioni
	gestioneServizio = new ThreadChatClient(lista,ipServer,porta);
  }

  public void actionPerformed(ActionEvent e)
  {
	String bottone = e.getActionCommand();
			
	if(bottone.equals("Invia"))
    {
	gestioneServizio.spedisciMessaggioChat(textNuovo.getText());
	textNuovo.setText("");
    }			
  }	
} 
	