package Implement;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import Interface.TodoItem;
class MgrTodo { // cai nay la list work
    List<TodoItem> todoList = new ArrayList<>();
    List<TodoItem> trash = new ArrayList<>();
    public List<TodoItem> getTodoList() {
        return todoList;
    }
    public List<TodoItem> getTrash (){ return trash;}
    public void addTodo(String text,Priority P){
        todoList.add(new Task(text,false,P)); // goi constructor ra
    }
    public void deleteTodo(int index){ // xoa
        if(index>=0&&index<todoList.size()){
            TodoItem removed = todoList.get(index);
            trash.add(removed); // day vao thung rac
            todoList.remove(index);
        }
    }
    public void restoreTrash(int index){
        if(index>=0&&index<trash.size()){
            todoList.add(trash.get(index));
            trash.remove(index);
        }
    }
    public void markDone(int index){
        if(index>=0&&index<todoList.size()){
            todoList.get(index).markDone();
        }
    }
}
