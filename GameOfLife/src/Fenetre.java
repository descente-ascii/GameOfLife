import java.util.Random;

import javax.swing.JFrame;

public class Fenetre extends JFrame{

	RleParser p = new RleParser();
	String filePath = "src/60P5H2V0gun.rle";
	String file2string = p.file2string(filePath);
	String getSecondLine= p.getSecondLine(file2string);
	int col = p.getX(getSecondLine);
	int row = p.getY(getSecondLine);
	int width = col;
	int height = row;
	int scale = (height/row);
	boolean[][] tab = p.runParser(filePath);
	Grid grid = new Grid(row,col,scale,tab);

	public Fenetre() {
		this.setSize(width, height+26);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setContentPane(grid);
		this.setVisible(true);
	}

	public void run() {
		while(true) {
			grid.update();
			grid.repaint();
			try {
				Thread.sleep(5);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean[][] putInCenter(boolean[][] tab){
		boolean[][] fullTab = new boolean[height][width];
		int a = 250;
		int b = 250;
		for(int i=0; i<tab.length; i++) {
			for(int j=0; j<tab[i].length; j++) {
				fullTab[a+i][b+i] = tab[i][j];
			}
		}
		return fullTab;
	}
	
	public void randomSetUp(Grid tab, double d) {
		Random r = new Random();
		Random randDens = new Random();

		for (int i = 0; i < tab.grid.length; i++) {
			for (int j = 0; j < tab.grid[i].length; j++) {
				int dens = randDens.nextInt(100);
				if(dens<=d*100) {
					int bit = r.nextInt(2);
					System.out.println(bit);
					if(bit==1)
						tab.load(i,j);
				}
			}
		}
	}
}
