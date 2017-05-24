package socket;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class TicTacToeClient extends JApplet implements Runnable, TicTacToeConstants {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("TicTacToe");
		
		TicTacToeClient applet = new TicTacToeClient();
		
		frame.add(applet, BorderLayout.CENTER);
		
		applet.init();
		applet.start();
		
		frame.pack();
		frame.setVisible(true);
	}
	
	// Indicate whether the player has the turn
	private boolean myTurn =  false;
	
	// Indicate the token for the player
	private char myToken = ' ';
	
	// Indicate the token for the other player
	private char otherToken = ' ';
	
	// Create and initialize cells
	private Cell [][] cell = new Cell[3][3];
	
	// Create and initialize a title label
	private JLabel jlblTitle = new JLabel();
	
	// Create and initialize a status label
	private JLabel jlblStatus = new JLabel();
	
	// Indicate selected row and column by the current move
	private int rowSelected;
	private int columnSelected;
	
	// Input and output streams from/to server
	private DataInputStream fromServer;
	private DataOutputStream toServer;
	
	// Continue to play
	private boolean continueToPlay = true;
	
	// Wait for the player to mark a cell
	private boolean waiting = true;
	
	// Host name or ip
	private String host = "localhost";
	
	/** Initialize UI */
	public void init() {
		// Panel p to hold cells
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(3, 3, 0, 0));
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				p.add(cell[i][j] = new Cell(i, j));
		
		// Set properties for labels and borders for labels and panel
		p.setBorder(new LineBorder(Color.black, 1));
		jlblTitle.setHorizontalAlignment(JLabel.CENTER);
		jlblTitle.setFont(new Font("SanSerif", Font.BOLD, 16));
		jlblTitle.setBorder(new LineBorder(Color.black, 1));
		jlblStatus.setBorder(new LineBorder(Color.black, 1));
		
		// Place the panel and labels to the applet
		add(jlblTitle, BorderLayout.NORTH);
		add(p, BorderLayout.CENTER);
		add(jlblStatus, BorderLayout.SOUTH);
		
		// Connect to the server
		connectToServer();
	}
	
	private void connectToServer() {
		try {
			// Create a socket to connect to the server
			Socket socket = new Socket(host, 8000);
			
			fromServer = new DataInputStream(socket.getInputStream());
			toServer = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			System.err.println(e);
		}
		
		// Control the game on a separate thread
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		try {
			// Get notification from the server
			int player = fromServer.readInt();
			
			// Am I player 1 or 2?
			if (player == PLAYER1) {
				myToken = 'X';
				otherToken = '0';
				jlblTitle.setText("Player 1 with token 'X'");
				jlblStatus.setText("Waiting for player 2 to join");
				
				// Receive startup notification from the server
				fromServer.readInt(); // Whatever read is ignoredj
				
				// The other player has joined
				jlblStatus.setText("Player 2 has joined. I start first");
				
				// It is my turn
				myTurn = true;
			}
			else if (player == PLAYER2) {
				myToken = '0';
				otherToken = 'X';
				jlblTitle.setText("Player 2 with token '0'");
				jlblStatus.setText("Waiting for player 1 to move");
			}
			
			// Continue to play
			while (continueToPlay) {
				if (player == PLAYER1) {
					waitForPlayerAction(); // Wait for player 1 to move
					sendMove(); // Send the move to the server
					receiveInfoFromServer(); // Receive info from the server
				}
				else if (player == PLAYER2) {
					receiveInfoFromServer(); // Receive info from the server
					waitForPlayerAction(); // Wait for player 2 to move
					sendMove(); // Send player 2's move to the server
				}
			}
		} catch (Exception e) {
		}
	}
	
	/** Wait for the player to mark a cell */
	private void waitForPlayerAction() throws InterruptedException {
		while (waiting) {
			Thread.sleep(100);
		}
		waiting = true;
	}
	
	/** Send this player's move to the server */
	private void sendMove() throws IOException {
		toServer.writeInt(rowSelected); // Send the selected row
		toServer.writeInt(columnSelected); // Send the selected column
	}
	
	/** Receive info from the server */
	private void receiveInfoFromServer() throws IOException {
		// Receive game status
		int status = fromServer.readInt();
		
		if (status == PLAYER1_WON) {
			// Player 1 won, stop playing
			continueToPlay = false;
			if (myToken == 'X') {
				jlblStatus.setText("I won! (X)");
			}
			else if (myToken == '0') {
				jlblStatus.setText("Player 1 (X) has won!");
				receiveMove();
			}
		}
		else if (status == PLAYER2_WON) {
			// Player 2 won, stop playing
			continueToPlay = false;
			if (myToken == '0') {
				jlblStatus.setText("I won! (0)");
			}
			else if (myToken == 'X') {
				jlblStatus.setText("Player 2 (0) has won!");
				receiveMove();
			}
		}
		else if (status == DRAW) {
			continueToPlay = false;
			jlblStatus.setText("Game is over, no winner!");
			
			if (myToken == '0') receiveMove();
		}
		else {
			receiveMove();
			jlblStatus.setText("My turn");
			myTurn = true;
		}
	}
	
	private void receiveMove() throws IOException {
		int row = fromServer.readInt();
		int column = fromServer.readInt();
		cell[row][column].setToken(otherToken);
	}
	
	public class Cell extends JPanel {
		// Indicate the row and column of this cell in the board
		private int row;
		private int column;
		
		// Token used for this cell
		private char token = ' ';
		
		public Cell(int row, int column) {
			this.row = row;
			this.column = column;
			setBorder(new LineBorder(Color.black, 1));
			addMouseListener(new ClickListener());
		}
		
		/** Return token */
		public char getToken() {
			return token;
		}
		
		/** Set a new token */
		public void setToken(char c) {
			token = c;
			repaint();
		}
		
		/** Paint the cell */
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			if (token == 'X') {
				g.drawLine(10, 10, getWidth() - 10, getHeight() - 10);
				g.drawLine(getWidth() - 10, 10, 10, getHeight() - 10);
			}
			else if (token == '0') {
				g.drawLine(10, 10, getWidth() - 20, getHeight() - 20);
			}
		}
		
		/** Handle mouse click on a cell */
		private class ClickListener extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				// If cell is not occupied and the player has the turn
				if ((token == ' ') && myTurn) {
					setToken(myToken);
					myTurn = false;
					rowSelected = row;
					columnSelected = column;
					jlblStatus.setText("Waiting for the other player to move");
					waiting = false;
				}
			}
		}
	}
}
