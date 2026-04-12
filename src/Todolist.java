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
    } // in ra
    public void markDone(){
        this.done=true;
    } // danh dau
    public boolean isDone(){
        return done;
    } // tra ketqua
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
        panel.setBackground(Color.LIGHT_GRAY);
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
            for(int i=0;i<todo.getTodoList().size();i++){
                Task t = todo.getTodoList().get(i);
                JPanel taskPanel = new JPanel(); // tao task o day nha
                taskPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                JLabel task;
                if(t.isDone()==true) {
                    task = new JLabel("<html><s>"+t.toString()+" </s></html>");
                } else {
                    task = new JLabel(t.toString());
                }
                taskPanel.add(task);
                JButton delete = new JButton("X");
                int index = i;
                delete.addActionListener( e1->{ // delete task
                    todo.deleteTodo(index);
                    System.out.println("Delete index: "+index);
                    System.out.println("List index: "+ todo.getTodoList().size());
                    button.doClick();
                });
                JButton tick = new JButton("V");
                int index1 = i;
                tick.addActionListener( e2->{ // done
                    todo.markDone(index1);
                    button.doClick();
                    if(t.isDone()==true) {
                        new JLabel("<html><s>"+t.toString()+" </s></html>");
                    } else {
                        new JLabel(t.toString());
                    }
                });
                taskPanel.add(delete);
                taskPanel.add(tick);
                panel.add(taskPanel);
                panel.add(Box.createVerticalStrut(1));
                taskPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            }
            panel.revalidate();
            panel.repaint();
            input.setText("");
                });
        inputPanel.add(input,BorderLayout.EAST);
        inputPanel.add(button,BorderLayout.WEST);
        frame.add(inputPanel,BorderLayout.NORTH);
        frame.add(panel,BorderLayout.CENTER);
        frame.setVisible(true);
    }
}