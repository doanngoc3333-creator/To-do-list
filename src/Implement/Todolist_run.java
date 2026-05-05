package Implement;
import javax.swing.*;
import java.awt.*;
import Implement.MgrTodo;
import Implement.Task;
import Implement.Priority;
import Interface.TodoItem;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Todolist_run {
    static boolean showingBin = false; // man hinh home

    public static void refresh(JPanel panel, MgrTodo todo, fileText storage) { // ham dieu huong
        if (showingBin) {
            renderBin(panel, todo, storage);
        } else {
            renderTodo(panel, todo, storage);
        }
        storage.saveToFile(todo);
    }

    public static void renderTodo(JPanel panel, MgrTodo todo, fileText storage) {
        panel.removeAll();
        for (int i = 0; i < todo.getTodoList().size(); i++) {
            TodoItem t = todo.getTodoList().get(i);
            JPanel taskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // tao task o day nha
            taskPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            taskPanel.setMinimumSize(new Dimension(Integer.MIN_VALUE, 40));

            JButton tick = new JButton("V");
            JButton delete = new JButton("X");
            JLabel task = new JLabel();

            if (t.isDone()) {
                task = new JLabel("<html><s>" + t.toString() + "</s></html>");
            } else {
                task = new JLabel(t.toString());
            }

            taskPanel.add(task);
            int index = i;

            tick.addActionListener(e -> { // done
                todo.markDone(index);
                refresh(panel, todo, storage);
            });

            delete.addActionListener(e1 -> { // delete task
                todo.deleteTodo(index);
                refresh(panel, todo, storage);
            });

            taskPanel.add(delete);
            taskPanel.add(tick);
            panel.add(taskPanel);
        }
        panel.revalidate();
        panel.repaint();
    }

    public static void renderBin(JPanel panel, MgrTodo todo, fileText storage) {
        panel.removeAll();
        for (int i = 0; i < todo.getTrash().size(); i++) {
            TodoItem t = todo.getTrash().get(i);
            JPanel taskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            
            taskPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            taskPanel.setMinimumSize(new Dimension(Integer.MIN_VALUE, 40));

            JLabel task = new JLabel(t.toString());
            JButton restore = new JButton("RESTORE"); 
            int index = i;
            // khoi phuc xong se load lai man hinh
            restore.addActionListener(e -> {
                todo.restoreTrash(index);
                refresh(panel, todo, storage);
            });

            taskPanel.add(task);
            taskPanel.add(restore);
            panel.add(taskPanel);
        }
        panel.revalidate();
        panel.repaint();
    }

    public static void main(String [] args){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.LIGHT_GRAY);
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

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
           refresh(panel,todo, storage);
        });
        
        java.awt.event.ActionListener addAction = e -> { // da sua lai
            String text = input.getText().trim();
            if (!text.isEmpty()) {
                try {
                    Priority P = (Priority) priorityBox.getSelectedItem();
                    LocalDate dueDate = LocalDate.parse(dueDateField.getText().trim());
                    todo.addTodo(text, P, dueDate);
                    input.setText("");
                    dueDateField.setText(LocalDate.now().toString());
                    showingBin = false;
                    refresh(panel, todo, storage);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Format: YYYY-MM-DD", "Date Format Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        
        button.addActionListener(addAction);
        input.addActionListener(addAction);
        
        JButton HOME = new JButton("HOME");
        HOME.addActionListener(e4->{
            showingBin = false;
            refresh(panel, todo, storage);
        });
        
        inputPanel.add(priorityBox,BorderLayout.CENTER);
        inputPanel.add(HOME,BorderLayout.NORTH);
        inputPanel.add(input,BorderLayout.EAST);
        inputPanel.add(button,BorderLayout.SOUTH);
        inputPanel.add(bin,BorderLayout.WEST);
        
        frame.add(inputPanel,BorderLayout.NORTH);
        frame.add(scrollPane,BorderLayout.CENTER);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        inputPanel.add(dueDateField, BorderLayout.WEST);
        refresh(panel,todo, storage);
        frame.setVisible(true);
    }
}