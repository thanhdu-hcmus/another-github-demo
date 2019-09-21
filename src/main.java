import javax.swing.*;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.event.*;
import java.awt.GridLayout;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

public class main {
    static JLabel nhanthang, nhannam,nhangio;
    static JButton btnNgayTrc, btnNgaySau, btnThangSau, btnThangTrc, nhap;
    static JComboBox cmbNam;
    static JFrame frame,mini;
    static JTable table;
    static JPanel pnlCalendar,pnN,pnS,pnC;
    static String temp, path;
    static Object[] row;
    static String[] str;
    static Scanner x;
    static int year, month,day,currentYear;

    public static void main(String[] args) throws IOException {
        //Giúp frame mềm mại và đẹp hơn
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}

        //Khởi tạo frame,các label, panel, ô chọn và các nút
        frame = new JFrame("Calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(630,490);

        nhannam = new JLabel("Change year: ");
        cmbNam = new JComboBox();
        btnThangTrc = new JButton ("PrevMonth");
        btnThangSau = new JButton ("NextMonth");
        btnNgayTrc = new JButton ("PrevDay");
        btnNgaySau = new JButton ("NextDay");
        nhap = new JButton("Ghi chú");

        pnlCalendar = new JPanel();
        pnlCalendar.setLayout(new BorderLayout());


        pnN = new JPanel(new FlowLayout());
        pnN.setBackground(Color.LIGHT_GRAY);
        pnN.setPreferredSize(new Dimension(600,35));

        pnS = new JPanel();
        pnS.setBackground(Color.LIGHT_GRAY);
        pnS.setPreferredSize(new Dimension(600,30));

        pnC = new JPanel();
        pnC.setPreferredSize(new Dimension(600,360));


        // Lấy ngày tháng hiện tại theo máy tính
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        year = localDate.getYear();
        month = localDate.getMonthValue();
        day = localDate.getDayOfMonth();
        CDTime dt = new CDTime(day,month,year);
        currentYear = year;

        // Tạo label hiển thị giờ
        nhangio = new JLabel();
        clock();

        // Tạo label hiển thị tháng hiện tại
        String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        nhanthang = new JLabel(months[month-1]);
        nhanthang.setFont(new Font("Cambria",Font.BOLD,18));
        nhannam.setFont(new Font("Cambria",Font.BOLD,13));

        //  Cấp dữ liệu vào ô chọn năm
        for (int i= year - 100; i<= year+100; i++){
            cmbNam.addItem(String.valueOf(i));
        }
        cmbNam.setSelectedItem(String.valueOf(year));

        //Set bounds các label và button
        pnN.setLayout(null);
        pnS.setLayout(null);
        btnThangTrc.setBounds(10,8,100,20);
        btnThangSau.setBounds(120,8,100,20);
        btnNgayTrc.setBounds(390,8,100,20);
        btnNgaySau.setBounds(500,8,100,20);
        nhanthang.setBounds(300-nhanthang.getPreferredSize().width/2,5,100,22);
        nhangio.setBounds(300-nhangio.getPreferredSize().width/2,5,100,20);
        nhannam.setBounds(438, 4, 100, 20);
        cmbNam.setBounds(520, 5, 80, 20);
        nhap.setBounds(30,5,100,20);


        // Gán các nút và label vào panel
        pnS.add(nhannam);
        pnS.add(cmbNam);
        pnS.add(nhangio);
        pnS.add(nhap);
        pnN.add(btnThangTrc);
        pnN.add(btnThangSau);
        pnN.add(nhanthang);
        pnN.add(btnNgayTrc);
        pnN.add(btnNgaySau);

        //Tạo dạng lưới
        GridLayout grid = new GridLayout(7,7,1,1);
        pnC.setLayout(grid);

        // Tạo các label hiển thị thứ
        JLabel arrLbl[] = new JLabel[7];
        arrLbl[0] = new JLabel("SUN");
        // Đưa các chữ ra giữa label
        arrLbl[0].setVerticalAlignment(JLabel.CENTER);
        arrLbl[0].setHorizontalAlignment(JLabel.CENTER);
        //Chỉnh sửa và hiển thị font, màu cho chữ
        arrLbl[0].setOpaque(true);
        arrLbl[0].setBackground(new Color(255, 220, 220));
        arrLbl[0].setFont(new Font("Cambria",Font.BOLD,12));
        pnC.add(arrLbl[0]);

        arrLbl[1] = new JLabel("MON");
        arrLbl[1].setVerticalAlignment(JLabel.CENTER);
        arrLbl[1].setHorizontalAlignment(JLabel.CENTER);
        arrLbl[1].setOpaque(true);
        arrLbl[1].setBackground(new Color(220, 220, 255));
        arrLbl[1].setFont(new Font("Cambria",Font.BOLD,12));
        pnC.add(arrLbl[1]);

        arrLbl[2] = new JLabel("TUE");
        arrLbl[2].setVerticalAlignment(JLabel.CENTER);
        arrLbl[2].setHorizontalAlignment(JLabel.CENTER);
        arrLbl[2].setOpaque(true);
        arrLbl[2].setBackground(new Color(220, 220, 255));
        arrLbl[2].setFont(new Font("Cambria",Font.BOLD,12));
        pnC.add(arrLbl[2]);

        arrLbl[3] = new JLabel("WED");
        arrLbl[3].setVerticalAlignment(JLabel.CENTER);
        arrLbl[3].setHorizontalAlignment(JLabel.CENTER);
        arrLbl[3].setOpaque(true);
        arrLbl[3].setBackground(new Color(220, 220, 255));
        arrLbl[3].setFont(new Font("Cambria",Font.BOLD,12));
        pnC.add(arrLbl[3]);

        arrLbl[4] = new JLabel("THU");
        arrLbl[4].setVerticalAlignment(JLabel.CENTER);
        arrLbl[4].setHorizontalAlignment(JLabel.CENTER);
        arrLbl[4].setOpaque(true);
        arrLbl[4].setBackground(new Color(220, 220, 255));
        arrLbl[4].setFont(new Font("Cambria",Font.BOLD,12));
        pnC.add(arrLbl[4]);

        arrLbl[5] = new JLabel("FRI");
        arrLbl[5].setVerticalAlignment(JLabel.CENTER);
        arrLbl[5].setHorizontalAlignment(JLabel.CENTER);
        arrLbl[5].setOpaque(true);
        arrLbl[5].setBackground(new Color(220, 220, 255));
        arrLbl[5].setFont(new Font("Cambria",Font.BOLD,12));
        pnC.add(arrLbl[5]);

        arrLbl[6] = new JLabel("SAT");
        arrLbl[6].setVerticalAlignment(JLabel.CENTER);
        arrLbl[6].setHorizontalAlignment(JLabel.CENTER);
        arrLbl[6].setOpaque(true);
        arrLbl[6].setBackground(new Color(255, 220, 220));
        arrLbl[6].setFont(new Font("Cambria",Font.BOLD,12));
        pnC.add(arrLbl[6]);

        //Tạo đường dẫn tới file lưu ghi chú
        path ="D:\\Test\\src\\data.txt";

        //Lấy ngày đầu của tuần
        int dayOfWeek = dt.GetDayOfWeek(day,month,year);

        //Lấy số lượng ngày của tháng trước
        int numDaysOfPrevMonth = dt.GetDaysOfMonth(month-1);

        //Tạo label hiển thị các ngày trong tháng
        JLabel arrBtn[] = new JLabel[42];

        //Tạo mảng các ngày trong tháng
        str = new String[dt.GetDaysOfMonth(month)];
        for(int i = 0; i< dt.GetDaysOfMonth(month);i++){
            str[i] = dt.GetYear()+"-"+dt.GetMonth()+"-"+(i+1);
        }

        System.out.println(str[1]);

        //Hiển thị các ngày của tháng trước có trong tuần của ngày đầu tiên của tháng hiện tại
        for (int i = 0; i<dayOfWeek;i++){
            arrBtn[i] = new JLabel(Integer.toString(numDaysOfPrevMonth-dayOfWeek+i+1));
            arrBtn[i].setOpaque(true);
            arrBtn[i].setBackground(Color.LIGHT_GRAY);
            arrBtn[i].setForeground(Color.WHITE);
            arrBtn[i].setVerticalAlignment(JLabel.CENTER);
            arrBtn[i].setHorizontalAlignment(JLabel.CENTER);
            pnC.add(arrBtn[i]);
        }

        int j = 1,k = 1;

        //Hiển thị các ngày của tháng hiện tại
        for (int i = dayOfWeek; i<=dt.GetDaysOfMonth(month)+dayOfWeek-1;i++){
            arrBtn[i] = new JLabel(Integer.toString(j));
            j++;
            arrBtn[i].setVerticalAlignment(JLabel.CENTER);
            arrBtn[i].setHorizontalAlignment(JLabel.CENTER);
            //Hiển thị màu khác lên label được chọn
            if (i == day+dayOfWeek-1){
                arrBtn[i].setOpaque(true);
                arrBtn[i].setBackground(Color.DARK_GRAY);
                arrBtn[i].setForeground(Color.WHITE);
            }
            pnC.add(arrBtn[i]);
        }

        //Gọi file ghi chú
        File f1 = new File("D:\\Test\\src\\data.txt");
        //Xác định xem file có tồn tại
        if(f1.exists()==true) {
            String[] words = null;
            // Đọc file ghi chú
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            String s;
            while ((s = br.readLine()) != null) // Đọc các dòng trong file ghi chú
            {
                words = s.split(","); // Lấy các từ được phân cách nhau bằng dấu ","
                for (String word : words) {     // Đọc các từ

                    for (int i = 0; i < dt.GetDaysOfMonth(month); i++) {
                        if (word.equals(str[i])) // Xét xem các từ được đọc có nằm trong mảng ngày của tháng không
                        {
                            //Nếu có thì hiển thị màu khác cho label
                            arrBtn[i + dayOfWeek].setOpaque(true);
                            arrBtn[i + dayOfWeek].setBackground(new Color(90, 170, 191));
                            arrBtn[i + dayOfWeek].setForeground(Color.WHITE);
                            System.out.println(i + dayOfWeek);

                        }
                        pnC.add(arrBtn[i + dayOfWeek]);
                        if (i + 1 == day && word.equals(str[i])) { // Xét xem ngày được chọn có ghi chú hay không
                            //Nếu có thì hiển thị màu khác cho label
                            arrBtn[i + dayOfWeek].setOpaque(true);
                            arrBtn[i + dayOfWeek].setBackground(new Color(64, 92, 150));
                            arrBtn[i + dayOfWeek].setForeground(Color.WHITE);
                        }
                        pnC.add(arrBtn[i + dayOfWeek]);

                    }

                }
            }
            br.close();// Dừng việc đọc
        }

        // Hiển thị các ngày của tháng sau có trong tuần của ngày cuối của tháng hiện tại
        for (int i = dt.GetDaysOfMonth(month)+dayOfWeek ; i< 42;i++){
            arrBtn[i] = new JLabel(Integer.toString(k));
            k++;
            arrBtn[i].setOpaque(true);
            arrBtn[i].setBackground(Color.LIGHT_GRAY);
            arrBtn[i].setForeground(Color.WHITE);
            arrBtn[i].setVerticalAlignment(JLabel.CENTER);
            arrBtn[i].setHorizontalAlignment(JLabel.CENTER);
            pnC.add(arrBtn[i]);
        }
        //Thêm các panel nhỏ vào panel tổng và thêm panel đó vào frame
        pnlCalendar.add(pnN,BorderLayout.NORTH);
        pnlCalendar.add(pnS,BorderLayout.SOUTH);
        pnlCalendar.add(pnC,BorderLayout.CENTER);
        frame.add(pnlCalendar);


        //################## Các button##################

        //Nút nhấn để lùi về một ngày
        btnNgaySau.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reColor(arrBtn,dt); // Chỉnh lại màu cho các label
                dt.gotoNextDay();  // Tiến tới một ngày
                refreshCalendar(dt,pnC,arrBtn); // Làm mới lại lịch
            }
        });

        //Nút nhấn để tiến lên một ngày
        btnNgayTrc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reColor(arrBtn,dt);
                dt.gotoPrvDay(); // Lùi lại một ngày
                refreshCalendar(dt,pnC,arrBtn);
            }
        });

        //Nút nhấn để lùi về một tháng
        btnThangTrc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reColor(arrBtn,dt);
                dt.gotoPrvMonth(); //Lùi lại một tháng
                refreshCalendar(dt,pnC,arrBtn);
            }
        });

        //Nút nhấn để tiến lên một tháng
        btnThangSau.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reColor(arrBtn,dt);
                dt.gotoNextMonth(); // Tiến lên một tháng
                refreshCalendar(dt,pnC,arrBtn);
            }
        });

        //Nút nhấn để chọn năm
        cmbNam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cmbNam.getSelectedItem() != null){ // Xét xem ô chọn năm có được chọn không
                    // Lấy giá trị của ô chọn
                    String b = cmbNam.getSelectedItem().toString();
                    // Gán năm hiện tại bằng năm của ô chọn
                    currentYear = Integer.parseInt(b);
                    reColor(arrBtn,dt);
                    dt.setYear(currentYear); // Thay đổi năm theo ô chọn
                    refreshCalendar(dt,pnC,arrBtn);
                }
            }
        });

        //Nút nhấn để mở ghi chú
        nhap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filePath = "D:\\Test\\src\\data.txt";
                File file = new File(filePath) ;

                //Tạo frame
                mini = new JFrame("Xem ghi chú");
                mini.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mini.setSize(605,320);
                mini.setVisible(true);
                mini.setResizable(false);

                // Tạo bảng và tạo định dạng cột cho bảng
                table = new JTable();
                Object[] columns = {"Id","Lưu ý"};
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columns);

                // Tạo kiểu cho bảng
                table.setModel(model);
                table.setSize(200,590);
                table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
                TableColumn col = table.getColumnModel().getColumn(0);
                col.setPreferredWidth(50);
                col = table.getColumnModel().getColumn(1);
                col.setPreferredWidth(535);
                table.setForeground(Color.black);
                table.setBackground(new Color(245,245,245));
                table.getTableHeader().setDefaultRenderer(new HeaderColor());
                table.getTableHeader().setFont(new Font("Cambria", Font.BOLD, 16)); // font name style size
                ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER); // center header text
                table.setFont(new Font("Cambria",Font.PLAIN,14));
                table.setRowHeight(30);

                // Tạo ô ghi chú
                JTextField textId = new JTextField();
                JTextField textNote = new JTextField();

                // Tạo các nút
                JButton btnAdd = new JButton("Add");
                JButton btnDelete = new JButton("Delete");
                JButton btnUpdate = new JButton("Update");

                // Tạo kích cỡ các nút và ô ghi chú
                textId.setBounds(20, 210, 70, 25);
                textNote.setBounds(105, 210, 400, 25);
                btnAdd.setBounds(20, 245, 100, 25);
                btnUpdate.setBounds(150, 245, 100, 25);
                btnDelete.setBounds(280, 245, 100, 25);

                // Tạo thanh cuộn
                JScrollPane pane = new JScrollPane(table);
                pane.setBounds(2, 0, 585, 200);

                //Thêm các nút, ô ghi chú và thanh cuộn vào frame
                mini.setLayout(null);
                mini.add(pane);
                mini.add(textId);
                mini.add(textNote);
                mini.add(btnAdd);
                mini.add(btnDelete);
                mini.add(btnUpdate);

                // Tạo cột
                row = new Object[2];
                // Tải dữ liệu lên bảng
                importRecord(filePath,row,model,dt);


                //*************** Các nút nhấn ***************

                //Nút thêm ghi chú
                btnAdd.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Gọi file ghi chú
                        File file = new File("D:\\Test\\src\\data.txt") ;
                        //Khi thêm ghi chú thì sẽ đổi màu label được chọn
                        arrBtn[dt.GetDay()+dayOfWeek-1].setOpaque(true);
                        arrBtn[dt.GetDay()+dayOfWeek-1].setBackground(new Color(64,92,150));
                        try {
                            // Ghi ghi chú vào file
                            BufferedWriter write = new BufferedWriter(new FileWriter( file,true));
                            write.write(dt.GetYear()+"-"+dt.GetMonth()+"-"+dt.GetDay()+","+textId.getText()+","+textNote.getText());
                            write.append("\n");
                            write.close();
                        }
                        catch (IOException e1) {
                            e1.printStackTrace();
                        } ;

                        str = new String[]{path};
                        row[0] = textId.getText();
                        row[1] = textNote.getText();
                        // Thêm dòng vào bảng
                        model.addRow(row);
                    }
                });

                // Nút xóa ghi chú
                btnDelete.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // i = giá trị của bảng được chọn
                        int i = table.getSelectedRow();
                        // Gán ô ghi chú ID bằng giá trị của ô ID được chọn
                        textId.setText(model.getValueAt(i, 0).toString());
                        String removeterm = textId.getText();
                        String filepath = "D:\\Test\\src\\data.txt";
                        File file = new File(filepath);
                        if(i >= 0){ //Xét xem ô ID còn ghi chú không
                            // bỏ dòng được chọn khỏi bảng
                            model.removeRow(i);
                            System.out.println(removeterm);
                            removeRecord(filepath,removeterm,dt);

                            // Xét xem ô ID không còn giá trị thì đổi label thành màu khác
                            if(i == 0){
                                arrBtn[dt.GetDay()+dayOfWeek-1].setOpaque(true);
                                arrBtn[dt.GetDay()+dayOfWeek-1].setBackground(Color.DARK_GRAY);
                            }
                        }
                        else{
                            System.out.println("Delete Error");
                        }
                    }
                });

                // Tạo khả năng chọn trong bảng
                table.addMouseListener(new MouseAdapter(){

                    @Override
                    public void mouseClicked(MouseEvent e){

                        // i = giá trị của ID dòng được chọn
                        int i = table.getSelectedRow();
                        textId.setText(model.getValueAt(i, 0).toString());
                        textNote.setText(model.getValueAt(i, 1).toString());

                        //Lưu ID vào biến tạm
                        temp = textId.getText();

                    }
                });

                // Nút cập nhật bảng
                btnUpdate.addActionListener(new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        // i = giá trị của ID dòng được chọn
                        int i = table.getSelectedRow();
                        String filepath ="D:\\Test\\src\\data.txt";

                        String updateterm = temp;   // Gán biến tạm vào điều kiện để update
                        String updateID = textId.getText(); // Gán ID update bằng giá trị ở ô ID
                        String updateNote = textNote.getText(); // Gán ghi chú update bằng giá trị ở ô ghi chú
                        if(i >= 0)
                        {
                            model.setValueAt(textId.getText(), i, 0);
                            model.setValueAt(textNote.getText(), i, 1);
                            updateRecord(filepath,updateterm,updateID,updateNote,dt);
                        }
                        else{
                            System.out.println("Update Error");
                        }
                    }
                });
            }
        });

        //Hiển thị frame và khiến frame không điều chỉnh được
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void importRecord(String filepath,Object[] row,DefaultTableModel model,CDTime dt) {
        String date = "";String ID = ""; String note = "";
        try {
            // Đọc và lọc các từ trong file ghi chú
            x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");
            // Xét xem trong file vẫn còn ghi chú hay không
            while (x.hasNext()){
                // Gán các giá trị vừa lọc vào các biến
                date = x.next();
                ID = x.next();
                note = x.next();
                // Gán dòng 0 bằng biến ID
                row[0] = ID;
                // Gán dòng 1 banwngf biến note
                row[1] = note;

                // Xét xem biến ngày có giống với ngày hiện tại và nếu có thì thêm các ghi chú vào bảng
                if(date.equals(dt.GetYear()+"-"+dt.GetMonth()+"-"+dt.GetDay())==true) {
                    model.addRow(row);
                }
            }
            x.close(); // dừng dọc
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Không có ghi chú");
        }
    }

    public static void removeRecord(String filepath,String removeterm,CDTime dt){
        //Tạo file tạm
        String tempfile = "D:\\Test\\src\\data.txt";
        // Gọi file ghi chú và file tạm
        File oldfile = new File(filepath);
        File newfile = new File(tempfile);
        String date = "" ;String ID = ""; String note = "";
        try {
            // Ghi vào file tạm
            FileWriter fw = new FileWriter(tempfile,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            // Đọc và lọc ghi chú ở file ghi chú
            x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");
            while (x.hasNext()){
                date = x.next();
                ID = x.next();
                note = x.next();
                //Xét xem biến ID và biến date có giống với điều kiện xóa và ngày của ghi chú không
                if(!ID.equals(removeterm) || !date.equals(dt.GetYear()+"-"+dt.GetMonth()+"-"+dt.GetDay())){
                    // Nếu không giống thì in ghi chú không giống vào file tạm
                    pw.println(date+","+ID+","+note);
                }
            }
            // Dùng đọc và in
            x.close();
            pw.flush();
            pw.close();
            // Xóa file ghi chú
            oldfile.delete();
            // Tạo một biến File và gán tên file ghi chú vào biến đó
            File dump = new File(filepath);
            // Đổi tên file tạm bằng tên file ghi chú
            newfile.renameTo(dump);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Không có ghi chú để xóa ");
        }
    }

    public static void updateRecord(String filepath,String updateterm,String updateid,String updatenote,CDTime dt){
        String tempfile = "D:\\Test\\src\\data.txt";
        File oldfile = new File(filepath);
        File newfile = new File(tempfile);
        String ID = ""; String note = ""; String date = "";
        try {
            FileWriter fw = new FileWriter(tempfile,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            x = new Scanner(new File(filepath));
            x.useDelimiter("[,\n]");
            while (x.hasNext()){
                date = x.next();
                ID = x.next();
                note = x.next();
                //Xét xem biến ID và biến date có giống với điều kiện xóa và ngày của ghi chú không
                if(ID.equals(updateterm) && date.equals(dt.GetYear()+"-"+dt.GetMonth()+"-"+dt.GetDay())){
                    // Nếu có thì in ghi chú mới vào file tạm
                    pw.println(date+","+updateid+","+updatenote);
                }
                else {
                    // Nếu không thì in ghi chú không giống vào file tạm
                    pw.println(date+","+ID+","+note);
                }
            }
            x.close();
            pw.flush();
            pw.close();
            oldfile.delete();
            File dump = new File(filepath);
            newfile.renameTo(dump);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Chọn ghi chú cần sửa");
        }
    }

    public static void refreshCalendar(CDTime ct,JPanel pnC,JLabel[] arrBtn){
        String[] months =  {"January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October", "November", "December"};
        // Thay đổi label tháng bằng tháng thay đổi
        nhanthang.setText(months[ct.GetMonth()-1]); //Refresh the month label (at the top)
        nhanthang.setFont(new Font("Cambria",Font.BOLD,18));
        nhanthang.setBounds(300-nhanthang.getPreferredSize().width/2,5,100,22);
        // Thay đổi ô chọn năm bằng giá trị được chọn
        cmbNam.setSelectedItem(String.valueOf(ct.GetYear()));

        int dayOfWeek = ct.GetDayOfWeek(ct.GetDay(),ct.GetMonth(),ct.GetYear());
        System.out.println(dayOfWeek);
        int numDaysOfPrevMonth = ct.GetDaysOfMonth(ct.GetMonth()-1);
        int numDaysOfThisMonth = ct.GetDaysOfMonth(ct.GetMonth());

        for (int i = 0; i<dayOfWeek;i++){
            arrBtn[i].setText(Integer.toString(numDaysOfPrevMonth-dayOfWeek+i+1));
            arrBtn[i].setOpaque(true);
            arrBtn[i].setBackground(Color.LIGHT_GRAY);
            arrBtn[i].setForeground(Color.WHITE);
            arrBtn[i].setVerticalAlignment(JLabel.CENTER);
            arrBtn[i].setHorizontalAlignment(JLabel.CENTER);
            pnC.add(arrBtn[i]);
        }

        int j = 1;
        for (int i = dayOfWeek; i<= numDaysOfThisMonth+dayOfWeek-1;i++){
            arrBtn[i].setText(Integer.toString(j));
            j++;
            arrBtn[i].setVerticalAlignment(JLabel.CENTER);
            arrBtn[i].setHorizontalAlignment(JLabel.CENTER);
            pnC.add(arrBtn[i]);
        }
        int l = 0;
        for (int i = dayOfWeek; i<=numDaysOfThisMonth+dayOfWeek-1;i++) {
            int t = Integer.parseInt(arrBtn[i].getText());
            if (t == ct.GetDay()) {
                arrBtn[i].setOpaque(true);
                arrBtn[i].setBackground(Color.DARK_GRAY);
                arrBtn[i].setForeground(Color.WHITE);
            }

        }

        str = new String[numDaysOfThisMonth];
        for(int i = 0; i< numDaysOfThisMonth;i++){
            str[i] = ct.GetYear()+"-"+ct.GetMonth()+"-"+(i+1);
        }

        try {
            File f1 = new File("D:\\Test\\src\\data.txt");
            String[] words = null;
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            String s;
            while ((s = br.readLine()) != null)
            {
                words = s.split(",");
                for (String word : words) {
                    for (int i = 0; i < numDaysOfThisMonth; i++) {
                        if (word.equals(str[i]))
                        {
                            arrBtn[i+dayOfWeek].setOpaque(true);
                            arrBtn[i+dayOfWeek].setBackground(new Color(90,170,191));//
                            arrBtn[i+dayOfWeek].setForeground(Color.WHITE);

                        }
                        pnC.add(arrBtn[i+dayOfWeek]);
                        if(i+1 == ct.GetDay() && word.equals(str[i])){
                            arrBtn[i+dayOfWeek].setOpaque(true);
                            arrBtn[i+dayOfWeek].setBackground(new Color(64,92,150));
                            arrBtn[i+dayOfWeek].setForeground(Color.WHITE);
                        }
                        pnC.add(arrBtn[i+dayOfWeek]);

                    }

                }
            }
            br.close();
        }
        catch (IOException e){

        }


        int k = 1;
        for (int i = numDaysOfThisMonth+dayOfWeek ; i< 42;i++){
            arrBtn[i].setText(Integer.toString(k));
            k++;
            arrBtn[i].setOpaque(true);
            arrBtn[i].setBackground(Color.LIGHT_GRAY);
            arrBtn[i].setForeground(Color.WHITE);
            arrBtn[i].setVerticalAlignment(JLabel.CENTER);
            arrBtn[i].setHorizontalAlignment(JLabel.CENTER);
            pnC.add(arrBtn[i]);
        }
    }

    public static void reColor (JLabel[] arrBtn1,CDTime ct){
        // Đổi tất cả các màu thành màu mặc định
        for (int i = 0;i<42 ;i++){
            arrBtn1[i].setOpaque(true);
            arrBtn1[i].setBackground(new JLabel().getBackground());
            arrBtn1[i].setForeground(new JLabel().getForeground());
        }

    }

    public static void clock(){
        Thread thread = new Thread(){
            public void run(){
                try{
                    for(;;){
                        Calendar time = new GregorianCalendar();
                        int hour = time.get(Calendar.HOUR);
                        int min = time.get(Calendar.MINUTE);
                        int sec = time.get(Calendar.SECOND);
                        int am_pm = time.get(Calendar.AM_PM);
                        String d_n = "";
                        if(am_pm == 0){
                            d_n = "AM";
                        }
                        else d_n = "PM";
                        String gio = (hour > 9) ? "" + hour + "" : "0" + hour;
                        String phut = (min > 9) ? "" + min + "" : "0" + min;
                        String giay = (sec > 9) ? "" + sec + "" : "0" + sec;
                        nhangio.setText(gio+":"+phut+":"+giay+" "+d_n);
                        nhangio.setBounds(300-nhangio.getPreferredSize().width/2,5,100,20);
                        sleep(1000);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    static public class HeaderColor extends DefaultTableCellRenderer{
        public HeaderColor(){
            setOpaque(true);
        }
        public Component getTableCellRendererComponent(JTable mytable,Object value,boolean selected,boolean focused,int row,int column){
            super.getTableCellRendererComponent(mytable, value, selected, focused, row, column);
            setBackground(new java.awt.Color(64,64,64));
            setForeground(new java.awt.Color(255,255,255));
            return this;
        }
    }
}
