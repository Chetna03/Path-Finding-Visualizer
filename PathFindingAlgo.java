import java.util.*;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
// import java.util.ArrayList;
// import java.util.Random;
// 
import javax.swing.JFrame;
import javax.swing.BorderFactory;
// import javax.swing.ImageIcon;
import javax.swing.JButton;
// import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.JComboBox;

// import javax.swing.*;



import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;


public class PathFindingAlgo {

    JFrame frame;

    ArrayList<Cell> q = new ArrayList<>();
    
    private final int boardXlength = 1176;
    private final int boardYlength = 400;
    private int cellSize = 25;
    private int numOfCellsOnX = boardXlength/cellSize;
    private int numOfCellsOnY = boardYlength/cellSize;
    private int currentAlgo = 0;
    private int currentSpeed = 0;
    private int speed = 20;
    private int currentTool = 0;
    private int srcX = -1;
    private int srcY = -1;
    private int destX = -1;
    private int destY = -1;
    private int buttonWidth = 120;
    private int buttonHeight = 35;
    boolean solving = false;
    boolean pauseAlgo = false;
    boolean algoInProcess = false;
    private int buttonBgColor = 0xD09683;
    private int legendPanelWidth = 180;
    private int legendPanelHeight = 30;
    private int distance;
    private boolean completed = false;
    JPanel mainBox = new JPanel();
    JPanel settingBox = new JPanel();
    JPanel headingBox = new JPanel();
    JPanel legendBox = new JPanel();
    JPanel startPanel = new JPanel();
    JPanel endPanel = new JPanel();
    JPanel wallPanel = new JPanel();
    JPanel checkedPanel = new JPanel();
    JPanel pathPanel = new JPanel();
    JPanel distPanel = new JPanel();

    JPanel startColor = new JPanel();
    JPanel endColor = new JPanel();
    JPanel wallColor = new JPanel();
    JPanel checkedColor = new JPanel();
    JPanel pathColor = new JPanel();

    JLabel headingLabel = new JLabel("Path Finding Visualizer");
    JLabel startLabel = new JLabel("Source ");
    JLabel endLabel = new JLabel("Destination ");
    JLabel wallLabel = new JLabel("Wall");
    JLabel checkedLabel = new JLabel("Checked Cell");
    JLabel pathLabel = new JLabel("Final Path");
    JLabel distLabel = new JLabel("Distance : ");
    JLabel distValueLabel = new JLabel();
    // JPanel algoBox = new JPanel();
    // JPanel speedBox = new JPanel();
    // JLabel algoLabel = new JLabel("Select Algorithm");
    // JLabel speedLabel = new JLabel("Speed");
    // JRadioButton dijkstra = new JRadioButton("Dijkstra");
    // JRadioButton aStar = new JRadioButton("A*");
    RoundButton clearBoardButton = new RoundButton("Clear Board");
    RoundButton clearPathButton = new RoundButton("Clear Path");
    RoundButton stopButton = new RoundButton("Stop");
    RoundButton continueButton = new RoundButton("Conitnue");
    RoundButton visualizeButton = new RoundButton("Visualize!");
    private String[] algoSelect = {"Select Algorithm","Dijkstra","A*"};
    private String[] speedSelect = {"Select Speed","Slow","Average","Fast"};
    private String[] tools = {"Source","Destination","Wall", "Wall Remover"};
    JComboBox<String> algoBox = new JComboBox<>(algoSelect);
    JComboBox<String> speedBox = new JComboBox<>(speedSelect);
    JComboBox<String> toolBox = new JComboBox<>(tools);

    public Color darkColor = new Color(47, 149, 154);
    public Color lightColor = new Color(255,255,255);
    public Color midColor = new Color(224, 135, 139);

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
        distValueLabel.setText("");
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
        distValueLabel.setText("");
        for(int i=0 ; i<numOfCellsOnX ; i++) 
        {
            for(int j=0 ; j<numOfCellsOnY ; j++)
            {
                if(grid[i][j].getType() == 4 || grid[i][j].getType() == 5 || grid[i][j].getType() == 6)
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
        frame.getContentPane().setBackground(darkColor);
        frame.getContentPane().setLayout(null);

        //Setting Box 

        headingBox.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, lightColor));
        headingBox.setLayout(null);
        
        headingBox.setBackground(darkColor);
        headingBox.setBounds(225,10,800,55);

        headingLabel.setBounds(200,-3,800,55);
        headingLabel.setFont(new Font("Serif", Font.ITALIC, 44));
        headingLabel.setForeground(lightColor);
        headingBox.add(headingLabel);
        // heading.setSize(100, 100);
        // heading.setLayout(new BoxLayout(heading,BoxLayout.Y_AXIS));
        // heading.setLayout(new GridLayout(3,1, 5, 5));
        // heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        // heading.setPreferredSize(new Dimension(400, 100));
        // heading.setHorizontalAlignment(JLabel.CENTER);
        frame.getContentPane().add(headingBox);

        mainBox.setBorder(BorderFactory.createLineBorder(Color.black));
        mainBox.setLayout(null);
        mainBox.setBackground(lightColor);
        mainBox.setBounds(12,90,1250,125);

        settingBox.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        settingBox.setLayout(null);
        settingBox.setBackground(lightColor);
        settingBox.setBounds(25,1,1200,75);

        int selectWidth = 150;
        int x = 1;
        int spaceBetween = 28;

        algoBox.setLayout(null);
        algoBox.setBounds(3,25,selectWidth,25);
        settingBox.add(algoBox);
        
        speedBox.setLayout(null);
        speedBox.setBounds(3 + spaceBetween*x + selectWidth*x,25,150,25);
        settingBox.add(speedBox);
        x++;

        toolBox.setLayout(null);
        toolBox.setBounds(3 + spaceBetween*x + selectWidth*x,25,150,25);
        settingBox.add(toolBox);
        x++;




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
        // clearBoardButton.setBackground(new Color(0f,0f,0f,0f ));
        clearBoardButton.setPosition(3 + spaceBetween*x + selectWidth*x,20,buttonWidth,buttonHeight);
        settingBox.add(clearBoardButton);

        int z = 1;
        clearPathButton.setLayout(null);
        clearPathButton.setPosition(3 + spaceBetween*(x+z) + selectWidth*x + buttonWidth*z ,20,buttonWidth,buttonHeight);
        settingBox.add(clearPathButton);
        z++;

        stopButton.setLayout(null);
        stopButton.setPosition(3 + spaceBetween*(x+z) + selectWidth*x + buttonWidth*z,20,buttonWidth,buttonHeight);
        settingBox.add(stopButton);
        z++;

        // continueButton.setLayout(null);
        // continueButton.setPosition(3 + spaceBetween*(x+z) + selectWidth*x + buttonWidth*z,20,buttonWidth,buttonHeight);
        // settingBox.add(continueButton);
        // z++;

        visualizeButton.setLayout(null);
        visualizeButton.setPosition(3 + spaceBetween*(x+z) + selectWidth*x + buttonWidth*z,10,buttonWidth+20,buttonHeight+20);
        settingBox.add(visualizeButton);
        z++;

        mainBox.add(settingBox);


        legendBox.setLayout(null);
        legendBox.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
        legendBox.setBackground(lightColor);
        legendBox.setBounds(25,76,1200,40);


        int i = 0;

        startColor.setBackground(Color.RED);
        startColor.setLayout(null);
        startColor.setBounds(10, 5, 20, 20);
        startColor.setBorder(BorderFactory.createRaisedBevelBorder());


        startLabel.setLayout(null);
        startLabel.setBounds(50,2,150,20);
        startLabel.setFont(new Font("Serif", Font.PLAIN, 20));

        startPanel.setLayout(null);
        startPanel.setBounds(10 + legendPanelWidth*i,8,legendPanelWidth,legendPanelHeight);
        // startPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        startPanel.add(startColor);
        startPanel.add(startLabel);
        startPanel.setBackground(Color.WHITE);
        i++;


        endColor.setBackground(Color.BLUE);
        endColor.setLayout(null);
        endColor.setBounds(10, 5, 20, 20);
        endColor.setBorder(BorderFactory.createRaisedBevelBorder());


        endLabel.setLayout(null);
        endLabel.setBounds(50,2,150,20);
        endLabel.setFont(new Font("Serif", Font.PLAIN, 20));

        endPanel.setLayout(null);;
        endPanel.setBounds(10 + legendPanelWidth*i,8,legendPanelWidth,legendPanelHeight);
        // endPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        endPanel.add(endColor);
        endPanel.add(endLabel);
        endPanel.setBackground(Color.WHITE);
        i++;





        checkedColor.setBackground(Color.lightGray);
        checkedColor.setLayout(null);
        checkedColor.setBounds(10, 5, 20, 20);
        checkedColor.setBorder(BorderFactory.createRaisedBevelBorder());


        checkedLabel.setLayout(null);
        checkedLabel.setBounds(50,2,150,20);
        checkedLabel.setFont(new Font("Serif", Font.PLAIN, 20));

        checkedPanel.setLayout(null);;
        checkedPanel.setBounds(10 + legendPanelWidth*i,8,legendPanelWidth,legendPanelHeight);
        // checkedPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        checkedPanel.add(checkedColor);
        checkedPanel.add(checkedLabel);
        checkedPanel.setBackground(Color.WHITE);
        i++;


        pathColor.setBackground(Color.YELLOW);
        pathColor.setLayout(null);
        pathColor.setBounds(10, 5, 20, 20);
        pathColor.setBorder(BorderFactory.createRaisedBevelBorder());


        pathLabel.setLayout(null);
        pathLabel.setBounds(50,2,150,20);
        pathLabel.setFont(new Font("Serif", Font.PLAIN, 20));

        pathPanel.setLayout(null);;
        pathPanel.setBounds(10 + legendPanelWidth*i,8,legendPanelWidth,legendPanelHeight);
        // pathPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        pathPanel.add(pathColor);
        pathPanel.add(pathLabel);
        pathPanel.setBackground(Color.WHITE);
        i++;


        wallColor.setBackground(Color.BLACK);
        wallColor.setLayout(null);
        wallColor.setBounds(10, 5, 20, 20);
        wallColor.setBorder(BorderFactory.createRaisedBevelBorder());

        wallLabel.setLayout(null);
        wallLabel.setBounds(50,2,150,20);
        wallLabel.setFont(new Font("Serif", Font.PLAIN, 20));

        wallPanel.setLayout(null);
        wallPanel.setBounds(10 + legendPanelWidth*i,8,legendPanelWidth,legendPanelHeight);
        // wallPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        wallPanel.add(wallColor);
        wallPanel.add(wallLabel);
        wallPanel.setBackground(Color.WHITE);


        distLabel.setLayout(null);
        distLabel.setBounds(10,2,90,25);
        distLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        distValueLabel.setLayout(null);
        distValueLabel.setBounds(100,2,150,25);
        distValueLabel.setFont(new Font("Serif", Font.PLAIN, 20));;

        distPanel.setLayout(null);
        distPanel.setBounds(950,8,legendPanelWidth+80,legendPanelHeight);
        // distPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        distPanel.add(distLabel);
        distPanel.add(distValueLabel);
        distPanel.setBackground(Color.WHITE);

        legendBox.add(distPanel);

        legendBox.add(pathPanel);
        legendBox.add(checkedPanel);
        legendBox.add(wallPanel);
        legendBox.add(startPanel);
        legendBox.add(endPanel);





        mainBox.add(legendBox);

        // line1 = new DrawLine();
        // line1.setBounds(120,45,120,25);
        // settingBox.add(line1);

        frame.getContentPane().add(mainBox);
        
        gridArea = new Board();
        gridArea.setBounds(50, 245, boardXlength,boardYlength);
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
                
                switch(currentSpeed)
                {
                    case 1 : {
                        speed = 60;
                        break;
                    }

                    case 2 : {
                        speed = 20;
                        break;
                    }

                    case 3 : {
                        speed = 5;
                        break;
                    }

                    default : {
                        speed = 40;
                        break;
                    }
                }
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
                
                if(solving)
                {
                    JOptionPane.showMessageDialog(frame,"Algorithm is currently running!"); 
                }
                else
                {
                    createBoard();
                    gridArea.repaint();
                    srcX = -1;
                    srcY = -1;
                    destX = -1;
                    destY = -1;
                }
			}
        });

        clearPathButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

                if(solving)
                {
                    JOptionPane.showMessageDialog(frame,"Algorithm is currently running!"); 
                }
                else
                {
                    clearPath();
				    gridArea.repaint();
                }
			}
        });
        

        stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

                solving = false;
			}
        });

        // continueButton.addActionListener(new ActionListener() {
		// 	@Override
		// 	public void actionPerformed(ActionEvent e) {

        //         if(solving == false)
        //         {
        //             solving = true;
        //         }
        //         else
        //         {
        //             solving = false;
        //         }
		// 	}
        // });

        visualizeButton.addActionListener(new ActionListener() {
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
                            break;
                        }

                        case 2 :
                        {
                            AStarAlgo();
                            break;
                        }
                    }
        }
        pause();
    }

    public void pause() {
		int i = 0;
		while(!solving) {
			i++;
			if(i > 500)
				i = 0;
			try {
				Thread.sleep(1);
			} catch(Exception e) {}
		}
		startSearch();
	}


    public void DijkstraAlgo()
    {
        // if(algoInProcess == false)
        // {
            if(srcX == destX && srcY == destY)
            {
                distValueLabel.setText("0 Units");
                solving = false;
                completed = false;
                return;
            }

        ArrayList<Cell> q = new ArrayList<>();
            // q = new ArrayList<>();
            q.add(grid[srcX][srcY]);
        //     algoInProcess = true;
        // }
        // Queue<Cell> q = new LinkedList<>();
        // q.add(grid[srcX][srcY]);
         
        while(solving && !q.isEmpty())
        {
            Cell temp = q.remove(0);
            int d = temp.getDist();

            ArrayList<Cell> extra = addNeighbors(q,temp,d);
            if(extra.size() > 0)
            {
                q.addAll(extra);
                gridArea.repaint();
                delay();
                
            }
        }
        solving = false;

        if(!completed)
        {
            distValueLabel.setText("No path found");
        }
        completed = false;
        // if(pauseAlgo == false)
        // {
        //     algoInProcess = false;
        // }
    }


    public void AStarAlgo()
    {
        if(srcX == destX && srcY == destY)
            {
                distValueLabel.setText("0 Units");
                solving = false;
                completed = false;
                return;
            }
        
        ArrayList<Cell> q = new ArrayList<>();
        q.add(grid[srcX][srcY]);
         
        while(solving && !q.isEmpty())
        {
            Cell temp = q.remove(0);
            int d = temp.getDist();

            ArrayList<Cell> extra = addNeighbors(q,temp,d);
            if(extra.size() > 0)
            {
                q.addAll(extra);
                gridArea.repaint();
                delay();
            }
            q = sort(q);
        }
        solving = false;

        if(!completed)
        {
            distValueLabel.setText("No path found");
        }
        completed = false;
    }


    public static ArrayList<Cell> sort(ArrayList<Cell> al)
    {
        // ArrayList<Cell> al = new ArrayList<>(q);
        int n = al.size();
        for(int i=0 ; i<n ; i++)
        {
            for(int j=i+1 ; j<n ; j++)
            {
                if(totalDistance(al.get(j)) < totalDistance(al.get(i)))
                {
                    Cell x = al.get(i);
                    al.set(i,al.get(j));
                    al.set(j,x);
                }
            }
        }
        // q = new LinkedList<>(al);
        return al;
    }

    public static double totalDistance(Cell curCell)
    {
        int travelledDist = curCell.getDist();
        double distanceToBeTravelled = curCell.getheuristicDist();
        return travelledDist + distanceToBeTravelled;
    }


    public void delay() {
		try {
			Thread.sleep(speed);
		} catch(Exception e) {}
	}

    public ArrayList<Cell> addNeighbors(ArrayList<Cell> q,Cell temp,int dist)
    {
        ArrayList<Cell> q2 = new ArrayList<>();

        // for(int i=-1 ; i<2 ; i++)
        // {
        //     for(int j=-1 ; j<2 ; j++)
        //     {

        int xValue = 0;
        int yValue = -1; 
        for(int k=0 ; k<4 ; k++)
        {
                int newX = temp.getX()+xValue;
                int newY = temp.getY()+yValue;
                
                if(onBoard(newX,newY))
                {
                    Cell curCell = grid[newX][newY];
                    if(newX == destX && newY == destY)
                    {
                        int count = 1;
                        int x = temp.getX();
                        int y = temp.getY();

                        while(onBoard(x, y) && grid[x][y].getType() != 1)
                        {
                            count++;
                            // System.out.println(x+","+y);
                            grid[x][y].setType(5);
                            int x2 = grid[x][y].getPrevX();
                            int y2 = grid[x][y].getPrevY();
                            x = x2;
                            y = y2;
                            gridArea.repaint();
                            delay();
                        }
                        completed = true;
                        algoInProcess = false;
                        String value = Integer.toString(count);
                        distValueLabel.setText(value+" Units");
                        
                        // System.out.println(x+","+y);

                        solving = false;
                        return q2;
                    }
                    else if(curCell.getType() == 0)
                    {
                        q2.add(curCell);
                        curCell.setType(6);
                        gridArea.repaint();
                        
                        curCell.setDist(dist+1);
                        curCell.setPrevCell(temp.getX(),temp.getY());
                        if(curCell.getType() != 1 && temp.getType() != 1)
                        {
                            Cell prevCell = grid[temp.getX()][temp.getY()];
                            prevCell.setType(4);
                        } 
                        
                    }
                    else if(curCell.getType() == 4 && curCell.getDist() >= dist+1)
                    {
                        curCell.setDist(dist+1);
                        curCell.setPrevCell(temp.getX(),temp.getY());
                    }
                
                }

                if(k%2 == 0)
                {
                    int temp1 = xValue;
                    xValue = yValue;
                    yValue = temp1;
                }
                else
                {
                    xValue = xValue * -1;
                    yValue = yValue * -1;
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
        
        public double getheuristicDist() {
            
            int xDiff = Math.abs(x - destX);
            int yDiff = Math.abs(y - destY);
            int xSquare = xDiff*xDiff;
            int ySquare = yDiff*yDiff;
            double res = Math.sqrt(xSquare + ySquare);
            return res;

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
							g.setColor(lightColor);
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
							g.setColor(Color.lightGray);
							break;
						case 5:
							g.setColor(Color.YELLOW);
                            break;
                        case 6:
							g.setColor(Color.lightGray);
							break;
					}
					g.fillRect(i*cellSize,j*cellSize,cellSize,cellSize);
                    // g.setColor(Color.BLACK);
                    g.setColor(darkColor);
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
                        }
                        // else if(grid[x][y].getType() == 1)
                        // {

                        // }
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

    




    public class RoundButton extends JButton {
 
        String text;
        private int x;
        private int y;
        private int width;
        private int height;

        public RoundButton(String label) {
        //   super(label);
          text = label;
          setBackground(new Color(47, 149, 154));        
          //   setFocusable(false);
       
        }

        public void setPosition(int x,int y,int width,int height)
        {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            setBounds(x, y, width, height);
        }
       
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            
        // //   if (getModel().isArmed()) {
            // g.setColor(Color.RED);
        // //   } else {
            // g.setColor(getBackground());
        // //   }
        //     // g.setBackground(new Color(0f,0f,0f,0f ));
        // //   g.drawRoundRect(100, 10, 80, 30, 115, 155);
        // g.drawRoundRect(0, 0, 120, 25, 10, 10);
        // g.setColor(Color.GREEN);
        // g.fillRoundRect( 0, 0, 110, 35, 15, 15 );
        // g.drawRoundRect( 0, 0, 110, 35, 15, 15 );



        Dimension d = this.getSize();
        int w = (int)d.getWidth();
        int h = (int)d.getHeight();

    //     FontMetrics metrics = g.getFontMetrics(font);
    // // Determine the X coordinate for the text
    // int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
    // // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
    // int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(new Font("Comic Sans MS", Font.PLAIN, height*width/290)); 
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(text)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);

        
        
        // g.fill3DRect(0,0,120,25,true); 
        // setForeground(new Color(234, 170, 142))
        g.setColor(lightColor);
        g.drawString(text, x, y);
        
       
          
        }
       
        protected void paintBorder(Graphics g) {
          g.setColor(Color.black);
        //   g.setBackground(new Color(0f,0f,0f,0f ));
          g.drawRoundRect(0, 0, width, height, 10, 10);

          int thickness = 2;
          for (int i = 1; i <= thickness; i++)
          g.drawRoundRect(0+i, 0+i, width - 2*i, height - 2*i, 10, 10);

        //     g.fill3DRect(0,0,120,25,true); 
        }
       
        
       
      }


    

};



