# Ragul's Command Prompt

This project is a simple command-line interface (CLI) implemented using Java and Swing. It simulates a terminal/command prompt experience with a few basic commands like `ls`, `cd`, `pwd`, and `exit`. The program is designed to be used as a terminal emulator with the following features:

- **Directory Navigation**: Change directories and list files.
- **Command Execution**: Execute simple terminal commands.
- **Simple UI**: A graphical user interface (GUI) that mimics a Linux terminal.

## Features

- **Directory Navigation**:
    - `cd <directory>`: Change the current directory.
    - `pwd`: Display the current directory path.
    - `ls`: List files in the current directory.
    - `touch`: Creates a new empty file.
    - `rmvf`: Deletes the file in the directory.
  
- **Exit the Program**:
    - `exit`: Exit the terminal.
  
- **Keyboard Shortcuts**:
    - **Enter**: Submit a command.
    - **Ctrl + q**: Exit the application.

## Installation

To use the Command Prompt, you'll need to have Java installed on your machine.

### 1. Clone the Repository
Clone the project to your local machine:
```bash
git clone https://github.com/your-username/command-prompt.git
```
### 2. Compile the Project
Navigate to the project directory and compile the Java file:
```bash
javac CommandPrompt.java
```
### 3. Run the program
After compiling, run the application using the following command:
``` bash
java CommandPrompt
```

Usage
-----

Once the program starts, a window will appear that simulates a terminal. The current directory will be displayed followed by the `%` prompt. You can type commands like:

*   `ls` to list the files in the current directory.
    
*   `cd` to change the current directory.
    
*   `pwd` to show the current directory path.
*   `touch` to create a new file.
*   `rmvf` deletes the file.
    
*   `exit` to close the terminal.
        

Contributions

Feel free to fork the project and submit pull requests with any improvements or bug fixes.

License
-------

This project is licensed under the MIT License.
