package beans;
import net.tkxtools.MailSender;
import db.*;
import entity.*;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import util.Pagenation;
/*
 * ・フィールド変数とアクセサメソッドを持つ
 * ・初期化処理を行う
 * ・ページング処理を行う
 * ・ユーティリティメソッドもスーパークラスに用意する
 */
public class SuperBb implements Serializable {
	/* *****（変数）********************************************/
	protected Map<String, Integer> priceItems;	// 並べ替え選択肢	
	protected Map<String, AppKind> kindItems;	// 種類選択肢
	protected AppKind kindItem = AppKind.NONE;	// 種類選択結果
	protected Integer priceItem = 1;			// 並べ替え選択結果

	protected List<OrderLine> cart;				// カート
	protected Product sel;						// 詳細画面に表示する商品						

	protected String c_name;					// 顧客氏名
	protected String c_address;					// 顧客住所
	protected String c_mail;					// 顧客メール
	protected String c_msg;						// 顧客メッセージ
	/* *****（データベース処理）*******************************/
	@EJB
	ProductDb productDb;		// 商品データベース
	@EJB
	CustomerDb customerDb;		// 顧客データベース
	@EJB
	AppOrderDb appOrderDb;		// 注文履歴データべース
	@EJB
	OrderLineDb orderLineDb;	// 注文明細データベース
	/* *****（ユーティリティのインジェクト）********************/
	@EJB
	ProductManager pm;					// 商品マネージャー
	@Inject
	protected transient Logger log;		// ロガー
	@EJB
	protected MailSender sender;		// 電子メールユーティリティ
	@Inject
	protected Pagenation productPage;	// ページングマネージャー
	@EJB
	OrderManager orderManager;			// 顧客の購入履歴の検索
	@Inject
	protected MakeText text;
	/* *****（初期化）******************************************/
	@PostConstruct
	public void init() {
		cart = new ArrayList<>();					// カート

		priceItems = new LinkedHashMap<>();			// 並べ替え選択肢
		priceItems.put("なし", 1);
		priceItems.put("安い順", 2);
		priceItems.put("高い順", 3);

		kindItems = new LinkedHashMap<>();			// 種類選択肢
		kindItems.put("全商品", AppKind.NONE);
		kindItems.put("新入荷品", AppKind.KIND1);
		kindItems.put("おすすめ品", AppKind.KIND2);
		kindItems.put("特価品", AppKind.KIND3);

		productPage.setup(productDb.dataCount(), 5);// ページングマネージャー
	}
	/* *****（画像表示処理＜小画像＞）************************/
	public StreamedContent getPicS() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			ExternalContext sv = context.getExternalContext();
			Map<String, String> map = sv.getRequestParameterMap();
			String key = map.get("productId");
			Product e = (Product) (productDb.find(Long.valueOf(key)));
			ByteArrayInputStream in = new ByteArrayInputStream(e.getPic_S());
			DefaultStreamedContent ds = new DefaultStreamedContent(in);
			return ds;
		}
	}
	/* *****（画像表示処理＜大画像＞）************************/
	public StreamedContent getPicL() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			ExternalContext sv = context.getExternalContext();
			Map<String, String> map = sv.getRequestParameterMap();
			String key = map.get("productId");
			Product e = (Product) (productDb.find(Long.valueOf(key)));
			ByteArrayInputStream in = new ByteArrayInputStream(e.getPic_L());
			DefaultStreamedContent ds = new DefaultStreamedContent(in);
			return ds;
		}
	}
	/* *****（メッセージを作成しキューに入れる）**************/
	public void facesMessage(String s) {
		FacesMessage msg = new FacesMessage(s);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	/* *****（エラーメッセージを作成しキューに入れる）**************/
	public void facesErrorMsg(String s) {
		FacesMessage msg = new FacesMessage(s);
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	/* *****（メッセージを作成しキューに入れる）*****************
		FacesMessage.SEVERITY_FATAL		致命的エラー(4)
		FacesMessage.SEVERITY_ERROR		エラー(3)
		FacesMessage.SEVERITY_WARN		警告(2)
		FacesMessage.SEVERITY_WARN		情報(1)   
	*************************************************************/
	public void facesMessage(FacesMessage.Severity severity, String s) {
		FacesMessage msg = new FacesMessage(s);
		msg.setSeverity(severity);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	/* *****（サーブレット環境を取得する）**************/
	public ExternalContext getServlet() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	/* *****（リクエストオブジェクトを取得する）**************/
	public HttpServletRequest getRequest() {
		return (HttpServletRequest) getServlet().getRequest();
	}
	/* *****（ログインしているユーザーのIDを返す */
	public String getUserId() {
		return getRequest().getRemoteUser();
	}
	/* *****（セッターとゲッター）***************************/
	public List<OrderLine> getCart() {
		return cart;
	}
	public void setCart(List<OrderLine> cart) {
		this.cart = cart;
	}
	public Product getSel() {
		return sel;
	}
	public void setSel(Product sel) {
		this.sel = sel;
	}
	public Integer getPriceItem() {
		return priceItem;
	}
	public void setPriceItem(Integer priceItem) {
		this.priceItem = priceItem;
	}
	public Map<String, Integer> getPriceItems() {
		return priceItems;
	}
	public AppKind getKindItem() {
		return kindItem;
	}
	public void setKindItem(AppKind kindItem) {
		this.kindItem = kindItem;
	}
	public Map<String, AppKind> getKindItems() {
		return kindItems;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public String getC_address() {
		return c_address;
	}
	public void setC_address(String c_address) {
		this.c_address = c_address;
	}
	public String getC_mail() {
		return c_mail;
	}
	public void setC_mail(String c_mail) {
		this.c_mail = c_mail;
	}
	public String getC_msg() {
		return c_msg;
	}
	public void setC_msg(String c_msg) {
		this.c_msg = c_msg;
	}
	public Pagenation getProductPage() {
		return productPage;
	}
	public void setProductPage(Pagenation productPage) {
		this.productPage = productPage;
	}
}
