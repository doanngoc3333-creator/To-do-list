package Implement;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import Interface.TodoItem;
class Task implements TodoItem { // class nay la work rieng le
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
    @Override
    public void markDone(){
        this.done=true;
    } // danh dau
    public boolean isDone(){
        return done;
    } // tra ketqua
    @Override
    public String getTodo(){
        return this.todo;
    } // wait
}