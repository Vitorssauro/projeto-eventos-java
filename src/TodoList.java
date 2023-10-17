import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TodoList extends JFrame {
    // atributos
    private JPanel mainPanel;
    private JTextField taskInputField;
    private JButton addButton;
    private JList<String> taskList;
    private DefaultListModel<String> listModel;
    private JButton deleteButton;
    private JButton markDoneButton;
    private JComboBox<String> filterComboBox;
    private JButton clearCompletedButton;
    private List<Task> tasks;
    private JLabel lblClock;
    private String formatedHour;

    // construtor
    public TodoList() {
        // Configuração da janela principal
        super("To-Do List App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 300);
        // Inicializa o painel principal
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        // Inicializa a lista de tasks e a lista de tasks concluídas
        tasks = new ArrayList<>();
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);

        // Inicializa campos de entrada, botões e JComboBox
        taskInputField = new JTextField();
        addButton = new JButton("Adicionar");
        deleteButton = new JButton("Excluir");
        markDoneButton = new JButton("Concluir");
        filterComboBox = new JComboBox<>(new String[] { "Todas", "Ativas",
                "Concluídas" });
        clearCompletedButton = new JButton("Limpar Concluídas");

        lblClock = new JLabel();// adicionar o relógio
        // Configuração do painel de entrada
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(taskInputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        // Configuração do painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(deleteButton);
        buttonPanel.add(markDoneButton);
        buttonPanel.add(filterComboBox);
        buttonPanel.add(clearCompletedButton);
        // Adiciona os componentes ao painel principal
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(taskList), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // adicionando o relógio
        buttonPanel.add(lblClock);
        // iniciando o timer e chamando a funcao de relogio
        Timer timer = new Timer(1000, new ClockListener());
        timer.start();

        // inicia os eventos dos botões
        setEvent();
        // Adiciona o painel principal à janela

        this.add(mainPanel);

    }

    public void addTime() {
        // setando relógio

    }

    class ClockListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Calendar now = Calendar.getInstance();
            formatedHour = String.format("%1$tH:%1$tM:%1$tS", now);
            lblClock.setText(formatedHour);
        }
    }

    private void setEvent() { // adicoionar eventos aos botões

        addButton.addActionListener(e -> {
            addTask();
        });

        deleteButton.addActionListener(e -> {
            deleteTask();
        });

        markDoneButton.addActionListener(e -> {
            markTaskDone();
        });

        filterComboBox.addActionListener(e -> {
            filterTasks();
        });

        clearCompletedButton.addActionListener(e -> {
            clearCompletedTasks();
        });

        taskInputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

    }

    // Configuração de Listener para os Eventos

    private void addTask() {
        // Adiciona uma nova task à lista de tasks
        String taskDescription = taskInputField.getText().trim();// remove espaços vazios
        if (!taskDescription.isEmpty()) {
            Task newTask = new Task(taskDescription, formatedHour);
            tasks.add(newTask);
            updateTaskList();
            taskInputField.setText("");
        }
    }

    private void deleteTask() {
        // Exclui a task selecionada da lista de tasks
        List<Task> selected = new ArrayList<>();
        int[] selectedTasks = taskList.getSelectedIndices();

        for (int i = 0; i < selectedTasks.length; i++) {
            Task selectedTask = tasks.get(i);
            selected.add(selectedTask);
        }

        tasks.removeAll(selected);
        updateTaskList();

    }

    private void markTaskDone() {
        // Marca a task selecionada como concluída
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < tasks.size()) {
            Task task = tasks.get(selectedIndex);
            task.setDone(true);
            updateTaskList();
            filterTasks();
        }
    }

    private void filterTasks() {
        // Filtra as tasks com base na seleção do JComboBox
        String filter = (String) filterComboBox.getSelectedItem();
        listModel.clear();
        for (Task task : tasks) {
            if (filter.equals("Todas") || (filter.equals("Ativas") &&
                    !task.isDone()) || (filter.equals("Concluídas") && task.isDone())) {
                listModel.addElement(task.getDescription());
            }
        }
    }

    private void clearCompletedTasks() {
        // Limpa todas as tasks concluídas da lista
        List<Task> completedTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isDone()) {
                completedTasks.add(task);
            }
        }
        tasks.removeAll(completedTasks);
        updateTaskList();
    }

    private void updateTaskList() {
        // Atualiza a lista de tasks exibida na GUI
        listModel.clear();
        for (Task task : tasks) {
            listModel.addElement(task.getDescription());

        }
    }

    public void run() {
        // Exibe a janela
        this.setVisible(true);
    }
}