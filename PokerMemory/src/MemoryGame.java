/**
 * Stores Memory game frame and its components.
 *
 * @author Michael Leonhard (Original Author)
 * @author Modified by Bienvenido Vélez (UPRM)
 * @author Modified by UPRM Hackers.java (UPRM)
 * @version Dic 2017
 */

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MemoryGame implements ActionListener {

	public static boolean DEBUG = true;
	private JFrame mainFrame;					// Top level window
	private Container mainContentPane;			// Frame that holds card field and turn counter
	private TurnsTakenCounterLabel turnCounterLabel;
	private GameLevel difficulty;
	private ScoreCounterLabel scoreCounterLabel;

	/**
	 * Make a JMenuItem, associate an action command and listener, add to menu.
	 */
	private static void newMenuItem(String text, JMenu menu, ActionListener listener) {
		JMenuItem newItem = new JMenuItem(text);
		newItem.setActionCommand(text);
		newItem.addActionListener(listener);
		menu.add(newItem);
	}

	/**
	 * Default constructor loads card images, makes window.
	 * @throws IOException.
	 */
	public MemoryGame () throws IOException {

		// Make top-level window
		this.mainFrame = new JFrame("UPRM Hackers.java Poker Memory");
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setSize(800,700);
		this.mainContentPane = this.mainFrame.getContentPane();
		this.mainContentPane.setLayout(new BoxLayout(this.mainContentPane, BoxLayout.PAGE_AXIS));

		// Menu bar
		JMenuBar menuBar = new JMenuBar();
		this.mainFrame.setJMenuBar(menuBar);

		// Game menu
		JMenu gameMenu = new JMenu("Memory");
		menuBar.add(gameMenu);
		newMenuItem("Exit", gameMenu, this);

		// Difficulty menu
		JMenu difficultyMenu = new JMenu("New Game");
		menuBar.add(difficultyMenu);
		newMenuItem("Easy Level", difficultyMenu, this);
		newMenuItem("Equal Pair Level", difficultyMenu, this);
		newMenuItem("Same Rank Trio Level", difficultyMenu, this);
		newMenuItem("Four of a kind Level", difficultyMenu, this);
		newMenuItem("Flush Level", difficultyMenu, this);
		newMenuItem("Straight Level", difficultyMenu, this);
		newMenuItem("Combo Level", difficultyMenu, this);

		// Help menu
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		newMenuItem("How To Play", helpMenu, this);
		newMenuItem("About", helpMenu, this);
	}

	/**
	 * Handles menu events. Necessary for implementing ActionListener.
	 *
	 * @param e object with information about the event.
	 */
	public void actionPerformed(ActionEvent e) {
		dprintln("actionPerformed " + e.getActionCommand());
		try {
			if(e.getActionCommand().equals("Easy Level")) newGame("easy");
			else if(e.getActionCommand().equals("Equal Pair Level")) newGame("medium");
			else if(e.getActionCommand().equals("Same Rank Trio Level")) newGame("trio");
			else if(e.getActionCommand().equals("Flush Level")) newGame("flush");
			else if(e.getActionCommand().equals("Straight Level")) newGame("straight");
			else if(e.getActionCommand().equals("Combo Level")) newGame("combo");
			else if(e.getActionCommand().equals("Four of a kind Level")) newGame("fourkind");
			else if(e.getActionCommand().equals("How To Play")) showInstructions();
			else if(e.getActionCommand().equals("About")) showAbout();
			else if(e.getActionCommand().equals("Exit")) System.exit(0);
		} catch (IOException e2) {
			e2.printStackTrace(); throw new RuntimeException("IO ERROR");
		}
	}

	/**
	 * Prints debugging messages to the console.
	 *
	 * @param message the string to print to the console.
	 */
	static public void dprintln( String message ) {
		if (DEBUG) System.out.println( message );
	}

	public JPanel showCardDeck() {
		// Make the panel to hold all of the cards
		JPanel panel = new JPanel(new GridLayout(difficulty.getRowsPerGrid(),difficulty.getCardsPerRow()));
		
		// This set of cards must have their own manager
		this.difficulty.makeDeck();

		for(int i= 0; i<difficulty.getGrid().size();i++) {
			panel.add(difficulty.getGrid().get(i));
		}
		return panel;
	}

	/**
	 * Prepares a new game (first game or non-first game).
	 * @throws IOException.
	 */
	public void newGame(String difficultyMode) throws IOException {
		// Reset the turn and score counters to zero
		this.turnCounterLabel = new TurnsTakenCounterLabel();
		this.scoreCounterLabel = new ScoreCounterLabel();

		// Make a new card field with cards, and add it to the window

		if(difficultyMode.equalsIgnoreCase("easy")) {
			this.difficulty = new EasyLevel(this.turnCounterLabel, this.scoreCounterLabel, this.mainFrame);
		}
		else if(difficultyMode.equalsIgnoreCase("medium")) {
			this.difficulty = new EqualPairLevel(this.turnCounterLabel, this.scoreCounterLabel, this.mainFrame);
		}
		else if(difficultyMode.equalsIgnoreCase("trio")) {
			this.difficulty = new RankTrioLevel(this.turnCounterLabel, this.scoreCounterLabel, this.mainFrame);
		}
		else if(difficultyMode.equalsIgnoreCase("fourkind")) {
			this.difficulty = new FourOfAKindLevel(this.turnCounterLabel, this.scoreCounterLabel, this.mainFrame);
		}
		else if(difficultyMode.equalsIgnoreCase("flush")) {
			this.difficulty = new FlushLevel(this.turnCounterLabel, this.scoreCounterLabel, this.mainFrame);
		}
		else if(difficultyMode.equalsIgnoreCase("straight")) {
			this.difficulty = new RankTrioLevel(this.turnCounterLabel, this.scoreCounterLabel, this.mainFrame);
		}
		else if(difficultyMode.equalsIgnoreCase("combo")) {
			this.difficulty = new ComboLevel(this.turnCounterLabel, this.scoreCounterLabel, this.mainFrame);
		}
		else {
			throw new RuntimeException("Illegal Game Level Detected");
		}

		this.turnCounterLabel.reset();
		this.scoreCounterLabel.reset();

		// Clear out the content pane (removes turn counter label and card field)
		this.mainContentPane.removeAll();

		this.mainContentPane.add(showCardDeck());

		// Add the turn and score counter label back in again
		this.mainContentPane.add(this.turnCounterLabel);
		this.mainContentPane.add(this.scoreCounterLabel);

		// Show the window (in case this is the first game)
		this.mainFrame.setVisible(true);
	}

	public boolean gameOver() throws FileNotFoundException, InterruptedException {
		return difficulty.isGameOver();
	}

	/**
	 * Shows an instructional dialog box to the user.
	 */
	private void showInstructions() {
		dprintln("MemoryGame.showInstructions()");
		final String HOWTOPLAYTEXT = 
				"How To Play\r\n" +
						"\r\n" +
						"EQUAL PAIR Level\r\n"+
						"The game consists of 8 pairs of cards.  At the start of the game, every card is face down.  The\r\n"+
						"object is to find every pair of cards in the fewest number of turns.\r\n"+
						"\r\n"+
						"Click on two cards to turn them face up.  If the cards are the same, then you have discovered a\r\n"+
						"pair.  The pair will remain turned up.  If the cards are different, they will flip back over\r\n"+
						"automatically after a short delay.  The game is won when you have discovered every pair.\r\n"+
						"\r\n"+
						"SAME RANK TRIO Level\r\n"+
						"The game consists of a grid of distinct cards.  At the start of the game, every card is face down.\r\n"+
						"The object is to find ever trio of cards with the same ranks in the fewest number of turns.\r\n"+
						"\r\n"+
						"Click on three cards to turn them face up.  If the cards have the same rank, then you have\r\n"+
						"discovered a trio.  The trio will remain turned up.  If the cards are different, they will flip back\r\n"+
						"over automatically after a short delay.  The game is won when you have discovered every trio.\r\n"+
						"\r\n"+
						"FOUR OF A KIND Level\r\n"+
						"The game consists of a grid of distinct cards.  At the start of the game, every card is face down.\r\n"+
						"The object is to uncover every quartet with the same rank in the fewest number of turns\r\n"+
						"\r\n"+
						"Click on four cards to turn them face up.  If the cards have the same rank, then you have\r\n"+
						"discovered a quartet.  The quartet will remain turned up.  If the cards are different, they will flip\r\n"+
						"back over automatically after a short delay.  The game is won when you have discovered\r\n"+
						"every quartet.\r\n"+
						"\r\n"+
						"FLUSH Level\r\n"+
						"The game consists of a grid of distinct cards.  At the start of the game, every card is face down.\r\n"+
						"The object is to find every quintet of cards with the same suit in the fewest number of turns.\r\n"+
						"\r\n"+
						"Click on five cards to turn them face up.  If the cards have the same suit, then you have\r\n"+
						"discovered a quintet.  The quintet will remain turned up.  If the cards are different, they will flip\r\n"+
						"back over automatically after a short delay.  The game is won when you have discovered\r\n"+
						"every quintet.\r\n"+
						"\r\n"+
						"COMBO Level\r\n"+
						"The game consists of a grid of distinct cards.  At the start of the game, every card is face down.\r\n"+
						"Uncover a poker hand and decide whether you stay with it or not.  The object is to uncover the\r\n"+
						"best poker hands in the fewest number of turns.  The game is won when you have uncovered\r\n"+
						"every card.";

		JOptionPane.showMessageDialog(this.mainFrame, HOWTOPLAYTEXT
				, "How To Play", JOptionPane.PLAIN_MESSAGE);
	}

	
	/**
	 * Shows an dialog box with information about the program.
	 */
	private void showAbout() {
		dprintln("MemoryGame.showAbout()");
		final String ABOUTTEXT = "Game Customized at UPRM. Originally written by Mike Leonhard";

		JOptionPane.showMessageDialog(this.mainFrame, ABOUTTEXT
				, "About Memory Game", JOptionPane.PLAIN_MESSAGE);
	}
}