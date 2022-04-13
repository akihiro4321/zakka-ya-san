/*
 * 注文確認メールのメールテキストを作成して返す
*/
package beans;

import entity.OrderLine;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class MakeText implements Serializable {

	DecimalFormat		fm = new DecimalFormat("###,###,###");
	SimpleDateFormat	fd = new SimpleDateFormat("y年M月d日");
	
	public	String getText(String name, String msg, List<OrderLine> cart){

		String CR = System.getProperty("line.separator");
		StringBuilder buf = new StringBuilder();
		buf.append(name).append("様").append(CR);
		buf.append("以下の注文を承りました。").append(CR).append("日付:");
		buf.append(ymd(new Date())).append(CR).append(CR);

		int total = 0;
		for (OrderLine o : cart) {
			int q = o.getQuantity();
			int p = o.getProduct().getPrice();
			total += q * p;
			buf.append(o.getProduct().getName()).append("\n");
			buf.append("\t").append("数量：").append(q).append("\t");
			buf.append("\t").append("単価：").append(gaku(p)).append(CR);
			buf.append("\t").append("小計：").append(gaku(p*q)).append("円").append(CR);
		}
		buf.append(CR).append("合計 ").append(gaku(total)).append("円").append(CR);
		buf.append(CR).append("連絡事項 ").append(CR);
		buf.append(msg).append(CR);
		
		return buf.toString();
	}
	public String gaku(Integer n){
		return	fm.format(n);
		
	}
	public	String ymd(Date d){
		return	(fd.format(d));
	}
}
