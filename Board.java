import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Classe que representa uma Tarefa com prioridade
class Task implements Serializable {
    private String description;
    private boolean isCompleted;
    private String priority;  // Adicionando a prioridade

    public Task(String description, String priority) {
        this.description = description;
        this.isCompleted = false;
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getPriority() {
        return priority;
    }

    public void completeTask() {
        this.isCompleted = true;
    }

    public void printTask() {
        String status = isCompleted() ? "[Completed]" : "[Pending]";
        System.out.println(status + " " + description + " (Priority: " + priority + ")");
    }
}

// Classe que representa o Board de Tarefas com persistência de dados
class TaskBoard {
    private List<Task> tasks;
    private final String FILE_NAME = "tasks.ser";

    public TaskBoard() {
        tasks = loadTasks();
    }

    // Método para salvar tarefas no arquivo
    public void saveTasks() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(tasks);
        } catch (IOException e) {
            System.out.println("Erro ao salvar as tarefas: " + e.getMessage());
        }
    }

    // Método para carregar tarefas do arquivo
    public List<Task> loadTasks() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Task>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    // Adicionar tarefa
    public void addTask(String description, String priority) {
        tasks.add(new Task(description, priority));
        saveTasks();
    }

    // Concluir tarefa
    public void completeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).completeTask();
            saveTasks();
        } else {
            System.out.println("Tarefa não encontrada.");
        }
    }

    // Mostrar tarefas
    public void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Nenhuma tarefa registrada.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.print((i + 1) + ". ");
                tasks.get(i).printTask();
            }
        }
    }
}
