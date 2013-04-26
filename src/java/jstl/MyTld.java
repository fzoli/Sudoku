package jstl;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class MyTld extends SimpleTagSupport {
    private String src;

    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        try {
            JspFragment f = getJspBody();
            if (f != null) {
                f.invoke(out);
            }
            out.print("<script type=\"text/javascript\" src=\""+src+"\"></script>");
        }
        catch (java.io.IOException ex) {
            throw new JspException("Error in MyTld tag", ex);
        }
    }

    public void setSrc(String src) {
        this.src = src;
    }
    
}