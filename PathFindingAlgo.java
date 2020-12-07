import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;


public class PathFindingAlgo {

    JFrame frame;
    
    private final int boardXlength = 1250;
    private final int boardYlength = 501;
    private int cellSize = 25;
    private int numOfCellsOnX = boardXlength/cellSize;
    private int numOfCellsOnY = boardYlength/cellSize;
    private int currentAlgo = 0;
    private int currentSpeed = 0;
    private int currentTool = 0;
    private int srcX = -1;
    private int srcY = -1;
    private int destX = -1;
    private int destY = -1;
    boolean solving = false;
    JPanel settingBox = new JPanel();
    // JPanel algoBox = new JPanel();
    // JPanel speedBox = new JPanel();
    // JLabel algoLabel = new JLabel("Select Algorithm");
    // JLabel speedLabel = new JLabel("Speed");
    // JRadioButton dijkstra = new JRadioButton("Dijkstra");
    // JRadioButton aStar = new JRadioButton("A*");
    JButton clearBoardButton = new JButton("Clear Board");
    JButton clearPathButton = new JButton("Clear Path");
    JButton visualizeButton = new JButton("Visualize!");
    private String[] algoSelect = {"Select Algorithm","Dijkstra","A*"};
    private String[] speedSelect = {"Select Speed","Slow","Average","Fast"};
    private String[] tools = {"Start","Finish","Wall", "Eraser"};
    JComboBox<String> algoBox = new JComboBox<>(algoSelect);
    JComboBox<String> speedBox = new JComboBox<>(speedSelect);
    JComboBox<String> toolBox = new JComboBox<>(tools);
	Cell[][] grid;
    Board gridArea;

    


    public static void main(String[] args) {
        new PathFindingAlgo();
    }

    public PathFindingAlgo() {
        createBoard();
		initializeScreen();
    }
    
    public void createBoard()
    {
        grid = new Cell[numOfCellsOnX][numOfCellsOnY];

        for(int i=0 ; i<numOfCellsOnX ; i++)
        {
            for(int j=0 ; j<numOfCellsOnY ; j++)
            {
                grid[i][j] = new Cell(0,i,j);
            }
        }
    }

    public void clearPath()
    {
        for(int i=0 ; i<numOfCellsOnX ; i++) 
        {
            for(int j=0 ; j<numOfCellsOnY ; j++)
            {
                if(grid[i][j].getType() == 4 || grid[i][j].getType() == 5)
                {
                    grid[i][j] = new Cell(0,i,j);
                }    
			}
		}
    }


    private void initializeScreen() {

		frame = new JFrame();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setTitle("Path Finding Algorithm");
		frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(0xD09683));
        frame.getContentPane().setLayout(null);

        //Setting Box 

        settingBox.setBorder(BorderFactory.createLineBorder(Color.black));
        settingBox.setLayout(null);
        settingBox.setBounds(12,15,1250,85);

        algoBox.setLayout(null);
        algoBox.setBounds(12,15,120,25);
        settingBox.add(algoBox);
        
        speedBox.setLayout(null);
        speedBox.setBounds(212,15,120,25);
        settingBox.add(speedBox);

        toolBox.setLayout(null);
        toolBox.setBounds(350,15,120,25);
        settingBox.add(toolBox);

        // algoBox.setLayout(null);
        // algoBox.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
        // algoBox.setBounds(10,10,200,105);

        // algoLabel.setBounds(20,20,120,25);
        // algoLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        // algoBox.add(algoLabel);
        
        // dijkstra.setBounds(20,45,120,25);
        // dijkstra.setBorder(BorderFactory.createLineBorder(Color.black));
        // aStar.setBounds(20,70,120,25);        
        // aStar.setBorder(BorderFactory.createLineBorder(Color.black));
        // algoBox.add(dijkstra);
        // algoBox.add(aStar);

        // settingBox.add(algoBox);


        // speedBox.setLayout(null);
        // speedBox.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
        // speedBox.setBounds(250,10,200,105);

        // speedLabel.setBounds(20,20,120,25);
        // speedLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        // speedBox.add(speedLabel);

        // settingBox.add(speedBox);

        clearBoardButton.setLayout(null);
        clearBoardButton.setBounds(500,20,120,25);
        settingBox.add(clearBoardButton);

        clearPathButton.setLayout(null);
        clearPathButton.setBounds(700,20,120,25);
        settingBox.add(clearPathButton);

        visualizeButton.setLayout(null);
        visualizeButton.setBounds(900,20,120,25);
        settingBox.add(visualizeButton);


        // line1 = new DrawLine();
        // line1.setBounds(120,45,120,25);
        // settingBox.add(line1);

        frame.getContentPane().add(settingBox);
        
        gridArea = new Board();
        gridArea.setBounds(12, 178, boardXlength,boardYlength);
        frame.getContentPane().add(gridArea);


        algoBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				currentAlgo = algoBox.getSelectedIndex();
			}
        });
        
        speedBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				currentSpeed = speedBox.getSelectedIndex();
			}
        });
        
        toolBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				currentTool = toolBox.getSelectedIndex();
			}
		});

        clearBoardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createBoard();
				gridArea.repaint();
			}
        });
        
        visualizeButton.addActionListener(new ActionListener() {		//ACTION LISTENERS
			@Override
			public void actionPerformed(ActionEvent e) {
                
                if(currentAlgo == 0)
                {
                    JOptionPane.showMessageDialog(frame,"Select an algorithm");  
                }
                else if(currentSpeed == 0)
                {
                    JOptionPane.showMessageDialog(frame,"Select speed"); 
                }
                else if(srcX == -1 && srcY == -1)
                {
                    JOptionPane.showMessageDialog(frame,"Select a source cell"); 
                }
                else if(destX == -1 && destY == -1)
                {
                    JOptionPane.showMessageDialog(frame,"Select a destination cell"); 
                }
                else
                {
                    solving = true;
                    
                    
                    
                }


			}
        });

        startSearch();
    }

    public void startSearch()
    {
        if(solving)
        {
            switch(currentAlgo)
                    {
                        case 1 : 
                        {
                            DijkstraAlgo();
                            // gridArea.repaint();
                            break;
                        }

                        case 2 :
                        {
                            // AStarAlgo();
                            break;
                        }
                    }
        }
                    pause();
    }

    public void pause() {	//PAUSE STATE
		int i = 0;
		while(!solving) {
			i++;
			if(i > 500)
				i = 0;
			try {
				Thread.sleep(1);
			} catch(Exception e) {}
		}
		startSearch();	//START STATE
	}


    public void DijkstraAlgo()
    {
        Queue<Cell> q = new LinkedList<>();
        q.add(grid[srcX][srcY]);
         
        while(solving && !q.isEmpty())
        {
            Cell temp = q.remove();
            int d = temp.getDist();

            Queue<Cell> extra = addNeighbors(q,temp,d);
            if(extra.size() > 0)
            {
                q.addAll(extra);
            }
            
            // delay();
            
        }
    }

    public void delay() {	//DELAY METHOD
		try {
			Thread.sleep(30);
		} catch(Exception e) {}
	}

    public Queue<Cell> addNeighbors(Queue<Cell> q,Cell temp,int dist)
    {
        Queue<Cell> q2 = new LinkedList<>();
        for(int i=-1 ; i<2 ; i++)
        {
            for(int j=-1 ; j<2 ; j++)
            {
                int newX = temp.getX()+i;
                int newY = temp.getY()+j;
                
                if(onBoard(newX,newY))
                {
                    Cell curCell = grid[newX][newY];
                    if(newX == destX && newY == destY)
                    {
                        // Cell curCell = grid[newX][newY];
                        //found destination cell, 
                        //now set the color of curent cell as of path and backtrack
                        

                        // curCell.setType(5);
                        int x = temp.getX();
                        int y = temp.getY();

                        while(onBoard(x, y) && grid[x][y].getType() != 1)
                        {
                            System.out.println(x+","+y);
                            grid[x][y].setType(5);
                            int x2 = grid[x][y].getPrevX();
                            int y2 = grid[x][y].getPrevY();
                            x = x2;
                            y = y2;
                            gridArea.repaint();
                            try {
                                Thread.sleep(10);
                            } catch(Exception e) {}
                        }

                        System.out.println(x+","+y);

                        solving = false;
                        return q2;
                    }
                    else if(curCell.getType() == 0)
                    {
                        q2.add(curCell);
                        curCell.setType(4);
                        curCell.setDist(dist+1);
                        curCell.setPrevCell(temp.getX(),temp.getY());
                        gridArea.repaint();
                        try {
                            Thread.sleep(10);
                        } catch(Exception e) {}
                    }
                    else if(curCell.getType() == 4 && curCell.getDist() >= dist+1)
                    {
                        curCell.setDist(dist+1);
                        curCell.setPrevCell(temp.getX(),temp.getY());
                    }
                }
                
                
            }
        }
        return q2;
    }


    private boolean onBoard(int x,int y) {
        
        if((x>-1 && x<numOfCellsOnX) && (y>-1 && y<numOfCellsOnY))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    class Cell {
		
		// 0 = empty
		private int cellType = 0;
		private int x; 
        private int y;
        private int dist;
        private int prevX;
        private int prevY;
	
		public Cell(int type, int x, int y) {
			cellType = type;
			this.x = x;
            this.y = y;
            dist = Integer.MAX_VALUE;
            prevX = -1;
            prevY = -1;
        }
        
        public int getPrevX() {
            return prevX;
        }

        public int getPrevY() {
            return prevY;
        }

        public void setPrevCell(int x, int y) {
            prevX = x;
            prevY = y;
        }

        public int getDist() {
            return dist;
        }

        public void setDist(int d) {
            dist = d;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getType() {
            return cellType;
        }
        public void setType(int type) {cellType = type;}
    }
    



    
    
    class Board extends JPanel implements MouseListener, MouseMotionListener{
		
        public Board() {
            addMouseListener(this);
            addMouseMotionListener(this);
        }
        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            for(int i=0 ; i<numOfCellsOnX ; i++)
            {
                for(int j=0 ; j<numOfCellsOnY ; j++)
                {
                    // g.setColor(new Color(0xD09683));

                    switch(grid[i][j].getType()) 
                    {
						case 0:
							g.setColor(Color.WHITE);
							break;
						case 1:
							g.setColor(Color.RED);
							break;
						case 2:
							g.setColor(Color.BLUE);
							break;
						case 3:
							g.setColor(Color.BLACK);
							break;
						case 4:
							g.setColor(Color.CYAN);
							break;
						case 5:
							g.setColor(Color.YELLOW);
							break;
					}
					g.fillRect(i*cellSize,j*cellSize,cellSize,cellSize);
                    // g.setColor(Color.BLACK);
                    g.setColor(new Color(0xD09683));
					g.drawRect(i*cellSize,j*cellSize,cellSize,cellSize);
                }
            }

        }

        @Override
		public void mouseDragged(MouseEvent e) {
			try {
				int x = e.getX()/cellSize;	
				int y = e.getY()/cellSize;
                
                if(currentTool == 2)
                {
                    if(grid[x][y].getType() != 1 && grid[x][y].getType() != 2)
                    {
                        grid[x][y].setType(3);
                    }
                }
                else if(currentTool == 3)
                {
                    if(grid[x][y].getType() != 1 && grid[x][y].getType() != 2)
                    {
                        grid[x][y].setType(0);
                    }
                }
                gridArea.repaint();
			} catch(Exception z) {}
		}

        @Override
        public void mouseMoved(MouseEvent e) {}

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {
            clearPath();
            try
            {
                int x = e.getX()/cellSize;
                int y = e.getY()/cellSize;

                switch(currentTool) 
				{
					case 0: 
					{
                        if(grid[x][y].getType()!=2) 
                        {	
                            if(srcX > -1 && srcY > -1)
                            {
                                grid[srcX][srcY].setType(0);
                            }
                            srcX = x;
                            srcY = y;
                            grid[srcX][srcY].setType(1); 
                            // gridArea.repaint();
                        }
						break;
					}
					case 1: 
					{
                        if(grid[x][y].getType()!=2) 
                        {	
                            if(destX > -1 && destY > -1)
                            {
                                grid[destX][destY].setType(0);
                            }
                            destX = x;
                            destY = y;
                            grid[destX][destY].setType(2);
                            // gridArea.repaint();
                        }
						break;
					}
					default:
						
				}
                gridArea.repaint();
            }
            catch(Exception z) {}

        }

        @Override
        public void mouseReleased(MouseEvent e) {}
    }

    
    

};
