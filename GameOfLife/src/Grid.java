import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Grid extends JPanel {

	/*
	 * true -> cell is alive
	 * false -> cell is dead
	 */
	int row;
	int column;
	boolean[][] grid;
	int scale;

	public Grid() {
		scale=0;
		row=0;
		column=0;
		grid = new boolean[row][column];
	}

	public Grid(int row,int column,int scale) {
		this.row=row;
		this.column=column;
		this.grid= new boolean[row][column];
		this.scale=scale;
	}

	public Grid(int row,int column, int scale, boolean[][] grid) {
		this.row=row;
		this.column=column;
		this.scale=scale;
		this.grid=grid;
	}

	public void load(int i, int j) {
		if(i>=0 && i<row)
			if(j>=0 && j<column)
				grid[i][j]=true;
	}

	public void update() {
		//new array for reading (right in this.grid)
		boolean[][] tmp = new boolean[row][column];
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[i].length; j++) {
				tmp[i][j]=grid[i][j];
			}
		}
		int neighboorNumber = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j <grid[i].length; j++) {
				neighboorNumber = numberOfNeighboors(i, j, tmp);
				//calcul of the cell
				if(grid[i][j] && (neighboorNumber<2 || neighboorNumber>3)) {
						grid[i][j]=false;
				}
				else if(!grid[i][j] && neighboorNumber==3)
						grid[i][j]=true;
			}
		}
	}

	public int numberOfNeighboors(int row, int column, boolean[][] tab) {
		int countCell=0;
		//up-left
		if(row>0 && column>0)
			if(tab[row-1][column-1])
				countCell++;
		//up-right
		if(row<this.row-1 && column>0)
			if(tab[row+1][column-1])
				countCell++;
		//down-left
		if(row>0 && column<this.column-1)
			if(tab[row-1][column+1])
				countCell++;
		//down-right
		if(row<this.row-1 && column<this.column-1)
			if(tab[row+1][column+1])
				countCell++;
		//left
		if(column>0)
			if(tab[row][column-1])
				countCell++;
		//right
		if(column<this.column-1)
			if(tab[row][column+1])
				countCell++;
		//down
		if(row<this.row-1)
			if(tab[row+1][column])
				countCell++;
		//up
		if(row>0)
			if(tab[row-1][column])
				countCell++;

		return countCell;
	}

	@Override
	public void paintComponent(Graphics g) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j <grid[i].length; j++) {
				if(grid[i][j])
					g.setColor(Color.white);
				else
					g.setColor(Color.decode("#131313"));
				g.fillRect(j*scale, i*scale, scale, scale);
			}
		}
	}
}
