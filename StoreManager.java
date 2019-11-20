import javax.swing.*;

public class StoreManager {
    public static String dbms = "SQLite";
    public static String path = "/Users/liyuemeng/Desktop/IdeaProjects/Project2/store15.db";

    IDataAdapter dataAdapter = null;
    private static StoreManager instance = null;

    public INetworkAdapter getNetworkAdapter() {
        return networkAdapter;
    }

    INetworkAdapter networkAdapter = null;

    public static StoreManager getInstance() {
        if (instance == null) {
            instance = new StoreManager(dbms, path);
        }
        return instance;
    }

    private StoreManager(String dbms, String dbfile) {
        if (dbms.equals("Oracle"))
            dataAdapter = new OracleDataAdapter();
        else
        if (dbms.equals("SQLite"))
            dataAdapter = new SQLiteDataAdapter();
        else
        if (dbms.equals("Network"))
            dataAdapter = new NetworkDataAdapter();

        dataAdapter.connect(dbfile);
        networkAdapter = new SocketNetworkAdapter();

    }

    public IDataAdapter getDataAdapter() {
        return dataAdapter;
    }

    public void setDataAdapter(IDataAdapter a) {
        dataAdapter = a;
    }


    public void run() {
        MainUI ui = new MainUI();
        ui.view.setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("Hello class!");
        if (args.length > 0) { // having runtime arguments
            dbms = args[0];
            if (args.length == 1) { // do not have 2nd arguments for dbfile
                if (dbms.equals("SQLite")) {
                    JFileChooser fc = new JFileChooser();
                    if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                        path = fc.getSelectedFile().getAbsolutePath();
                }
                else
                    path = JOptionPane.showInputDialog("Enter address of database server as host:port");
            }
            else
                path = args[1];
        }
        StoreManager.getInstance().run();
    }


}
