package Implement;
import javax.swing.*;
import java.awt.*;
import Implement.MgrTodo;
import Implement.Task;
import Implement.Priority;
import Interface.TodoItem;
import java.time.LocalDate;
public class Todolist_run{
    static boolean showingBin = true;
    public static void refresh(JPanel panel,MgrTodo todo){
        if (showingBin==true){
            renderBin(panel,todo);
        } else if (showingBin == false){
            renderTodo(panel,todo);
        }
    }
    public static void renderTodo(JPanel panel, MgrTodo todo){
        panel.removeAll();
        for(int i=0;i<todo.getTodoList().size();i++){
            TodoItem t = todo.getTodoList().get(i);
            JPanel taskPanel  = new JPanel(new FlowLayout(FlowLayout.LEFT ,0,0));
            taskPanel.setMaximumSize(new Dimension (Integer.MAX_VALUE, 40));
            taskPanel.setMinimumSize(new Dimension(Integer.MIN_VALUE, 40));
            JButton tick = new JButton("V");
            JButton delete = new JButton("X");
            JLabel task = new JLabel();
            if(t.isDone()==true){
                task = new JLabel("<html><s>"+t.toString()+"</s></html>");
            } else{
                task = new JLabel(t.toString());
            }
            taskPanel.add(task);
            int index = i;
            tick.addActionListener(e->{
                todo.markDone(index);
                showingBin = false;
                refresh(panel,todo);
            });
            delete.addActionListener(e1 ->{
                todo.deleteTodo(index);
                refresh(panel,todo);
        });
        taskPanel.add(tick);
        taskPanel.add(delete);
        panel.add(taskPanel);
    }
        panel.revalidate();
        panel.repaint();
}
public static void renderBin(JPanel panel,MgrTodo todo) {
    panel.removeAll();
    for (int i=0;i<todo.getTrash().size();i++){
        TodoItem t = todo.getTrash().get(i);
        JPanel taskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        taskPanel.setMaximumSize(new Dimension (Integer.MAX_VALUE, 40));
        taskPanel.setMinimumSize(new Dimension(Integer.MIN_VALUE, 40));
        JLabel task = new JLabel(t.toString());
        JButton restore = new JButton("RESTORE");
        int index = i;
        restore.addActionListener(e -> { todo.restoreTrash(index);
            refresh(panel, todo);});
        JButton deleteForever = new JButton("X");
        deleteForever.addActionListener(e->{
            todo.deleteForever(index);
            refresh(panel,todo);
        });
        taskPanel.add(deleteForever);
        taskPanel.add(task);
        taskPanel.add(restore);
        panel.add(taskPanel); }
    panel.revalidate();
    panel.repaint();
    }
    public static void main(String [] args){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.LIGHT_GRAY);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MgrTodo todo = new MgrTodo();
        fileText storage = new fileText();
        storage.loadfromFILE(todo);

        JTextField input = new JTextField(92); // add task
        JButton button = new JButton("ADD");
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(input);
        inputPanel.add(button);
        JTextField dueDateField = new JTextField("YYYY-MM-DD", 10);

        JComboBox<Priority> priorityBox = new JComboBox<>(Priority.values());

        JButton bin = new JButton("BIN"); // Bin
        bin.addActionListener( e3 ->{
            showingBin = true;
           refresh(panel,todo);
        });
        button.addActionListener(e -> { // VAN DANG SUA
            String text = input.getText().trim();
            if (!text.isEmpty()) {
                Priority P = (Priority) priorityBox.getSelectedItem();
                LocalDate dueDate = LocalDate.parse(dueDateField.getText().trim());
                todo.addTodo(text, P, dueDate);
            }
            input.setText("");
            dueDateField.setText("YYYY-MM-DD");
            showingBin = false;
            refresh(panel, todo);
        });
        JButton HOME = new JButton("HOME");
        HOME.addActionListener(e4->{
            showingBin = false;
            refresh(panel,todo);
        });
        inputPanel.add(priorityBox,BorderLayout.CENTER);
        inputPanel.add(HOME,BorderLayout.NORTH);
        inputPanel.add(input,BorderLayout.EAST);
        inputPanel.add(button,BorderLayout.SOUTH);
        inputPanel.add(bin,BorderLayout.WEST);
        frame.add(inputPanel,BorderLayout.NORTH);
        frame.add(panel,BorderLayout.CENTER);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        inputPanel.add(dueDateField, BorderLayout.WEST);
        refresh(panel,todo);
        frame.setVisible(true);
    }
}