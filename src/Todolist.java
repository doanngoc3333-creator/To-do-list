import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
class Task{ // class nay la work rieng le
    private String todo;
    private boolean done;
    Task(String todo,boolean done){
        this.todo=todo;
        this.done=false;
    } //constructor nha
    public void setTodo(String work){
        this.todo=work;
    } // cai nay la setter nha
    public String toString(){ // cai nay in ra
        return todo;
    }
    public void markDone(){
        this.done=true;
    }
    public boolean isDone(){
        return done;
    }
}
class MgrTodo { // cai nay la list work
    List<Task> todoList = new ArrayList<>();
    public List<Task> getTodoList() {
        return todoList;
    }
    public void addTodo(String text){
        todoList.add(new Task(text,false)); // goi constructor ra
    }
    public void deleteTodo(int index){
        if(index>=0&&index<todoList.size()){
            todoList.remove(index);
        }
    }
    public void markDone(int index){
        if(index>=0&&index<todoList.size()){
            todoList.get(index).markDone();
        }
    }
}
public class Todolist{
    public static void main(String [] args){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JFrame frame = new JFrame();
        frame.setSize(1080,720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MgrTodo todo = new MgrTodo();
        JTextField input = new JTextField(50);
        JButton button = new JButton("ADD");
        JPanel inputPanel = new JPanel();
        inputPanel.add(input);
        inputPanel.add(button);
        button.addActionListener(e ->{
            String text = input.getText();
            if(!text.isEmpty()){
                todo.addTodo(text);
            }
            panel.removeAll();
            for(Task t: todo.getTodoList()){
                JLabel label = new JLabel(t.toString());
                panel.add(label);
            }
            panel.revalidate();
            panel.repaint();
            input.setText("");
                });
        frame.add(inputPanel,BorderLayout.EAST);
        frame.add(panel,BorderLayout.CENTER);
        frame.setVisible(true);
    }
}