import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChatServer extends JFrame
{
  public ChatServer() 
  {
    super("Chat Server");
	this.setSize(new Dimension(500,300)); // setto la grandezza della finestra 
	this.setLocationRelativeTo(null);     // la metto al centro dello schermo
	this.setEnabled(true);                // setto la proprietà enable
	this.setBackground(Color.blue);	      // setto il colore di sfondo
	//creo il pannello per l'inserimento e la visualizzazione dei messaggi
	PannelloChatServer pan = new PannelloChatServer();
	this.getContentPane().add(pan);
	this.setVisible(true);		
  }
}	
	
class PannelloChatServer extends JPanel implements ActionListener
{
  private List lista;
  private JTextField textNuovo;
  private ThreadGestioneServizioChat gestioneServizio;
		
  public PannelloChatServer()
  {
 	super();
	this.setBackground(new Color(50, 100, 255));	
	// pannello superiore: lista messaggi  
	JPanel panLista = new JPanel(new BorderLayout(20,5));
	panLista.setBackground(new Color(50, 100, 255));
	lista = new List();
	lista.setBackground(Color.lightGray);       
	lista.setSize(100,50);
	lista.setVisible(true);
	// scritte laterali   
	JLabel chat1 = new JLabel("  Chat  ",JLabel.CENTER);	
    chat1.setForeground(new Color(200,100,100));	  
	JLabel chat2 = new JLabel("  Chat  ",JLabel.CENTER);	
    chat2.setForeground(new Color(200,100,100));		    
	// aggiungiamo gli oggetti sul pannello 		  
	panLista.add(chat1,BorderLayout.WEST);
	panLista.add(lista,BorderLayout.CENTER);
	panLista.add(chat2,BorderLayout.EAST);
			  
	//pannello inserimento nuovo messaggio
	JPanel nuovoMex = new JPanel(new BorderLayout(20,5));
    nuovoMex.setBackground(new Color(50, 100, 255));
	  
	JLabel labNuovo = new JLabel("Nuovo Messaggio -> ",JLabel.CENTER);
	labNuovo.setForeground(new Color(255,255,0));	  
		
	textNuovo = new JTextField("");
		
	JButton buttonInvia = new JButton("Invia");			    
    buttonInvia.addActionListener(this);
	// aggiungiamo gli oggetti sul pannello 		  
    nuovoMex.add(labNuovo,BorderLayout.WEST);
	nuovoMex.add(textNuovo,BorderLayout.CENTER);
	nuovoMex.add(buttonInvia,BorderLayout.EAST); 
					
	this.setLayout(new BorderLayout(0,5));
	add(panLista,BorderLayout.CENTER);
	add(nuovoMex,BorderLayout.SOUTH);		
		
	connetti();
  }//fine costruttore classe PannelloChat
		
  public void connetti()
  {
    //instanzio il Thread per le connessioni : numero massimo giocatori = 10
	gestioneServizio = new ThreadGestioneServizioChat(10,lista);   
  }

  public void actionPerformed(ActionEvent e)
  {
 	String bottone = e.getActionCommand();
	if(bottone.equals("Invia"))
    {
  	 gestioneServizio.spedisciMessaggio(textNuovo.getText());
	 textNuovo.setText("");
    }			
  }	
}	
	

