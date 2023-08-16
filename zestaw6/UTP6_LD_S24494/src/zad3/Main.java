package zad3;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.Vector;
import java.util.concurrent.*;

public class Main extends JFrame implements ActionListener, ListSelectionListener, PropertyChangeListener {

    private final DefaultListModel<Task> taskOutputDataModel;
    private final JTextArea tasksListTextArea;
    private final JLabel selectedTaskStatus;
    int k = 0;
    int n = 15;
    Vector<Future<String>> tasks = new Vector<Future<String>>();
    ExecutorService pool = Executors.newFixedThreadPool(2);
    private JButton stop;
    private JButton start;
    private JButton refresh;
    private int selectedIndex;

    Main() {
        taskOutputDataModel = new DefaultListModel<>();
        JList<Task> list = new JList<>(taskOutputDataModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);

        JPanel buttons_panel = make_buttons_panel();
        add(buttons_panel, "North");

        tasksListTextArea = new JTextArea(20, 50);
        add(new JScrollPane(tasksListTextArea), "Center");
        add(new JScrollPane(list), "West");
        selectedTaskStatus = new JLabel();
        selectedTaskStatus.setText("No task is running.");
        add(selectedTaskStatus, "South");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        Main main = new Main();
    }

    private JPanel make_buttons_panel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        start = new JButton("Start a new task");
        start.setActionCommand("StartNew");
        start.addActionListener(this);
        p.add(start);
        stop = new JButton("Stop selected task");
        stop.setEnabled(false);
        stop.setActionCommand("StopSelected");
        stop.addActionListener(this);
        p.add(stop);
        refresh = new JButton("Refresh task status");
        refresh.setActionCommand("RefreshStatus");
        refresh.addActionListener(this);
        p.add(refresh);
        return p;
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        try {
            Method m = this.getClass().getDeclaredMethod("task" + cmd);
            m.invoke(this);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        updateSelectedTaskStatus();
    }

    private void updateSelectedTaskStatus() {
        if (selectedIndex < 0) {
            selectedTaskStatus.setText("No task selected.");
        }
        selectedTaskStatus.setText("ExampleTask " + selectedIndex + ": " + getTaskResultStr());
    }

    public void taskRefreshStatus() {
        updateSelectedTaskStatus();
    }

    public void taskStartNew() {
        try {
            tasks.addElement(pool.submit(new ExampleTask(k++, 10)));
        } catch (RejectedExecutionException exc) {
        }
    }

    public void taskStopSelected() {
        if (selectedIndex < 0) return;
        tasks.get(selectedIndex).cancel(true);
        stop.setEnabled(false);
    }

    public String getTaskResultStr() {
        if (tasks.get(selectedIndex).isCancelled()) {
            return "Cancelled.";
        } else if (tasks.get(selectedIndex).isDone()) {
            try {
                return "Finished with result: " + tasks.get(selectedIndex).get();
            } catch (Exception exc) {
                return exc.getMessage();
            }
        } else {
            return "Running or queued.";
        }
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (selectedIndex >= 0) taskOutputDataModel.get(selectedIndex).setPropertyChangeListener(null);
        selectedIndex = ((JList<String>) e.getSource()).getSelectedIndex();
        stop.setEnabled(selectedIndex >= 0 && !tasks.get(selectedIndex).isDone() && !tasks.get(selectedIndex).isCancelled());
        if (selectedIndex >= 0) {
            tasksListTextArea.setText(taskOutputDataModel.get(selectedIndex).output);
            taskOutputDataModel.get(selectedIndex).setPropertyChangeListener(this);
        }
        updateSelectedTaskStatus();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        tasksListTextArea.setText((String) evt.getNewValue());
        updateSelectedTaskStatus();
    }

    class ExampleTask implements Callable<String> {

        private final int taskId;
        private final int duration;

        public ExampleTask(int taskId, int duration) {
            this.taskId = taskId;
            this.duration = duration;
            taskOutputDataModel.addElement(new Task("ExampleTask " + taskId));
        }

        public String call() throws Exception {
            for (int i = 0; i < duration; i++) {
                taskOutputDataModel.get(taskId).addOutputLine("Task progress: " + (float) i / duration + '\n');
                if (Thread.currentThread().isInterrupted()) {
                    taskOutputDataModel.get(taskId).addOutputLine("task interrupted\n");
                    return "Interrupted";
                }
                Thread.sleep(1000);
            }
            taskOutputDataModel.get(taskId).addOutputLine("Task finished !\n");
            return "Success";
        }
    }

}