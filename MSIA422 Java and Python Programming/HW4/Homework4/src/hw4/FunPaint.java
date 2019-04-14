package hw4;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Scanner;

import javax.swing.JFrame;

import javax.swing.JComponent;

@SuppressWarnings("serial")

public class FunPaint {
	static class BadNumberException extends Exception {
		BadNumberException() {
		}

	}

	public static void draw(Graphics g) {
		Scanner in = new Scanner(System.in);
		boolean nonstop = true;
		
			while (nonstop) {
				try {
					System.out.println("Enter Grid Dimension:");
					int GridDimension = 6;
					GridDimension = in.nextInt();
					// if the user input is an odd number or negative number, raise BadNumberException
					if (GridDimension % 2 == 1 || GridDimension < 0) {
						throw new BadNumberException();
					} else {
						for (int row = 0; row < GridDimension / 2; row++) {
							// set the color of small circles in the second quadrant to be green
							for (int column = 0; column < GridDimension / 2; column++) {
								g.setColor(Color.GREEN);
								g.fillOval(row * 60 + 50, column * 60 + 50, 50, 50);
							}
							// set the color of small circles in the first quadrant to be black
							for (int column = GridDimension / 2; column < GridDimension; column++) {
								g.setColor(Color.BLACK);
								g.fillOval(row * 60 + 50, column * 60 + 50, 50, 50);
							}
						}
						for (int row = GridDimension / 2; row < GridDimension; row++) {
							// set the color of small circles in the third quadrant to be black
							for (int column = 0; column < GridDimension / 2; column++) {
								g.setColor(Color.BLACK);
								g.fillOval(row * 60 + 50, column * 60 + 50, 50, 50);
						
							}
							// set the color of small circles in the fourth quadrant to be red
							for (int column = GridDimension / 2; column < GridDimension; column++) {
								g.setColor(Color.RED);
								g.fillOval(row * 60 + 50, column * 60 + 50, 50, 50);
							}
						}
						nonstop = false;
					}
					// catch invalid odd number inputs
				} catch (BadNumberException e) {
					System.out.println("Warning: Please enter a positive even integer!");

				} catch (Exception e) {
					System.out.println("Warning: This is not a valid entry!\n" + "Please enter a positive even integer!");
					in.next();
				}
				continue;
			}
		} 
	

	public static void main(String[] args) {
		JFrame frame = new JFrame();

		final int FRAME_WIDTH = 800;
		final int FRAME_HEIGHT = 800;

		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		JComponent component = new JComponent() {
			public void paintComponent(Graphics graph) {
				draw(graph);
				}
				
			
	
		};frame.add(component);frame.setVisible(true);
}}
