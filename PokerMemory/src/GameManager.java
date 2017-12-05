/**
 * Main class for Memory game
 *
 * @author Michael Leonhard (Original Author)
 * @author Modified by Bienvenido Vélez (UPRM)
 * @author Modified by RUMHackers.java (UPRM)
 * @version Dic 2017
 */

import java.io.IOException;

import javax.swing.JOptionPane;


public class GameManager {

	/**
	 * @param args.
	 * @throws InterruptedException. 
	 * @throws IOException. 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		// Make an instance of the main game class

		int playMore = 2;
		while (playMore != 1) {
			MemoryFrame instance = new MemoryFrame();
			instance.newGame("easy");

			while(!instance.gameOver()) {
				Thread.sleep(500);
			}
			playMore = JOptionPane.showConfirmDialog(null, "Congratulations, you found every posible combination. Play Again?", "Game over.", JOptionPane.YES_NO_OPTION);
			System.out.println(playMore+"");
		}
		System.exit(0);
	}
}