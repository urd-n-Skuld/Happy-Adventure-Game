import javax.security.auth.login.CredentialException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GUIClass
{
    int menuWidth = 350, menuHeight = 450;
    JLayeredPane layeredPane = new JLayeredPane();    //This is the main container for all menus
    JPanel MainMenuPanel = new JPanel();
    JPanel PauseMenuPanel = new JPanel();
    JPanel CreditsMenuPanel = new JPanel();
    JPanel HowToPlayMenuPanel = new JPanel();
    JPanel QuitMenuPanel = new JPanel();
    JPanel RetryMenuPanel = new JPanel();
    JPanel SuperSweetTutorialMenuPanel = new JPanel();
    JPanel foundKeyTutorialMenuPanel = new JPanel();
    JPanel MMbuttonPanel = new JPanel();
    JPanel PAbuttonPanel = new JPanel();
    JPanel QUbuttonPanel = new JPanel();
    JPanel RTbuttonPanel = new JPanel();
    JPanel HTPbuttonPanel = new JPanel();
    JPanel CRbuttonPanel = new JPanel();
    //JPanel SuperSweetTutorialbuttonPanel = new JPanel();


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
        Image MMBackgroundImage = game.loadImage("images/Menus/MainMenuBG.png");
        Image CRBackgroundImage = game.loadImage("images/Menus/CreditsMenuBG.png");
        Image HTPBackgroundImage = game.loadImage("images/Menus/HowToPlayMenuBG.png");
        Image PABackgroundImage = game.loadImage("images/Menus/PauseMenuBG.png");
        Image QUBackgroundImage = game.loadImage("images/Menus/QuitMenuBG.png");
        Image RTBackgroundImage = game.loadImage("images/Menus/RetryMenuBG.png");
        Image SSTBackgroundImage = game.loadImage("images/Menus/supersweetpopup.png");
        Image FFKBackgroundImage = game.loadImage("images/Menus/keypopup.png");
        ImageIcon playButtonIm = new ImageIcon("images/Menus/PlayBTN.png");
        ImageIcon playHoverButtonIm = new ImageIcon("images/Menus/PlayHoverBTN.png");
        ImageIcon quitButtonIm = new ImageIcon("images/Menus/QuitBTN.png");
        ImageIcon quitHoverButtonIm = new ImageIcon("images/Menus/QuitHoverBTN.png");
        ImageIcon creditsButtonIm = new ImageIcon("images/Menus/CreditsBTN.png");
        ImageIcon creditsHoverButtonIm = new ImageIcon("images/Menus/CreditsHoverBTN.png");
        //ImageIcon multiPlayerButtonIm = new ImageIcon("images/Menus/MultiPlayerBTN.png");
        //ImageIcon multiPlayerHoverButtonIm = new ImageIcon("images/Menus/multiPlayerHoverBTN.png");
        ImageIcon noButtonIm = new ImageIcon("images/Menus/NoBTN.png");
        ImageIcon noHoverButtonIm = new ImageIcon("images/Menus/NoHoverBTN.png");
        ImageIcon yesButtonIm = new ImageIcon("images/Menus/YesBTN.png");
        ImageIcon yesHoverButtonIm = new ImageIcon("images/Menus/YesHoverBTN.png");
        ImageIcon continueButtonIm = new ImageIcon("images/Menus/continueBTN.png");
        ImageIcon continueHoverButtonIm = new ImageIcon("images/Menus/continueHoverBTN.png");
        ImageIcon mainMenuButtonIm = new ImageIcon("images/Menus/MainMenuBTN.png");
        ImageIcon mainMenuHoverButtonIm = new ImageIcon("images/Menus/MainMenuHoverBTN.png");
        ImageIcon readyButtonIm = new ImageIcon("images/Menus/imreadyBTN.png");
        ImageIcon readyHoverButtonIm = new ImageIcon("images/Menus/imReadyHoverBTN.png");
        ImageIcon resumeButtonIm = new ImageIcon("images/Menus/resumeBTN.png");
        ImageIcon resumeHoverButtonIm = new ImageIcon("images/Menus/resumeHoverBTN.png");

        //Create the buttons for each of the menus
        JButton MMplayButton = new JButton();
        JButton HTPreadyButton = new JButton();
        JButton MMquitButton = new JButton();
        JButton PAquitButton = new JButton();
        JButton creditsButton = new JButton();
        //JButton multiPlayerButton = new JButton();
        JButton RTnoButton = new JButton();
        JButton QUnoButton = new JButton();
        JButton RTyesButton = new JButton();
        JButton QUyesButton = new JButton();
        JButton CRmainMenuButton = new JButton();
        JButton PAresumeButton = new JButton();
        JButton SSTresumeButton = new JButton();

        //Set images for each of the buttons
        MMplayButton.setIcon(playButtonIm);
        MMplayButton.setRolloverIcon(playHoverButtonIm);
        HTPreadyButton.setIcon(readyButtonIm);
        HTPreadyButton.setRolloverIcon(readyHoverButtonIm);
        MMquitButton.setIcon(quitButtonIm);
        MMquitButton.setRolloverIcon(quitHoverButtonIm);
        PAquitButton.setIcon(quitButtonIm);
        PAquitButton.setRolloverIcon(quitHoverButtonIm);
        creditsButton.setIcon(creditsButtonIm);
        creditsButton.setRolloverIcon(creditsHoverButtonIm);
        //multiPlayerButton.setIcon(multiPlayerButtonIm);
        //multiPlayerButton.setRolloverIcon(multiPlayerHoverButtonIm);
        RTnoButton.setIcon(noButtonIm);
        RTnoButton.setRolloverIcon(noHoverButtonIm);
        QUnoButton.setIcon(noButtonIm);
        QUnoButton.setRolloverIcon(noHoverButtonIm);
        RTyesButton.setIcon(yesButtonIm);
        RTyesButton.setRolloverIcon(yesHoverButtonIm);
        QUyesButton.setIcon(yesButtonIm);
        QUyesButton.setRolloverIcon(yesHoverButtonIm);
        CRmainMenuButton.setIcon(mainMenuButtonIm);
        CRmainMenuButton.setRolloverIcon(mainMenuHoverButtonIm);
        PAresumeButton.setIcon(resumeButtonIm);
        PAresumeButton.setRolloverIcon(resumeHoverButtonIm);
        SSTresumeButton.setIcon(resumeButtonIm);
        SSTresumeButton.setRolloverIcon(resumeHoverButtonIm);

        //Settings for each of the buttons
        MMplayButton.setBorder(null);
        MMplayButton.setContentAreaFilled(false);
        MMplayButton.setFocusPainted(false);
        MMplayButton.setPreferredSize(new Dimension(playButtonIm.getIconWidth(), playButtonIm.getIconHeight()));
        HTPreadyButton.setBorder(null);
        HTPreadyButton.setContentAreaFilled(false);
        HTPreadyButton.setFocusPainted(false);
        HTPreadyButton.setPreferredSize(new Dimension(readyButtonIm.getIconWidth(), readyButtonIm.getIconHeight()));
        MMquitButton.setBorder(null);
        MMquitButton.setContentAreaFilled(false);
        MMquitButton.setFocusPainted(false);
        MMquitButton.setPreferredSize(new Dimension(quitButtonIm.getIconWidth(), quitButtonIm.getIconHeight()));
        PAquitButton.setBorder(null);
        PAquitButton.setContentAreaFilled(false);
        PAquitButton.setFocusPainted(false);
        PAquitButton.setPreferredSize(new Dimension(quitButtonIm.getIconWidth(), quitButtonIm.getIconHeight()));
        creditsButton.setBorder(null);
        creditsButton.setContentAreaFilled(false);
        creditsButton.setFocusPainted(false);
        creditsButton.setPreferredSize(new Dimension(creditsButtonIm.getIconWidth(), creditsButtonIm.getIconHeight()));
        //multiPlayerButton.setBorder(null);
        //multiPlayerButton.setContentAreaFilled(false);
        //multiPlayerButton.setFocusPainted(false);
        //multiPlayerButton.setPreferredSize(new Dimension(multiPlayerButtonIm.getIconWidth(), multiPlayerButtonIm.getIconHeight()));
        RTnoButton.setBorder(null);
        RTnoButton.setContentAreaFilled(false);
        RTnoButton.setFocusPainted(false);
        RTnoButton.setPreferredSize(new Dimension(noButtonIm.getIconWidth(), noButtonIm.getIconHeight()));
        QUnoButton.setBorder(null);
        QUnoButton.setContentAreaFilled(false);
        QUnoButton.setFocusPainted(false);
        QUnoButton.setPreferredSize(new Dimension(noButtonIm.getIconWidth(), noButtonIm.getIconHeight()));
        RTyesButton.setBorder(null);
        RTyesButton.setContentAreaFilled(false);
        RTyesButton.setFocusPainted(false);
        RTyesButton.setPreferredSize(new Dimension(yesButtonIm.getIconWidth(), yesButtonIm.getIconHeight()));
        QUyesButton.setBorder(null);
        QUyesButton.setContentAreaFilled(false);
        QUyesButton.setFocusPainted(false);
        QUyesButton.setPreferredSize(new Dimension(yesButtonIm.getIconWidth(), yesButtonIm.getIconHeight()));
        CRmainMenuButton.setBorder(null);
        CRmainMenuButton.setContentAreaFilled(false);
        CRmainMenuButton.setFocusPainted(false);
        CRmainMenuButton.setPreferredSize(new Dimension(mainMenuButtonIm.getIconWidth(), mainMenuButtonIm.getIconHeight()));
        PAresumeButton.setBorder(null);
        PAresumeButton.setContentAreaFilled(false);
        PAresumeButton.setFocusPainted(false);
        PAresumeButton.setPreferredSize(new Dimension(resumeButtonIm.getIconWidth(), resumeButtonIm.getIconHeight()));
        SSTresumeButton.setBorder(null);
        SSTresumeButton.setContentAreaFilled(false);
        SSTresumeButton.setFocusPainted(false);
        SSTresumeButton.setPreferredSize(new Dimension(resumeButtonIm.getIconWidth(), resumeButtonIm.getIconHeight()));

        // Add the buttons to the Main Menu button panel
        MMbuttonPanel.add(Box.createVerticalGlue());        //create vertical spacing between each button
        MMbuttonPanel.add(MMplayButton);
        //MMbuttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // add some transparent space between the buttons of specific size
        //MMbuttonPanel.add(multiPlayerButton);
        MMbuttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // add some transparent space between the buttons of specific size
        MMbuttonPanel.add(creditsButton);
        MMbuttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // add some transparent space between the buttons of specific size
        MMbuttonPanel.add(MMquitButton);
        MMbuttonPanel.add(Box.createVerticalGlue());        //create vertical spacing between each button

        // Add the buttons to the Pause Menu button panel
        PAbuttonPanel.add(Box.createVerticalGlue());        //create vertical spacing between each button
        PAbuttonPanel.add(PAresumeButton);
        PAbuttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // add some transparent space between the buttons of specific size
        PAbuttonPanel.add(PAquitButton);
        PAbuttonPanel.add(Box.createVerticalGlue());        //create vertical spacing between each button

        // Add the buttons to the How To Play Menu button panel
        HTPbuttonPanel.add(Box.createVerticalGlue());        //create vertical spacing between each button
        HTPbuttonPanel.add(HTPreadyButton);
        HTPbuttonPanel.add(Box.createVerticalGlue());        //create vertical spacing between each button

        // Add the buttons to the Credits Menu button panel
        CRbuttonPanel.add(Box.createVerticalGlue());        //create vertical spacing between each button
        CRbuttonPanel.add(CRmainMenuButton);
        CRbuttonPanel.add(Box.createVerticalGlue());        //create vertical spacing between each button

        // Add the buttons to the Quit Menu button panel
        QUbuttonPanel.add(Box.createVerticalGlue());        //create vertical spacing between each button
        QUbuttonPanel.add(QUyesButton);
        QUbuttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // add some transparent space between the buttons of specific size
        QUbuttonPanel.add(QUnoButton);
        QUbuttonPanel.add(Box.createVerticalGlue());        //create vertical spacing between each button

        // Add the buttons to the Quit Menu button panel
        RTbuttonPanel.add(Box.createVerticalGlue());        //create vertical spacing between each button
        RTbuttonPanel.add(RTyesButton);
        RTbuttonPanel.add(Box.createRigidArea(new Dimension(0, 15))); // add some transparent space between the buttons of specific size
        RTbuttonPanel.add(RTnoButton);
        RTbuttonPanel.add(Box.createVerticalGlue());        //create vertical spacing between each button

        /*// Add the buttons to the Quit Menu button panel
        SuperSweetTutorialbuttonPanel.add(Box.createVerticalGlue());        //create vertical spacing between each button
        SuperSweetTutorialbuttonPanel.add(SSTresumeButton);
        SuperSweetTutorialbuttonPanel.add(Box.createVerticalGlue());        //create vertical spacing between each button*/

        //Set the location of the button panel
        MMbuttonPanel.setBounds((game.width() - playButtonIm.getIconWidth()) / 2, 100, MMBackgroundImage.getWidth(null), MMBackgroundImage.getHeight(null));
        CRbuttonPanel.setBounds((game.width() - continueButtonIm.getIconWidth()) / 2, 80, CRBackgroundImage.getWidth(null), CRBackgroundImage.getHeight(null));
        HTPbuttonPanel.setBounds((game.width() - readyButtonIm.getIconWidth()) / 2, 215, HTPBackgroundImage.getWidth(null), HTPBackgroundImage.getHeight(null));
        PAbuttonPanel.setBounds((game.width() - continueButtonIm.getIconWidth()) / 2, 130, PABackgroundImage.getWidth(null), PABackgroundImage.getHeight(null));
        QUbuttonPanel.setBounds((game.width() - yesButtonIm.getIconWidth()) / 2, 120, QUBackgroundImage.getWidth(null), QUBackgroundImage.getHeight(null));
        RTbuttonPanel.setBounds((game.width() - yesButtonIm.getIconWidth()) / 2, 120, RTBackgroundImage.getWidth(null), RTBackgroundImage.getHeight(null));
        //SuperSweetTutorialbuttonPanel.setBounds((game.width() - resumeButtonIm.getIconWidth()) / 2, 180, SSTBackgroundImage.getWidth(null), SSTBackgroundImage.getHeight(null));

        //Set the background images for each of the menu panels
        MainMenuPanel.add(new JLabel(new ImageIcon(MMBackgroundImage))); // add the image to a label and add the label to the panel
        CreditsMenuPanel.add(new JLabel(new ImageIcon(CRBackgroundImage))); // add the image to a label and add the label to the panel
        HowToPlayMenuPanel.add(new JLabel(new ImageIcon(HTPBackgroundImage))); // add the image to a label and add the label to the panel
        PauseMenuPanel.add(new JLabel(new ImageIcon(PABackgroundImage))); // add the image to a label and add the label to the panel
        QuitMenuPanel.add(new JLabel(new ImageIcon(QUBackgroundImage))); // add the image to a label and add the label to the panel
        RetryMenuPanel.add(new JLabel(new ImageIcon(RTBackgroundImage))); // add the image to a label and add the label to the panel
        SuperSweetTutorialMenuPanel.add(new JLabel(new ImageIcon(SSTBackgroundImage))); // add the image to a label and add the label to the panel
        foundKeyTutorialMenuPanel.add(new JLabel(new ImageIcon(FFKBackgroundImage))); // add the image to a label and add the label to the panel

        //Set the settings for each menu panel - Main Menu
        MainMenuPanel.setBackground(new Color(0, 0, 0, 0)); // set background color to transparent
        MainMenuPanel.setOpaque(false); // make the panel transparent
        MainMenuPanel.setBounds(0,0, game.frameWidth,game.frameHeight);
        MMbuttonPanel.setOpaque(false);
        MMbuttonPanel.setLayout(new BoxLayout(MMbuttonPanel, BoxLayout.PAGE_AXIS)); // set layout to place buttons in a column

        //Set the settings for each menu panel - How To Play Menu
        HowToPlayMenuPanel.setBackground(new Color(0, 0, 0, 0)); // set background color to transparent
        HowToPlayMenuPanel.setOpaque(false); // make the panel transparent
        HowToPlayMenuPanel.setBounds(0, 0, game.frameWidth, game.frameHeight);
        HTPbuttonPanel.setOpaque(false);
        HTPbuttonPanel.setLayout(new BoxLayout(HTPbuttonPanel, BoxLayout.PAGE_AXIS)); // set layout to place buttons in a column

        //Set the settings for each menu panel - Credits Menu
        CreditsMenuPanel.setBackground(new Color(0, 0, 0, 0)); // set background color to transparent
        CreditsMenuPanel.setOpaque(false); // make the panel transparent
        CreditsMenuPanel.setBounds(0, 0, game.frameWidth, game.frameHeight);
        CRbuttonPanel.setOpaque(false);
        CRbuttonPanel.setLayout(new BoxLayout(CRbuttonPanel, BoxLayout.PAGE_AXIS)); // set layout to place buttons in a column

        //Set the settings for each menu panel - Pause Menu
        PauseMenuPanel.setBackground(new Color(0, 0, 0, 0)); // set background color to transparent
        PauseMenuPanel.setOpaque(false); // make the panel transparent
        PauseMenuPanel.setBounds(0,0,game.frameWidth, game.frameHeight);
        PAbuttonPanel.setOpaque(false);
        PAbuttonPanel.setLayout(new BoxLayout(PAbuttonPanel, BoxLayout.PAGE_AXIS)); // set layout to place buttons in a column

        //Set the settings for each menu panel - Quit Menu
        QuitMenuPanel.setBackground(new Color(0, 0, 0, 0)); // set background color to transparent
        QuitMenuPanel.setOpaque(false); // make the panel transparent
        QuitMenuPanel.setBounds((game.width() - menuWidth) / 2, (game.height() - menuHeight) /2, menuWidth, menuHeight);
        QUbuttonPanel.setOpaque(false);
        QUbuttonPanel.setLayout(new BoxLayout(QUbuttonPanel, BoxLayout.PAGE_AXIS)); // set layout to place buttons in a column

        //Set the settings for each menu panel - Retry Menu
        RetryMenuPanel.setBackground(new Color(0, 0, 0, 0)); // set background color to transparent
        RetryMenuPanel.setOpaque(false); // make the panel transparent
        RetryMenuPanel.setBounds((game.width() - menuWidth) / 2, (game.height() - menuHeight) /2, menuWidth, menuHeight);
        RTbuttonPanel.setOpaque(false);
        RTbuttonPanel.setLayout(new BoxLayout(RTbuttonPanel, BoxLayout.PAGE_AXIS)); // set layout to place buttons in a column

        //Set the settings for each menu panel - SuperSweetTutorial Menu
        SuperSweetTutorialMenuPanel.setBackground(new Color(0, 0, 0, 0)); // set background color to transparent
        SuperSweetTutorialMenuPanel.setOpaque(false); // make the panel transparent
        //SuperSweetTutorialMenuPanel.setBounds((game.width() - menuWidth) / 2+20, (game.height() - menuHeight) /2+110, 244, 145);
        SuperSweetTutorialMenuPanel.setBounds((game.width() / 2) - 127, game.height() /2-75, 264, 165);
        //SuperSweetTutorialbuttonPanel.setOpaque(false);
        //SuperSweetTutorialbuttonPanel.setLayout(new BoxLayout(SuperSweetTutorialbuttonPanel, BoxLayout.PAGE_AXIS)); // set layout to place buttons in a column

        //Set the settings for each menu panel - SuperSweetTutorial Menu
        foundKeyTutorialMenuPanel.setBackground(new Color(0, 0, 0, 0)); // set background color to transparent
        foundKeyTutorialMenuPanel.setOpaque(false); // make the panel transparent
        foundKeyTutorialMenuPanel.setBounds((game.width() / 2) - 127, game.height() /2-75, 264, 165);


        //Add all the menu panels to the layered pane of the frame
        layeredPane.add(mPanel);
        layeredPane.setLayer(mPanel, 0);
        layeredPane.add(MMbuttonPanel); //1
        layeredPane.setLayer(MMbuttonPanel, 1);
        layeredPane.add(MainMenuPanel); //1
        layeredPane.setLayer(MainMenuPanel, 1);
        layeredPane.add(HTPbuttonPanel); //1
        layeredPane.setLayer(HTPbuttonPanel, 1);
        layeredPane.add(HowToPlayMenuPanel); //1
        layeredPane.setLayer(HowToPlayMenuPanel, 1);
        layeredPane.add(CRbuttonPanel); //1
        layeredPane.setLayer(CRbuttonPanel, 1);
        layeredPane.add(CreditsMenuPanel); //1
        layeredPane.setLayer(CreditsMenuPanel, 1);
        layeredPane.add(PAbuttonPanel); //1
        layeredPane.setLayer(PAbuttonPanel, 1);
        layeredPane.add(PauseMenuPanel); //1
        layeredPane.setLayer(PauseMenuPanel, 1);
        layeredPane.add(QUbuttonPanel); //1
        layeredPane.setLayer(QUbuttonPanel, 1);
        layeredPane.add(QuitMenuPanel); //1
        layeredPane.setLayer(QuitMenuPanel, 1);
        layeredPane.add(RTbuttonPanel); //1
        layeredPane.setLayer(RTbuttonPanel, 1);
        layeredPane.add(RetryMenuPanel); //1
        layeredPane.setLayer(RetryMenuPanel, 1);
        //layeredPane.add(SuperSweetTutorialbuttonPanel); //1
        //layeredPane.setLayer(SuperSweetTutorialbuttonPanel, 1);
        layeredPane.add(SuperSweetTutorialMenuPanel); //1
        layeredPane.setLayer(SuperSweetTutorialMenuPanel, 1);
        layeredPane.add(foundKeyTutorialMenuPanel); //1
        layeredPane.setLayer(foundKeyTutorialMenuPanel, 1);


        //Adding the layeredPane to the mFrame
        frame.setContentPane(layeredPane);

        //Set the panels to visible / invisible
        mPanel.setVisible(true);
        frame.setVisible(true);
        layeredPane.setVisible(true);
        HowToPlayMenuPanel.setVisible(false);
        HTPbuttonPanel.setVisible(false);
        CreditsMenuPanel.setVisible(false);
        CRbuttonPanel.setVisible(false);
        PauseMenuPanel.setVisible(false);
        PAbuttonPanel.setVisible(false);
        QuitMenuPanel.setVisible(false);
        QUbuttonPanel.setVisible(false);
        RetryMenuPanel.setVisible(false);
        RTbuttonPanel.setVisible(false);
        SuperSweetTutorialMenuPanel.setVisible(false);
        //SuperSweetTutorialbuttonPanel.setVisible(false);
        foundKeyTutorialMenuPanel.setVisible(false);

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
        MMplayButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {System.out.println("Main Menu Single Player button pressed");
                game.gameOver = false;
                game.gameStates = "PlayGame";
                //game.score = 0;
                MainMenuPanel.setVisible(false);
                MMbuttonPanel.setVisible(false);
                HowToPlayMenuPanel.setVisible(true);
                HTPbuttonPanel.setVisible(true);
            }
        });

        HTPreadyButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {System.out.println("How To Play Play button pressed");
                HowToPlayMenuPanel.setVisible(false);
                HTPbuttonPanel.setVisible(false);
            }
        });

        //Set the keyListeners for each of the buttons in the different menu screens
        /*multiPlayerButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {System.out.println("Main Menu MultiPlayer button pressed");
                game.gameOver = false;
                game.gameStates = "2Player";
                //game.score = 0; //Add a new score for 2nd player
                MainMenuPanel.setVisible(false);
                MMbuttonPanel.setVisible(false);
            }
        });*/

        creditsButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {System.out.println("Main Menu Credits button pressed");
                MainMenuPanel.setVisible(false);
                MMbuttonPanel.setVisible(false);
                CreditsMenuPanel.setVisible(true);
                CRbuttonPanel.setVisible(true);
            }
        });

        CRmainMenuButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                CreditsMenuPanel.setVisible(false);
                CRbuttonPanel.setVisible(false);
                MainMenuPanel.setVisible(true);
                MMbuttonPanel.setVisible(true);
            }
        });

        MMquitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MainMenuPanel.setVisible(false);
                MMbuttonPanel.setVisible(false);
                QuitMenuPanel.setVisible(true);
                QUbuttonPanel.setVisible(true);
            }
        });

        PAquitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                PauseMenuPanel.setVisible(false);
                PAbuttonPanel.setVisible(false);
                QuitMenuPanel.setVisible(true);
                QUbuttonPanel.setVisible(true);
            }
        });

        RTyesButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                game.gameStates = "PlayGame";
                game.gameOver = false;
                game.softResetIsTrue = false;
                game.gameReset();
                RetryMenuPanel.setVisible(false);
                RTbuttonPanel.setVisible(false);
            }
        });

        QUyesButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        RTnoButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Retry Menu No button pressed");
                game.gameStates = "MenuSystem";
                game.gameReset();
                RetryMenuPanel.setVisible(false);
                RTbuttonPanel.setVisible(false);
                MainMenuPanel.setVisible(true);
                MMbuttonPanel.setVisible(true);
            }
        });

        QUnoButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Quit Menu No button pressed");
                QuitMenuPanel.setVisible(false);
                QUbuttonPanel.setVisible(false);
                MainMenuPanel.setVisible(true);
                MMbuttonPanel.setVisible(true);
            }
        });

        PAresumeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Pause Menu Continue button pressed");
                PauseMenuPanel.setVisible(false);
                PAbuttonPanel.setVisible(false);
            }
        });
        SSTresumeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Supersweettutorial Menu Continue button pressed");
                SuperSweetTutorialMenuPanel.setVisible(false);
                //SuperSweetTutorialbuttonPanel.setVisible(false);
                game.superSweetsEaten = 2;
                game.unPauseGame();
            }
        });

        /*mainMenuButton.addActionListener(new ActionListener()
        {

        });*/
    }
}