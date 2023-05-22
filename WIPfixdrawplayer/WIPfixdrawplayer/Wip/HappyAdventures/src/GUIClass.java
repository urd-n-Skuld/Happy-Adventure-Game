import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GUIClass
{
    int menuWidth = 350, menuHeight = 450;
    String menuName;  //'MainMenu', 'GameOverMenu', 'YouWinMenu', 'PauseMenu'
    JLayeredPane layeredPane = new JLayeredPane();    //This is the main container for all menus
    JPanel MainMenuPanel = new JPanel();
    JPanel MMbuttonPanel = new JPanel();


    public void setupGUI(HappyAdventuresGame game, JFrame frame, GameEngine.GamePanel mPanel, String gameStates)
    {
        System.out.println(gameStates);
        frame.repaint();
        frame.setTitle("Happy's Adventures");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.add(mPanel, BorderLayout.CENTER);

        //Load the images for all the menuPanels and buttons
        Image MMBackgroundImage = game.loadImage("images/Menus/MainMenu.png");
        ImageIcon MMplayButtonIm = new ImageIcon("images/Menus/Play.png");
        ImageIcon MMplayHoverButtonIm = new ImageIcon("images/Menus/PlayHover.png");
        ImageIcon MMquitButtonIm = new ImageIcon("images/Menus/Quit.png");
        ImageIcon MMquitHoverButtonIm = new ImageIcon("images/Menus/QuitHover.png");
        ImageIcon MMcreditsButtonIm = new ImageIcon("images/Menus/Credits.png");
        ImageIcon MMcreditsHoverButtonIm = new ImageIcon("images/Menus/CreditsHover.png");
        ImageIcon MM2playerButtonIm = new ImageIcon("images/Menus/2Player.png");
        ImageIcon MM2playerHoverButtonIm = new ImageIcon("images/Menus/2PlayerHover.png");

        //Create the buttons for each of the menus
        JButton MMplayButton = new JButton();
        JButton MMquitButton = new JButton();
        JButton MMcreditsButton = new JButton();
        JButton MM2playerButton = new JButton();

        //Set images for each of the buttons
        MMplayButton.setIcon(MMplayButtonIm);
        MMplayButton.setRolloverIcon(MMplayHoverButtonIm);
        MMquitButton.setIcon(MMquitButtonIm);
        MMquitButton.setRolloverIcon(MMquitHoverButtonIm);
        MMcreditsButton.setIcon(MMcreditsButtonIm);
        MMcreditsButton.setRolloverIcon(MMcreditsHoverButtonIm);
        MM2playerButton.setIcon(MM2playerButtonIm);
        MM2playerButton.setRolloverIcon(MM2playerHoverButtonIm);

        //Settings for each of the buttons
        MMplayButton.setBorder(null);
        MMplayButton.setContentAreaFilled(false);
        MMplayButton.setFocusPainted(false);
        MMplayButton.setPreferredSize(new Dimension(MMplayButtonIm.getIconWidth(), MMplayButtonIm.getIconHeight()));
        MMquitButton.setBorder(null);
        MMquitButton.setContentAreaFilled(false);
        MMquitButton.setFocusPainted(false);
        MMquitButton.setPreferredSize(new Dimension(MMquitButtonIm.getIconWidth(), MMquitButtonIm.getIconHeight()));
        MMcreditsButton.setBorder(null);
        MMcreditsButton.setContentAreaFilled(false);
        MMcreditsButton.setFocusPainted(false);
        MMcreditsButton.setPreferredSize(new Dimension(MMcreditsButtonIm.getIconWidth(), MMcreditsButtonIm.getIconHeight()));
        MM2playerButton.setBorder(null);
        MM2playerButton.setContentAreaFilled(false);
        MM2playerButton.setFocusPainted(false);
        MM2playerButton.setPreferredSize(new Dimension(MM2playerButtonIm.getIconWidth(), MM2playerButtonIm.getIconHeight()));

        // Add the buttons to the Main Menu button panel
        MMbuttonPanel.add(Box.createVerticalGlue());        //create vertical spacing between each button
        MMbuttonPanel.add(MMplayButton);
        MMbuttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // add some transparent space between the buttons of specific size
        MMbuttonPanel.add(MM2playerButton);
        MMbuttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // add some transparent space between the buttons of specific size
        MMbuttonPanel.add(MMcreditsButton);
        MMbuttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // add some transparent space between the buttons of specific size
        MMbuttonPanel.add(MMquitButton);
        MMbuttonPanel.add(Box.createVerticalGlue());        //create vertical spacing between each button

        //Set the location of the button panel
        MMbuttonPanel.setBounds((game.width() - MMplayButtonIm.getIconWidth()) / 2, 80, MMBackgroundImage.getWidth(null), MMBackgroundImage.getHeight(null));

        //Set the background images for each of the menu panels
        MainMenuPanel.add(new JLabel(new ImageIcon(MMBackgroundImage))); // add the image to a label and add the label to the panel

        //Set the settings for each menu panel
        MainMenuPanel.setBackground(new Color(0, 0, 0, 0)); // set background color to transparent
        MainMenuPanel.setOpaque(false); // make the panel transparent
        MainMenuPanel.setBounds((game.width() - menuWidth) / 2, (game.height() - menuHeight) /2, menuWidth, menuHeight);
        MMbuttonPanel.setOpaque(false);
        MMbuttonPanel.setLayout(new BoxLayout(MMbuttonPanel, BoxLayout.PAGE_AXIS)); // set layout to place buttons in a column

        //Add all the menu panels to the layered pane of the frame
        layeredPane.add(mPanel);
        layeredPane.setLayer(mPanel, 0);
        layeredPane.add(MainMenuPanel); //1
        layeredPane.setLayer(MainMenuPanel, 1);
        layeredPane.add(MMbuttonPanel); //2
        layeredPane.setLayer(MMbuttonPanel, 2);

        //Adding the layeredPane to the mFrame
        frame.setContentPane(layeredPane);

        //Set the panels to visible / invisible
        mPanel.setVisible(true);
        frame.setVisible(true);
        layeredPane.setVisible(true);

        if(Objects.equals(gameStates, "MenuSystem"))
        {
            MainMenuPanel.setVisible(true);
            MMbuttonPanel.setVisible(true);
        }
        else
        {
            MainMenuPanel.setVisible(false);
            MMbuttonPanel.setVisible(false);
        }

        //Set the keyListeners for each of the buttons in the different menu screens
        MMplayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //System.out.println("Main Menu play button pressed");
                //game.gameOver = false;
                //game.gameStates = "PlayGame";
                //game.score = 0;
                MainMenuPanel.setVisible(false);
                MMbuttonPanel.setVisible(false);
            }
        });

        //Set the keyListeners for each of the buttons in the different menu screens
        MM2playerButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            { //System.out.println("Main Menu play button pressed");
                //game.gameOver = false;
                //game.gameStates = "2Player";
                //game.score = 0; //Add a new score for 2nd player
                MainMenuPanel.setVisible(false);
                MMbuttonPanel.setVisible(false);
            }
        });

        MMcreditsButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            { //System.out.println("Main Menu endurance button pressed");
                //game.gameOver = false;
                //game.gameStates = "Endurance";
                //game.score = 0;
                // game.reset();
                MainMenuPanel.setVisible(false);
                MMbuttonPanel.setVisible(false);
            }
        });

        MMquitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
    }
}