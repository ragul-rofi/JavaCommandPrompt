import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class CommandPrompt {

    private static String currentDir = "C:\\Users\\"+System.getProperty("user.name");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CommandPrompt::createAndShowGUI);
    }
    
    private static void createAndShowGUI(){
        JFrame frame = new JFrame("Ragul's Command Prompt");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,500);

        try {
            Image logo = Toolkit.getDefaultToolkit().getImage(CommandPrompt.class.getResource("/logo2.png"));
            frame.setIconImage(logo);
        } catch (Exception e) {
            System.out.println("Logo cannot be loaded");
        }

        // Area to display 


        JTextArea terminalArea = new JTextArea();
        terminalArea.setEditable(true);
        terminalArea.setLineWrap(true);
        terminalArea.setWrapStyleWord(true);
        terminalArea.setBackground(Color.WHITE);
        terminalArea.setForeground(Color.DARK_GRAY);
        terminalArea.setFont(new Font("Consolas", Font.BOLD, 18));
        terminalArea.append("\nWelcome to Ragul's Command Prompt!\n");
        terminalArea.append("Enter 'exit' or 'Ctrl + q'to quit.\n");
        terminalArea.append("\n");
        terminalArea.setCaretColor(Color.black);
        terminalArea.setBorder(new EmptyBorder(0,10,5,10));
        

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
                } else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Q){
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

            case "rmvf" ->{
                if (parts.length >1){
                    removeFile(parts[1], terminalArea);
                }else{
                    terminalArea.append("Use command like this: rm-f __filename__\n");
                }
            }

            case "touch" -> {
                if(parts.length > 1){
                    createFile(parts[1], terminalArea);
                }else{
                    terminalArea.append("Use command like this: touch __filename__\n");
                }
            }
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

    private static void createFile(String fileName, JTextArea terminalArea){
        Path newPath = Paths.get(currentDir, fileName);

            try {
                if(Files.exists(newPath)){
                    terminalArea.append("File already exists: "+ newPath.getFileName() + "\n");
                }else{
                    Files.createFile(newPath);
                }
            } catch (IOException e) {
                terminalArea.append("Error creating file.");
            }
    }

    private static void removeFile(String fileName, JTextArea terminalArea){      
        Path path = Paths.get(fileName).normalize();
        File file = path.toFile();
        if(file.exists()){
            try{
                Files.delete(path);
                terminalArea.append("File '"+fileName+"' deleted.\n");
            }catch (IOException e) {
                terminalArea.append("Error deleteing file!");
            }
        }else{
            terminalArea.append("File not found: "+fileName+"\n");
        }
    }  
} 
