
package animationplayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class AnimationPlayer {
//framecount-gives declaration to circle,rectangle and triangle
    int frameCount = 0;
    //frameSpeed-speed frames per second
    int frameSpeedPerSec = 0;
    //numberElements-an integer specified to each shape 
    int numberOfElements = 0;
    //a number given to the current frame showing the animation
    int currentframe = 1;

    ArrayList<String>[] elementsList;
//referring ffrom stack-overflow
    JDesktopPane desk;
    JInternalFrame frame1, frame2, frame3, frame4;
    JFrame frame;
//a method for loading the animation file using filechooser method
    public void loadAnimationFromFile() throws IOException {
        //libraries are above
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setDialogTitle("Select text file");
        String name = "";
        
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getPath().toLowerCase().endsWith(".txt")
                        || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Text File";
            }
        });

        int r = chooser.showOpenDialog(new JFrame());
        if (r == JFileChooser.APPROVE_OPTION) {
            name = chooser.getSelectedFile().getPath();
            System.out.println(name);
        }
        //return name;

        String line;
        String[] stringsEachLine = new String[]{};
        //Using set will remove duplicate words
        List<String> stringsSet = new ArrayList<>();

        //Read data from the file at particular path
        try {
        BufferedReader reader = new BufferedReader(new FileReader(new File("C:\\Users\\Demo\\OneDrive\\Desktop\\ENGG-1420\\input.txt")));

        boolean isElement = false;
        //Traverse each line
        while ((line = reader.readLine()) != null) {

            stringsEachLine = line.split("[\\s+\\p{P}]");
            if (stringsEachLine[0].toLowerCase().contains("frames")) {
                frameCount = Integer.parseInt(stringsEachLine[2]);
                System.out.println(frameCount);

            } else if (stringsEachLine[0].toLowerCase().contains("speed")) {
                frameSpeedPerSec = Integer.parseInt(stringsEachLine[2]);
                System.out.println(frameSpeedPerSec);

            } else if (stringsEachLine[0].toLowerCase().contains("elements")) {
                numberOfElements = Integer.parseInt(stringsEachLine[2]);
                System.out.println(numberOfElements);

            } else if (line.isEmpty()) {
                isElement = true;
            }
            while (isElement && (line = reader.readLine()) != null) {

                stringsSet.add(line);
            }
        }

        elementsList = new ArrayList[numberOfElements];
        int j = 0;
        elementsList[j] = new ArrayList<>();
        for (int i = 0; i < stringsSet.size(); i++) {

            if (stringsSet.get(i).isEmpty()) {
                j++;
                elementsList[j] = new ArrayList<>();

            } else {

                elementsList[j].add(stringsSet.get(i).toLowerCase());
            }
        }

    }
        catch(Exception druv){
            System.out.println(druv);
               System.out.println("It is an exception");
    }
    
    
}
// method to control the time for each animation of each element 
    public void MultipleFrames() {

        int DELAY = 1000 / frameSpeedPerSec;

        Timer timer = new Timer(DELAY, new SwingTimerActionListener());

        timer.start();
    }

    class Swing_Timer_Action_Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evnt) {
            JFrame frame = new JFrame("Frame " + Integer.toString(currentframe));
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());

            ArrayList<String>[] aa = elementsList;

            for (ArrayList<String> aa1 : aa) {
                boolean isCircle = false;
                boolean isRect = false;
                boolean isLine = false;
                int r = 0;
                int x = 0;
                int y = 0;
                int length = 0;
                int width = 0;
                int startX = 0;
                int endX = 0;
                int startY = 0;
                int endY = 0;
                String effect = "";
                int frameNumber = 0;
                Color color = null;
                int border = 0;
                Color borderColor = null;

                if (aa1.get(0).contains("circle")) {
                    isCircle = true;
                } else if (aa1.get(0).contains("circle")) {
                    isRect = true;
                } else {
                    isLine = true;
                }
//if-else-loops to see and set and read the information of a circle
                if (isCircle) {
                    for (int i = 0; i < aa1.size(); i++) {
                        if (aa1.get(i).contains("start")) {
                            frameNumber = Integer.parseInt(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("radius:")) {
                            r = Integer.parseInt(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("x:")) {
                            x = Integer.parseInt(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("y:")) {
                            y = Integer.parseInt(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("color:")) {
                            color = Color.getColor(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("border:")) {
                            border = Integer.parseInt(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("bgcolr:")) {
                            borderColor = Color.getColor(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("effect")) {
                            effect = (aa1.get(i + 1));
                            if (currentframe >= frameNumber && frameNumber != 0) {
                                Circle c = new Circle(r, x, y, effect, frameNumber, color, border, borderColor);
                                panel.add(c);
                            }
                        }
                    }
//if-else-loops to see and read the information of a rectangle
                } else if (isRect) {
                    for (int i = 0; i < aa1.size(); i++) {
                        if (aa1.get(i).contains("start")) {
                            frameNumber = Integer.parseInt(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("length:")) {
                            length = Integer.parseInt(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("width:")) {
                            width = Integer.parseInt(aa1.get(i).split("\\s+")[1]);

                        } else if (aa1.get(i).contains("x:")) {
                            x = Integer.parseInt(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("y:")) {
                            y = Integer.parseInt(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("color:")) {
                            color = Color.getColor(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("border:")) {
                            border = Integer.parseInt(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("bgcolr:")) {
                            borderColor = Color.getColor(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("effect")) {
                            effect = (aa1.get(i + 1));
                            if (currentframe >= frameNumber && frameNumber != 0) {
                                Rect rect = new Rect(length, width, x, y, effect, frameNumber, color, border, borderColor);
                                panel.add(rect);
                            }
                        }
//if-else-loops to see and read all the information of a line
                    }
                } else if (isLine) {
                    for (int i = 0; i < aa1.size(); i++) {
                        if (aa1.get(i).contains("start")) {
                            frameNumber = Integer.parseInt(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("x1:")) {
                            startX = Integer.parseInt(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("y1:")) {
                            startY = Integer.parseInt(aa1.get(i).split("\\s+")[1]);

                        } else if (aa1.get(i).contains("x2:")) {
                            endX = Integer.parseInt(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("y2:")) {
                            endY = Integer.parseInt(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("color:")) {
                            color = Color.getColor(aa1.get(i).split("\\s+")[1]);
                        } else if (aa1.get(i).contains("border:")) {
                            border = Integer.parseInt(aa1.get(i).split("\\s+")[1]);

                        } else if (aa1.get(i).contains("effect")) {
                            effect = (aa1.get(i + 1));
                            if (currentframe >= frameNumber && frameNumber != 0) {
                                Line line = new Line(startX, endX, startY, endY, effect, frameNumber, color, border);
                                panel.add(line);
                            }
                        }

                    }
                }
                frame.add(panel);
                frame.setSize(400, 300);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

                if (currentframe == frameCount) {
                    ((Timer) evnt.getSource()).stop();
                } else {
                    currentframe = currentframe + 1;
                }
            }
        }
// a seperate circle class to store information about the circle element
        public class Circle extends JPanel {

            int r = 0;
            int x = 0;
            int y = 0;
            String effect = "";
            int frameNumber = 0;
            Color color = null;
            int border = 0;
            Color borderColor = null;

            public Circle(int r1, int x1, int y1, String effect1, int frameNumber1, Color color1, int border1, Color borderColor1) {

                r = r1;
                x = x1;
                y = y1;
                effect = effect1;
                frameNumber = frameNumber1;
                color = color1;
                borderColor = borderColor1;
                border = border1;

            }
//method to set the color and paint components of the circle element
            @Override
            public void paintComponent(Graphics g) {

                super.paintComponent(g);
                g.drawOval(x, y, r * 2, r * 2);
                g.setColor(color);
                g.fillOval(x, y, r * 2, r * 2);

            }
//size of the pop-up window 
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(300, 200);
            }
        }
// a seperate circle class to store information about the circle element
        public class Rect extends JPanel {

            int length = 0;
            int width = 0;
            int x = 0;
            int y = 0;
            String effect = "";
            int frameNumber = 0;
            Color color = null;
            int border = 0;
            Color borderColor = null;

            public Rect(int length1, int width1, int x1, int y1, String effect1, int frameNumber1, Color color1, int border1, Color borderColor1) {

                length = length1;
                width = width1;
                x = x1;
                y = y1;
                effect = effect1;
                frameNumber = frameNumber1;
                color = color1;
                borderColor = borderColor1;
                border = border1;

            }
//method to set the color and paint components of the rectangle element
            @Override
            public void paintComponent(Graphics g) {

                super.paintComponent(g);
                g.drawRect(x, y, width, length);
                g.setColor(color);
                g.fillRect(x, y, width, length);

            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(300, 200);
            }
        }
// a seperate circle class to store information about the line element
        public class Line extends JPanel {

            int startX = 0;
            int endX = 0;
            int startY = 0;
            int endY = 0;
            String effect = "";
            int frameNumber = 0;
            Color color = null;
            int border = 0;

            public Line(int startX1, int endX1, int startY1, int endY1, String effect1, int frameNumber1, Color color1, int border1) {

                startX = startX1;
                endX = endX1;
                startY = startY1;
                endY = endY1;
                effect = effect1;
                frameNumber = frameNumber1;
                color = color1;
                border = border1;

            }
//method to set the color and paint components of the line element
            @Override
            public void paintComponent(Graphics g) {

                super.paintComponent(g);
                g.drawLine(startX, endX, startY, endY);
                g.setColor(color);

            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(300, 200);
            }
        }
    }

 //main method
    public static void main(String[] args) throws IOException {
        AnimationPlayer player = new AnimationPlayer();

        try {

            player.loadAnimationFromFile();

        } catch (Exception ex) {
            Logger.getLogger(AnimationPlayer.class
                    .getName()).log(Level.SEVERE, null, ex);
            try {

            player.loadAnimationFromFile();

        }
        catch ( ArithmeticException a){
            System.out.println(a);
             System.out.println("It is an exception");
        }
        player.MultipleFrames();

    }

}
}
