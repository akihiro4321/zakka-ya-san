package beans;


import java.io.Serializable;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


@Named
@RequestScoped
public class LoginBean extends SuperBb implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	transient Logger log;

	private String username;
	private String password;

	// ログイン処理

	public String login() {
		HttpServletRequest request = getRequest();
		try {
			log.info("■" + username + " / " + password);
			request.login(username, password);
		} catch (ServletException ex) {
			facesErrorMsg("ログインできません");
			return null;
		}

		return "/index.xhtml?faces-redirect=true";
	}

	//　ログアウト処理

	public String logout() {
		try {
			getServlet().invalidateSession();
			HttpServletRequest request = getRequest();
			request.logout();
		} catch (ServletException ex) {}
		return "/index.xhtml?faces-redirect=true ";
	}

    //　アクセサメソッド
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
