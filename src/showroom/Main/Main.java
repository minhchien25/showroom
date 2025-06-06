
package showroom.Main;



import com.formdev.flatlaf.FlatDarculaLaf; 
import showroom.view.MainDashboardView;

public class Main {
    public static void main(String[] args) {
     
        
         FlatDarculaLaf.setup(); 

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainDashboardView().setVisible(true);
            }
        });
    }
}