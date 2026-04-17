package Implement;
import javax.swing.*;
import java.awt.*;

import Interface.TodoItem;

public class Todolist_run{
    public static void renderTodo(JPanel panel, MgrTodo todo){
        panel.removeAll();
        for(int i=0;i<todo.getTodoList().size();i++){
            TodoItem t = todo.getTodoList().get(i);
            JPanel taskPanel  = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
                renderTodo(panel,todo);
            });
            delete.addActionListener(e1 ->{
                todo.deleteTodo(index);
                renderTodo(panel,todo);
        });
        taskPanel.add(tick);
        taskPanel.add(delete);
        panel.add(taskPanel);
    }
        panel.revalidate();
        panel.repaint();
}
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
        JButton bin = new JButton("BIN"); // CAN SUA
        bin.addActionListener( e3 ->{
            panel.removeAll();
           for (int i=0;i<todo.getTrash().size();i++){
               TodoItem t = todo.getTrash().get(i);
               JPanel taskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
               JLabel Task = new JLabel(t.toString());
               taskPanel.add(Task);
               int index = i;
               JButton Restore = new JButton("Restore"); // CAN SUA
               Restore.addActionListener(e4->{
                   todo.restoreTrash(index);
                   bin.doClick();
               });
               taskPanel.add(Restore);
               panel.add(taskPanel);
           }
            panel.revalidate();
            panel.repaint();
        });
        button.addActionListener(e ->{
            String text = input.getText();
            if(!text.isEmpty()){
                todo.addTodo(text);
            }
            panel.removeAll();
            for(int i=0;i<todo.getTodoList().size();i++){
                TodoItem t = todo.getTodoList().get(i);
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
                    bin.doClick();
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
        inputPanel.add(bin,BorderLayout.WEST);
        frame.add(inputPanel,BorderLayout.NORTH);
        frame.add(panel,BorderLayout.CENTER);
        frame.setVisible(true);
    }
}