package Implement;
import Interface.TodoItem;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
public class fileText {
    private File file;
    public fileText() {
        File file = new File("task.txt");
        this.file = file;
    }
    public void saveToFile(MgrTodo todo) {
        try{
            BufferedWriter text = new BufferedWriter (new FileWriter(file));
            for (TodoItem task : todo.getTodoList()){
                text.write("TODO|" + (task.isDone() ? 1 : 0) + "|" + task.toString());
                text.newLine();
            }
        } catch(IOException e){

        }
    }
}
