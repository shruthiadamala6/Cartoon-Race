package com.assignment4;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//import sun.audio.*;

import java.io.*;


public class ActionPanel extends JPanel{

	private static final long serialVersionUID = 8549418363445304981L;
	
	private JLabel winnerNameLabel;
	private JButton callButton;
    private JButton raceStartButton;
    
    private DashboardFrame mainFrame;
    
    private RunRace runRace;
    private Thread thread;
   
    private Clip clip = null;
    
    public ActionPanel(DashboardFrame mainFrame){
    	this.mainFrame = mainFrame;
    	createPanels();
    }
    
    public void createPanels(){
    	this.setLayout(new GridLayout(1, 3));
    	
    	
    	callButton = new JButton("Call Players Back");
    	callButton.setBackground(Color.cyan);
    	callButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	runRace.setIsRunning(false);
            	mainFrame.reset();
            	mainFrame.getTrackPanel().repaint();
            	mainFrame.getActionPanel().getWinnerNameLabel().setText("    Race Game    ");
            	clip.stop();
            	mainFrame.setAllSmiling();
            }
        });

          
    	winnerNameLabel = new JLabel("      Race Game    ");
    	winnerNameLabel.setFont(winnerNameLabel.getFont().deriveFont(20.0F));
    	
    	
    	raceStartButton = new JButton("Start Race");
    	raceStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	//start race logic
            	
            	if(runRace == null || !thread.isAlive()){
	            	runRace = new RunRace(mainFrame, mainFrame.getTrackPanel());
	            	thread = new Thread(runRace);
	            	thread.start();
	            	addMusic();
            	}
            }
        });
    	
    	GridBagConstraints constraint = new GridBagConstraints();
    	
    	constraint.gridx = 0;
    	constraint.gridy = 0;
    	constraint.fill = GridBagConstraints.WEST;
    	this.add(callButton, constraint);
    	
    	constraint = new GridBagConstraints();
    	constraint.gridx = 0;
    	constraint.gridy = 1;
    	constraint.fill = GridBagConstraints.CENTER;
    	this.add(winnerNameLabel, constraint);
    	
    	constraint = new GridBagConstraints();
    	constraint.gridx = 0;
    	constraint.gridy = 2;
    	constraint.fill = GridBagConstraints.EAST;
    	this.add(raceStartButton, constraint);
    	this.setBackground(Color.LIGHT_GRAY);
    }
 
    public JLabel getWinnerNameLabel(){
    	clip.stop();
    	return this.winnerNameLabel;
    }
    
   
    public  void addMusic(){
    	
    	try {
    		
    		clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("croc.wav"));
            clip.open(inputStream);
            clip.loop(2);
            clip.start();
            
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} 
    	
    }
    

}