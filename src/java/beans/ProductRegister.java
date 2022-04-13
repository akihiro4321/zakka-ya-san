/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import db.ProductDb;
import java.io.File;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import util.FileUtil;

/**
 *
 * @author okuya
 */
@Named
@RequestScoped
public class ProductRegister implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    ProductDb db;
    
    @Inject
    transient Logger log;
    
    @Inject
    FileUtil fu;
    
    private String filePath;

    public void fileChoose() {
        JFileChooser chooser = new JFileChooser();
        //ファイルの拡張子を設定
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("csv");
//        chooser.setFileFilter(filter);
        //ダイアログを表示setSelectedFile
        chooser.showOpenDialog(null);
        //ダイアログの選択結果を取得
        File file = chooser.getSelectedFile();
        if(file != null) {
            filePath = file.getPath();
        } else {
           log.warning("csvファイルを選択してください。"); 
        }
    }
}
