package util;

import dto.ColumnInfo;
import dto.TableDto;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * テキストファイルの入力と出力を行うクラスメソッド<br/>
 *
 * @author t.kawaba@gmail.com
 */
public class FileUtil {

    static final Logger log = Logger.getLogger(FileUtil.class.getName());

    /**
     * テキストファイルの全内容を読みだして文字列として返す
     *
     * @param fpath テキストファイルのパス<br/>
     * resourcesからの相対パスで指定する（/resources/～）
     * @return	テキスト
     */
    public static String getText(String fpath) {
        String path = getRealPath(fpath);		// 絶対パスに変換
        StringBuilder text = new StringBuilder();
        try (InputStream is = new FileInputStream(path);
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "Shift_Jis"));) {
            String line;
            while ((line = in.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
        } catch (IOException e) {
            log.severe("★ファイルが見つからない:" + fpath);
        }
        return text.toString();
    }

    /**
     * テキストをファイルに書き出す
     *
     * @param text 出力するテキスト
     * @param fpath テキストファイルのパス<br/>
     * resourcesからの相対パスで指定する（/resources/～）
     */
    public static void putText(String text, String fpath) {
        String path = getRealPath(fpath);		// 絶対パスに変換
        try (OutputStream os = new FileOutputStream(path);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os, "Shift_Jis"));) {
            out.write(text);
        } catch (IOException e) {
            log.severe("★ファイルを出力できない:" + fpath);
        }
    }

    /**
     * csvファイルの全内容を読みだして文字列として返す
     *
     * @param fpath csvファイルのパス<br/>
     * resourcesからの相対パスで指定する（/resources/～）
     * @return	ArrayList<String[]>
     */
    public static ArrayList<String[]> getCsv(String fpath) {
        String path = getRealPath(fpath);
        String[] array;
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        try (InputStream is = new FileInputStream(path);
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "Shift_Jis"));) {
            String line;
            while ((line = in.readLine()) != null) {
                if (!line.isEmpty() && !line.substring(0, 1).equals("#")) {// ヘッダー以外を
                    array = line.split(",");
                    arrayList.add(array);
                }
            }
        } catch (IOException e) {
            log.severe("★ファイルが見つからない:" + fpath);
        }
        return arrayList;
    }

    public static TableDto getTableInfoFromXML(String className) {
        TableDto tableDto = new TableDto();
        String fileName = className.toLowerCase() + ".xml";
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File("resources/tableinfo/" + fileName));

            Element rootNode = document.getDocumentElement();
            NodeList rootChildren = rootNode.getChildNodes();

            for (int i = 0; i < rootChildren.getLength(); i++) {
                Node node = rootChildren.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String elementNodeName = element.getNodeName();
                    if (elementNodeName.equals("name")) {
                        tableDto.setTable(element.getTextContent());
                    } else if (elementNodeName.equals("columns")) {
                        List<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();
                        NodeList columnsChildren = node.getChildNodes();
                        for (int k = 0; k < columnsChildren.getLength(); k++) {
                            Node columnsnode = columnsChildren.item(k);
                            if (columnsnode.getNodeType() == Node.ELEMENT_NODE) {
                                Element columnselement = (Element) columnsnode;
                                if (columnselement.getNodeName().equals("column")) {
                                    ColumnInfo columnInfo = new ColumnInfo();
                                    columnInfo.setColumnName(columnselement.getTextContent());
                                    columnInfo.setJdbcType(columnselement.getAttribute("jdbcType"));
                                    columnInfoList.add(columnInfo);
                                }
                            }
                        }
                        tableDto.setColumnInfoList(columnInfoList);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tableDto;
    }

    /**
     * I/Oで使用する絶対パスを求める
     *
     * @param path	アプリケーションルートのリソースからの相対パス（/resources/～）
     * @return	絶対パス
     */
    public static String getRealPath(String path) {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        return ctx.getRealPath(path);
    }
}
