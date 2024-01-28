package project;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;



public class GUI extends JFrame implements ActionListener {

    private HashMap<String, LinkedList<Not>> notes = new HashMap<>();
    private JComboBox<String> noteSelector;
    private JTextField nameField;
    private JTextArea contentArea;
    private JButton saveButton;
    private JComboBox<String> fontSelector;
    private JComboBox<String> fontSizeSelector;
    private JComboBox<String> fontColourSelector;
    private Color selectedColour = Color.BLACK; // Seçilen rengi takip etmek için
    

    public GUI() {
    	
       	
        super("Not Defteri");
        
        // Not Adı girdisi ve etiketi
        JPanel namePanel = new JPanel(new BorderLayout());
        JLabel nameLabel = new JLabel("Note name:");
        nameField = new JTextField(20);
        namePanel.add(nameLabel, BorderLayout.WEST);
        namePanel.add(nameField, BorderLayout.CENTER);

        // Not İçeriği girdisi ve etiketi
        JPanel contentPanel = new JPanel(new BorderLayout());
        JLabel contentLabel = new JLabel("Note content:");
        contentArea = new JTextArea(10, 20);
        JScrollPane scrollPane = new JScrollPane(contentArea);
        contentPanel.add(contentLabel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Not Ekle ve Not Sil düğmeleri
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Add new note");
        addButton.addActionListener(this);
        buttonPanel.add(addButton);
        JButton removeButton = new JButton("Delete note");
        removeButton.addActionListener(this);
        buttonPanel.add(removeButton);

        // Seçilebilen Notlar
        JPanel selectorPanel = new JPanel(new BorderLayout());
        JLabel selectorLabel = new JLabel("Notes:");
        noteSelector = new JComboBox<>();
        noteSelector.addActionListener(this);
        selectorPanel.add(selectorLabel, BorderLayout.NORTH);
        selectorPanel.add(noteSelector, BorderLayout.CENTER);

        // Font ve Boyut seçici
        JPanel fontSizerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        fontSizerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JPanel fontPanel = new JPanel(new BorderLayout());
        JLabel fontLabel = new JLabel("Font:");
        fontSelector = new JComboBox<>();
        fontSelector.addItem("Arial");
        fontSelector.addItem("Times New Roman");
        fontSelector.addItem("Courier New");
        fontPanel.add(fontLabel, BorderLayout.WEST);
        fontPanel.add(fontSelector, BorderLayout.CENTER);

        JPanel sizePanel = new JPanel(new BorderLayout());
        JLabel sizeLabel = new JLabel("Size:");
        fontSizeSelector = new JComboBox<>();
        fontSizeSelector.addItem("12");
        fontSizeSelector.addItem("16");
        fontSizeSelector.addItem("20");
        sizePanel.add(sizeLabel, BorderLayout.WEST);
        sizePanel.add(fontSizeSelector, BorderLayout.CENTER);

        // Renk seçici
        JPanel colourPanel = new JPanel(new BorderLayout());
        JLabel colourLabel = new JLabel("Colour:");
        fontColourSelector = new JComboBox<>();
        fontColourSelector.addItem("Black");
        fontColourSelector.addItem("Red");
        fontColourSelector.addItem("Blue");
        fontColourSelector.addActionListener(new ActionListener() {
        	
        	public void actionPerformed(ActionEvent e) {
                String selectedColourName = (String) fontColourSelector.getSelectedItem();
                if (selectedColourName.equals("Black")) {
                    selectedColour = Color.BLACK;
                } else if (selectedColourName.equals("Red")) {
                    selectedColour = Color.RED;
                } else if (selectedColourName.equals("Blue")) {
                    selectedColour = Color.BLUE;
                }
                contentArea.setForeground(selectedColour);
               
            }
        });
        colourPanel.add(colourLabel, BorderLayout.WEST);
        colourPanel.add(fontColourSelector, BorderLayout.CENTER);

        fontSizerPanel.add(fontPanel);
        fontSizerPanel.add(sizePanel);
        fontSizerPanel.add(colourPanel);
   	
        
        // Ana paneli oluşturma ve bileşenleri eklenme
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(namePanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Notu Kaydet ve Notları Göster düğmeleri
        JPanel saveShowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        saveButton = new JButton("Save notes");
        saveButton.addActionListener(this);
        saveShowPanel.add(saveButton);
        JButton showButton = new JButton("Show notes");
        showButton.addActionListener(this);
        saveShowPanel.add(showButton);

        // Ana paneli, seçim panelini ve font/boyut seçici panelini birleştirme
        JPanel contentContainer = new JPanel(new BorderLayout());
        contentContainer.add(mainPanel, BorderLayout.CENTER);
        contentContainer.add(selectorPanel, BorderLayout.SOUTH);

        // Pencereye ana paneli ekleme
        JPanel panelContainer = new JPanel(new BorderLayout());
        panelContainer.add(contentContainer, BorderLayout.CENTER);
        panelContainer.add(fontSizerPanel, BorderLayout.WEST);
        panelContainer.add(saveShowPanel, BorderLayout.NORTH);

        // Pencereyi tam ekran yapma
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Pencereye ana paneli ekleme
        add(panelContainer);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 
        setVisible(true);
       
       
    }
  

    	public void saveNotesToFile(String fileName) {
    	    try {
    	        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

    	        for (LinkedList<Not> noteList : notes.values()) {
    	            for (Not note : noteList) {
    	                writer.write(note.getName() + "\n");
    	                writer.write(note.getContent() + "\n");
    	                writer.write("---\n");
    	            }
    	        }

    	        writer.close();
    	        System.out.println("Notlar dosyaya kaydedildi.");
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    	}

    public void showNotesFromFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            String currentNoteName = null;
            StringBuilder currentNoteContent = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.equals("---")) {
                    if (currentNoteName != null) {
                        String content = currentNoteContent.toString();
                        addNoteToList(currentNoteName, content);
                        currentNoteName = null;
                        currentNoteContent = new StringBuilder();
                    }
                } else {
                    if (currentNoteName == null) {
                        currentNoteName = line;
                    } else {
                        currentNoteContent.append(line).append("\n");
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addNoteToList(String name, String content) {
    	Not note = new Not(name, content);

        if (notes.containsKey(name)) {
            notes.get(name).add(note);
        } else {
            LinkedList<Not> noteList = new LinkedList<>();
            noteList.add(note);
            notes.put(name, noteList);
            noteSelector.addItem(name);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add new note")) {
            String name = nameField.getText();
            String content = contentArea.getText();
            addNoteToList(name, content);

            // Not Adı ve Not İçeriği girdilerini temizleme
            nameField.setText("");
            contentArea.setText("");
        } else if (e.getActionCommand().equals("Delete note")) {
            // Seçili notu silin ve not listesinden kaldırma
            String selectedNote = (String) noteSelector.getSelectedItem();

            if (selectedNote != null) {
                notes.remove(selectedNote);
                noteSelector.removeItem(selectedNote);
                contentArea.setText("");

                // Notu dosyadan da silme
                String fileName = "notes.txt";
                deleteNoteFromFile(fileName, selectedNote);
            }
        } else if (e.getSource() == noteSelector) {
            // Seçili notu gösterme
            String selectedNote = (String) noteSelector.getSelectedItem();

            if (selectedNote != null) {
                LinkedList<Not> noteList = notes.get(selectedNote);
                Not note = noteList.getLast();
                contentArea.setText(note.getContent());
                nameField.setText(selectedNote);
            }
        } else if (e.getActionCommand().equals("Save notes")) {
            // Not Adı girdisi boş değilse notları dosyaya kaydetme
            String name = nameField.getText();

            if (!name.isEmpty()) {
                String fileName = "notes.txt";
                saveNotesToFile(fileName);
            }
        } else if (e.getActionCommand().equals("Show notes")) {
            // Notları dosyadan yükleme ve gösterme
            String fileName = "notes.txt";
            showNotesFromFile(fileName);
        }

        String selectedFont = (String) fontSelector.getSelectedItem();
        int selectedFontSize = Integer.parseInt((String) fontSizeSelector.getSelectedItem());
        Font font = new Font(selectedFont, Font.PLAIN, selectedFontSize);
        contentArea.setFont(font);
    }


    public void deleteNoteFromFile(String fileName, String noteName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            StringBuilder fileContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.equals(noteName)) {
                    // Not adını bulduğumuzda, ilgili notu atlayarak geçirme
                    while ((line = reader.readLine()) != null) {
                        if (line.equals("---")) {
                            break;
                        }
                    }
                } else {
                    // Not adını bulmadığımız durumlarda dosyaya geri yazma
                    fileContent.append(line).append("\n");
                }
            }

            reader.close();

            // Dosyayı yeniden yazma
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(fileContent.toString());
            writer.close();

            System.out.println("Not dosyadan silindi.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
            
        }
