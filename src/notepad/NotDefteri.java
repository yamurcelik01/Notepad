package project;

import java.io.*;
import java.util.*;
import javax.swing.*;



public class NotDefteri {
    private HashMap<String, LinkedList<Not>> notes;
    
    public NotDefteri() {
        notes = new HashMap<>();
    }
    
    public void addNote(String name, String content) {
        Not note = new Not(name, content);
        
        if (notes.containsKey(name)) {
            LinkedList<Not> list = notes.get(name);
            list.add(note);
        } else {
            LinkedList<Not> list = new LinkedList<>();
            list.add(note);
            notes.put(name, list);
        }
    }
    
    public void removeNote(String name) {
        notes.remove(name);
    }
    
    public LinkedList<Not> getNote(String name) {
        return notes.get(name);
    }
    public void saveNotesToFile(String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

            for (LinkedList<Not> noteList : notes.values()) {
                for (Not note : noteList) {
                    writer.write(note.getName() + "\n");
                    writer.write(note.getContent() + "\n");
                    writer.write("---\n");  // Notlar arasında bir ayracı kullanma
                }
            }

            writer.close();
            System.out.println("Notes were saved on file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


public static void main(String[] args) {
   
	   SwingUtilities.invokeLater(new Runnable() {
           public void run() {
               new GUI();
           }
       });
   }

    
    }


