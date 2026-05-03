package Implement;
import Implement.Task;
import Implement.MgrTodo;
import Interface.TodoItem;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
public class fileText {
    private File file;

    public fileText() {
        File file = new File("task.txt");
        this.file = file;
    }

    public void saveToFile(MgrTodo todo) {
        try {
            BufferedWriter text = new BufferedWriter(new FileWriter(file));
            for (TodoItem task : todo.getTodoList()) {
                Task t = (Task) task;
                text.write((task.isDone() ? 1 : 0) + "|" + t.getPriority() + "|"+t.getDate()+"|" + t.getTodo());
                text.newLine();
            }
            text.close();
        } catch (IOException e) {
            e.printStackTrace(); // error on terminal
            JOptionPane.showMessageDialog(null, "Fail to save", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadfromFILE(MgrTodo todo) {
        if (!file.exists()) {
            return;
        }
        try  (BufferedReader reader = new BufferedReader(new FileReader(file))){ //if normal
            String line;
            while ((line = reader.readLine()) !=null) {
                String[] part = line.split("\\|");
                if (part.length == 4) {
                    LocalDate dueDate = LocalDate.parse(part[2].trim());
                    boolean done = part[0].trim().equals("1");
                    Priority priority = Priority.valueOf(part[1].trim());
                    todo.addTodo(part[3], priority,dueDate);
                    if (done) {
                        todo.markDone(todo.getTodoList().size() - 1);
                    }
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}

