package zad1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

class MainWindow extends JFrame {

    private static final String[] availableLanguages = {"pl", "en"};

    private final List<Record> records;

    private final LangSelectPanel langPanel;
    private String lang;

    private Properties translations;


    MainWindow(List<Record> records, Properties translations) {
        this.records = records;
        this.translations = translations;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Travel Data");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setLocation(screenSize.width / 2 - 400, screenSize.height / 2 - 300);

        langPanel = new LangSelectPanel(this);

        setContentPane(langPanel);

        pack();
        setVisible(true);
    }

    private List<Record> getRecords() {
        return records;
    }

    private void setLang(String actionCommand) {
        lang = actionCommand;

        String[] colNames = new String[]{"countryCode", "countryName", "startDate", "endDate", "location", "price", "currency"};

        Locale destLocale = Locale.forLanguageTag(lang);
        for (int i = 0; i < colNames.length; i++) {
            colNames[i] = translations.getProperty("en-" + destLocale.getLanguage() + "." + colNames[i], colNames[i]);
        }

        String dateFormat = "yyyy-MM-dd";

        Object[][] records = new Object[getRecords().size()][];
        for (int i = 0; i < getRecords().size(); i++) {
            Record record = getRecords().get(i);
            records[i] = record.toArrayStr(destLocale, dateFormat, translations, true).toArray();
        }

        JTable table = new JTable(records, colNames);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        table.setPreferredSize(new Dimension(900, 450));
        table.setLocation(screenSize.width / 2 - 450, screenSize.height / 2 - 225);

        remove(langPanel);

        add(new JScrollPane(table), BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private class LangSelectPanel extends JPanel implements ActionListener {

        MainWindow mainWindow;

        List<JButton> jButtons = new ArrayList<>();
        JLabel buttonsLabel;

        LangSelectPanel(MainWindow mainWindow) {
            this.mainWindow = mainWindow;

            int i = 0;

            buttonsLabel = new JLabel("Please select a language:");
            this.add(buttonsLabel, BorderLayout.NORTH);

            for (String lang : MainWindow.availableLanguages) {
                JButton jButton = new JButton(lang);
                jButton.setActionCommand(lang);
                jButton.addActionListener(this);
                jButtons.add(i, jButton);
                this.add(jButtons.get(i++));
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            mainWindow.setLang(e.getActionCommand());

            for (JButton jButton : jButtons) {
                this.remove(jButton);
                jButton.setEnabled(false);
            }
            this.remove(buttonsLabel);
        }
    }
}
