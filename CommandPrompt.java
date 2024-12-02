import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.*;



public class CommandPrompt {

    private static String currentDir = System.getProperty("user.dir");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CommandPrompt::createAndShowGUI);
    }
    
    private static void createAndShowGUI(){
        JFrame frame = new JFrame("Ragul's Command Prompt");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);

        try {
            Image logo = Toolkit.getDefaultToolkit().getImage(CommandPrompt.class.getResource("/logo.jpeg"));
            frame.setIconImage(logo);
        } catch (Exception e) {
            System.out.println("Logo cannot be loaded");
        }

        // Area to display 


        JTextArea terminalArea = new JTextArea();
        terminalArea.setEditable(true);
        terminalArea.setLineWrap(true);
        terminalArea.setWrapStyleWord(true);
        terminalArea.setBackground(Color.DARK_GRAY);
        terminalArea.setForeground(Color.WHITE);
        terminalArea.setFont(new Font("Consolas", Font.BOLD, 16));
        terminalArea.append("\nWelcome to Ragul's Command Prompt!\n");
        terminalArea.append("Enter 'exit' to quit.\n");
        terminalArea.append("\n");
        terminalArea.setCaretColor(Color.WHITE);
        

        JScrollPane scrollPane = new JScrollPane(terminalArea);
        frame.add(scrollPane,BorderLayout.CENTER);

        terminalArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    e.consume();
                    String text = terminalArea.getText();
                    int lastPromptIndex = text.lastIndexOf(currentDir + " % ");
                    if(lastPromptIndex == -1){
                        return;
                    }
                    String command = text.substring(lastPromptIndex + (currentDir+" % ").length()).trim();
                    if(!command.isEmpty()){
                        processCommand(command, terminalArea);
                    }
                } else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C){
                    System.exit(0);
                }
            }
        });

        terminalArea.append(currentDir + " % ");
        frame.setVisible(true);
    }

    private static void processCommand(String command, JTextArea terminalArea) {
        try {
            terminalArea.append("\n");
            currentDir = executeCommand(command,currentDir,terminalArea);
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error: "+ e.getMessage()+ "\n");
        }
    }

    private static String executeCommand(String command,String currentDir, JTextArea terminalArea) throws IOException {
        String[] parts = command.split("\\s+");
        String baseCommand = parts[0].toLowerCase();

        switch (baseCommand){
            case "ls" -> listFiles(currentDir, terminalArea);

            case "cd" -> {
                if(parts.length > 1){
                    currentDir = changeDirectory(parts[1], currentDir, terminalArea);
                }else{
                    terminalArea.append("Usage: cd <directory>\n");
                }
            }
            
            case "pwd" -> terminalArea.append(currentDir + "\n");
            case "exit" ->{
                terminalArea.append("Exiting...\n");
                System.exit(0);
            }

            default -> terminalArea.append("Unknown command: "+ baseCommand +"\n");
        }
        terminalArea.append(currentDir + " % ");
        return currentDir;
    }

    private static void listFiles(String currentDir, JTextArea terminalArea){
        File folder = new File(currentDir);
        File[] files = folder.listFiles();

        if(files != null){
            for(File file : files){
                terminalArea.append((file.isDirectory() ? "[FOLDR] " : "[FILE] ")+file.getName()+"\n");
            }
        }else{
            terminalArea.append("Unable to list files.\n");
        }  
    }

    private static String changeDirectory(String path, String currentDir, JTextArea terminalArea){
        Path newPath = Paths.get(currentDir, path).normalize();

        if(Files.exists(newPath) && Files.isDirectory(newPath)){
            return newPath.toAbsolutePath().toString();
        }else{
            terminalArea.append("Unable to change the directory to "+path+"\n");
            return currentDir;
        }
    }

}