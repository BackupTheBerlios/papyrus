/*
 * JSP generated by Resin 2.1.12 (built Tue Dec  9 14:58:25 PST 2003)
 */

package _management;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.eurocash.data.management.customer.CustomerBean;

public class _customerList__jsp extends com.caucho.jsp.JavaPage{
  private boolean _caucho_isDead;
  
  public void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response)
    throws java.io.IOException, javax.servlet.ServletException
  {
    com.caucho.jsp.QPageContext pageContext = (com.caucho.jsp.QPageContext) com.caucho.jsp.QJspFactory.create().getPageContext(this, request, response, null, true, 8192, true);
    javax.servlet.jsp.JspWriter out = (javax.servlet.jsp.JspWriter) pageContext.getOut();
    javax.servlet.ServletConfig config = getServletConfig();
    javax.servlet.Servlet page = this;
    javax.servlet.http.HttpSession session = pageContext.getSession();
    javax.servlet.ServletContext application = pageContext.getServletContext();
    response.setContentType("text/html;charset=iso-8859-1");
    org.apache.taglibs.standard.tag.el.core.ImportTag _jsp_tag0 = null;
    org.apache.taglibs.standard.tag.el.core.ParamTag _jsp_tag20 = null;
    org.apache.taglibs.standard.tag.el.core.IfTag _jsp_tag1 = null;
    org.apache.taglibs.standard.tag.el.core.OutTag _jsp_tag2 = null;
    org.apache.taglibs.standard.tag.el.core.ForEachTag _jsp_tag3 = null;
    org.apache.taglibs.standard.tag.el.core.OutTag _jsp_tag4 = null;
    org.apache.taglibs.standard.tag.el.core.IfTag _jsp_tag5 = null;
    org.apache.taglibs.standard.tag.el.core.OutTag _jsp_tag6 = null;
    org.apache.taglibs.standard.tag.el.core.OutTag _jsp_tag7 = null;
    org.apache.taglibs.standard.tag.common.core.ChooseTag _jsp_tag8 = null;
    org.apache.taglibs.standard.tag.el.core.WhenTag _jsp_tag9 = null;
    org.apache.taglibs.standard.tag.common.core.OtherwiseTag _jsp_tag10 = null;
    org.apache.taglibs.standard.tag.el.core.ForEachTag _jsp_tag11 = null;
    org.apache.taglibs.standard.tag.el.core.OutTag _jsp_tag12 = null;
    org.apache.taglibs.standard.tag.el.core.IfTag _jsp_tag13 = null;
    org.apache.taglibs.standard.tag.el.core.OutTag _jsp_tag14 = null;
    org.apache.taglibs.standard.tag.common.core.ChooseTag _jsp_tag15 = null;
    org.apache.taglibs.standard.tag.el.core.WhenTag _jsp_tag16 = null;
    org.apache.taglibs.standard.tag.el.core.OutTag _jsp_tag17 = null;
    org.apache.taglibs.standard.tag.common.core.OtherwiseTag _jsp_tag18 = null;
    org.apache.taglibs.standard.tag.el.core.OutTag _jsp_tag19 = null;
    try {
      pageContext.write(_jsp_string0, 0, _jsp_string0.length);
      pageContext.write(_jsp_string0, 0, _jsp_string0.length);
      com.eurocash.data.ItemListBean customerListBean;
      synchronized (session) {
        customerListBean = (com.eurocash.data.ItemListBean) session.getAttribute("customerListBean");
        if (customerListBean == null) {
          customerListBean = new com.eurocash.data.ItemListBean();
          session.setAttribute("customerListBean", customerListBean);
        }
      }
      pageContext.write(_jsp_string1, 0, _jsp_string1.length);
      com.eurocash.data.mapping.form.FormMappingObject customerListForm;
      synchronized (session) {
        customerListForm = (com.eurocash.data.mapping.form.FormMappingObject) session.getAttribute("customerListForm");
        if (customerListForm == null) {
          throw new java.lang.InstantiationException("`com.eurocash.data.mapping.form.FormMappingObject' has no public zero-arg constructor");
        }
      }
      pageContext.write(_jsp_string1, 0, _jsp_string1.length);
      com.eurocash.data.administration.agency.AgenciesBean agenciesBean;
      synchronized (application) {
        agenciesBean = (com.eurocash.data.administration.agency.AgenciesBean) application.getAttribute("agenciesBean");
        if (agenciesBean == null) {
          agenciesBean = new com.eurocash.data.administration.agency.AgenciesBean();
          application.setAttribute("agenciesBean", agenciesBean);
        }
      }
      pageContext.write(_jsp_string0, 0, _jsp_string0.length);
       
	pageContext.setAttribute("STE_CIVILITY", new Integer(CustomerBean.STE_CIVILITY)); 
	pageContext.setAttribute("SARL_CIVILITY", new Integer(CustomerBean.SARL_CIVILITY));
	pageContext.setAttribute("SCI_CIVILITY", new Integer(CustomerBean.SCI_CIVILITY));

      pageContext.write(_jsp_string2, 0, _jsp_string2.length);
      if (_jsp_tag0 == null) {
        _jsp_tag0 = new org.apache.taglibs.standard.tag.el.core.ImportTag();
        _jsp_tag0.setPageContext(pageContext);
        _jsp_tag0.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_tag0.setUrl("/include/jsp/menu.jsp");
      javax.servlet.jsp.JspWriter _jsp_writer1 = out;
      try {
        _jsp_tag0.doStartTag();
        _jsp_tag0.setBodyContent(null);
        int _jsp_endTagVar3 = _jsp_tag0.doEndTag();
        if (_jsp_endTagVar3 == javax.servlet.jsp.tagext.Tag.SKIP_PAGE)
          return;
        } catch (Throwable t) {
        pageContext.setWriter(_jsp_writer1);
        out = _jsp_writer1;
          _jsp_tag0.doCatch(t);
        } finally {
          _jsp_tag0.doFinally();
        }
        pageContext.write(_jsp_string3, 0, _jsp_string3.length);
        _jsp_tag0.setUrl("/include/jsp/header.jsp");
        javax.servlet.jsp.JspWriter _jsp_writer5 = out;
        try {
          _jsp_tag0.doStartTag();
          _jsp_tag0.setBodyContent(null);
          int _jsp_endTagVar7 = _jsp_tag0.doEndTag();
          if (_jsp_endTagVar7 == javax.servlet.jsp.tagext.Tag.SKIP_PAGE)
            return;
          } catch (Throwable t) {
          pageContext.setWriter(_jsp_writer5);
          out = _jsp_writer5;
            _jsp_tag0.doCatch(t);
          } finally {
            _jsp_tag0.doFinally();
          }
          pageContext.write(_jsp_string4, 0, _jsp_string4.length);
          if (_caucho_expr_0.evalBoolean(pageContext)) {
            _caucho_expr_1.print(pageContext.getWriteStream(), pageContext, true);
          }
          pageContext.write(_jsp_string5, 0, _jsp_string5.length);
          if (_jsp_tag3 == null) {
            _jsp_tag3 = new org.apache.taglibs.standard.tag.el.core.ForEachTag();
            _jsp_tag3.setPageContext(pageContext);
            _jsp_tag3.setParent((javax.servlet.jsp.tagext.Tag) null);
            _jsp_tag3.setVarStatus("status");
            _jsp_tag3.setItems("${agenciesBean.agenciesList}");
            _jsp_tag3.setVar("agency");
          }

          javax.servlet.jsp.JspWriter _jsp_writer9 = out;
          try {
            int _jspEval10 = _jsp_tag3.doStartTag();
            if (_jspEval10 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
              do {
                pageContext.write(_jsp_string6, 0, _jsp_string6.length);
                _caucho_expr_2.print(pageContext.getWriteStream(), pageContext, true);
                pageContext.write(_jsp_string7, 0, _jsp_string7.length);
                if (_caucho_expr_3.evalBoolean(pageContext)) {
                  _caucho_expr_1.print(pageContext.getWriteStream(), pageContext, true);
                }
                pageContext.write(_jsp_string8, 0, _jsp_string8.length);
                _caucho_expr_4.print(pageContext.getWriteStream(), pageContext, true);
                pageContext.write(_jsp_string9, 0, _jsp_string9.length);
              } while (_jsp_tag3.doAfterBody() == javax.servlet.jsp.tagext.IterationTag.EVAL_BODY_AGAIN);
            }
            } catch (Throwable t) {
            pageContext.setWriter(_jsp_writer9);
            out = _jsp_writer9;
              _jsp_tag3.doCatch(t);
            } finally {
              _jsp_tag3.doFinally();
            }
            pageContext.write(_jsp_string10, 0, _jsp_string10.length);
            _caucho_expr_5.print(pageContext.getWriteStream(), pageContext, true);
            pageContext.write(_jsp_string11, 0, _jsp_string11.length);
            _caucho_expr_6.print(pageContext.getWriteStream(), pageContext, true);
            pageContext.write(_jsp_string12, 0, _jsp_string12.length);
            _caucho_expr_7.print(pageContext.getWriteStream(), pageContext, true);
            pageContext.write(_jsp_string13, 0, _jsp_string13.length);
            _caucho_expr_8.print(pageContext.getWriteStream(), pageContext, true);
            pageContext.write(_jsp_string14, 0, _jsp_string14.length);
            _caucho_expr_9.print(pageContext.getWriteStream(), pageContext, true);
            pageContext.write(_jsp_string15, 0, _jsp_string15.length);
            _caucho_expr_10.print(pageContext.getWriteStream(), pageContext, true);
            pageContext.write(_jsp_string16, 0, _jsp_string16.length);
            _caucho_expr_11.print(pageContext.getWriteStream(), pageContext, true);
            pageContext.write(_jsp_string17, 0, _jsp_string17.length);
            if (_caucho_expr_12.evalBoolean(pageContext)) {
              pageContext.write(_jsp_string18, 0, _jsp_string18.length);
            }
            else {
              pageContext.write(_jsp_string19, 0, _jsp_string19.length);
              if (_jsp_tag11 == null) {
                _jsp_tag11 = new org.apache.taglibs.standard.tag.el.core.ForEachTag();
                _jsp_tag11.setPageContext(pageContext);
                _jsp_tag11.setParent((javax.servlet.jsp.tagext.Tag) null);
                _jsp_tag11.setVarStatus("status");
                _jsp_tag11.setItems("${customerListBean.list}");
                _jsp_tag11.setVar("customer");
              }

              javax.servlet.jsp.JspWriter _jsp_writer12 = out;
              try {
                int _jspEval13 = _jsp_tag11.doStartTag();
                if (_jspEval13 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                  do {
                    pageContext.write(_jsp_string20, 0, _jsp_string20.length);
                    _caucho_expr_13.print(pageContext.getWriteStream(), pageContext, true);
                    pageContext.write(_jsp_string21, 0, _jsp_string21.length);
                    if (_caucho_expr_14.evalBoolean(pageContext)) {
                      pageContext.write(_jsp_string22, 0, _jsp_string22.length);
                    }
                    pageContext.write(_jsp_string23, 0, _jsp_string23.length);
                    if (_caucho_expr_15.evalBoolean(pageContext)) {
                      pageContext.write(_jsp_string24, 0, _jsp_string24.length);
                    }
                    pageContext.write(_jsp_string25, 0, _jsp_string25.length);
                    _caucho_expr_13.print(pageContext.getWriteStream(), pageContext, true);
                    pageContext.write(_jsp_string26, 0, _jsp_string26.length);
                    _caucho_expr_16.print(pageContext.getWriteStream(), pageContext, true);
                    pageContext.write(_jsp_string27, 0, _jsp_string27.length);
                    if (_caucho_expr_17.print(pageContext.getWriteStream(), pageContext, true)) {
                      com.caucho.el.Expr.toStreamEscaped(pageContext.getWriteStream(), "N/A");
                    }
                    pageContext.write(_jsp_string27, 0, _jsp_string27.length);
                    if (_caucho_expr_18.evalBoolean(pageContext)) {
                      pageContext.write(_jsp_string28, 0, _jsp_string28.length);
                      _caucho_expr_19.print(pageContext.getWriteStream(), pageContext, true);
                      pageContext.write(_jsp_string29, 0, _jsp_string29.length);
                    }
                    else {
                      pageContext.write(_jsp_string28, 0, _jsp_string28.length);
                      _caucho_expr_20.print(pageContext.getWriteStream(), pageContext, true);
                      pageContext.write(_jsp_string30, 0, _jsp_string30.length);
                      _caucho_expr_21.print(pageContext.getWriteStream(), pageContext, true);
                      pageContext.write(_jsp_string31, 0, _jsp_string31.length);
                    }
                    pageContext.write(_jsp_string32, 0, _jsp_string32.length);
                    if (_caucho_expr_22.evalBoolean(pageContext)) {
                      pageContext.write(_jsp_string33, 0, _jsp_string33.length);
                    }
                    else {
                      pageContext.write(_jsp_string34, 0, _jsp_string34.length);
                      _caucho_expr_23.print(pageContext.getWriteStream(), pageContext, true);
                      pageContext.write(_jsp_string34, 0, _jsp_string34.length);
                      _caucho_expr_24.print(pageContext.getWriteStream(), pageContext, true);
                      pageContext.write(_jsp_string34, 0, _jsp_string34.length);
                      _caucho_expr_25.print(pageContext.getWriteStream(), pageContext, true);
                      pageContext.write(_jsp_string35, 0, _jsp_string35.length);
                    }
                    pageContext.write(_jsp_string36, 0, _jsp_string36.length);
                    if (_caucho_expr_26.print(pageContext.getWriteStream(), pageContext, true)) {
                      com.caucho.el.Expr.toStreamEscaped(pageContext.getWriteStream(), "N/A");
                    }
                    pageContext.write(_jsp_string27, 0, _jsp_string27.length);
                    if (_caucho_expr_27.print(pageContext.getWriteStream(), pageContext, true)) {
                      com.caucho.el.Expr.toStreamEscaped(pageContext.getWriteStream(), "N/A");
                    }
                    pageContext.write(_jsp_string37, 0, _jsp_string37.length);
                    _caucho_expr_13.print(pageContext.getWriteStream(), pageContext, true);
                    pageContext.write(_jsp_string38, 0, _jsp_string38.length);
                    _caucho_expr_13.print(pageContext.getWriteStream(), pageContext, true);
                    pageContext.write(_jsp_string39, 0, _jsp_string39.length);
                  } while (_jsp_tag11.doAfterBody() == javax.servlet.jsp.tagext.IterationTag.EVAL_BODY_AGAIN);
                }
                } catch (Throwable t) {
                pageContext.setWriter(_jsp_writer12);
                out = _jsp_writer12;
                  _jsp_tag11.doCatch(t);
                } finally {
                  _jsp_tag11.doFinally();
                }
                pageContext.write(_jsp_string40, 0, _jsp_string40.length);
              }
              pageContext.write(_jsp_string41, 0, _jsp_string41.length);
              com.caucho.jsp.QBodyContent _jsp_endTagHack14 = null;
              _jsp_tag0.setUrl("/include/jsp/multipage.jsp");
              javax.servlet.jsp.JspWriter _jsp_writer15 = out;
              try {
                int _jspEval16 = _jsp_tag0.doStartTag();
                if (_jspEval16 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                  if (_jspEval16 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
                    out = pageContext.pushBody();
                    _jsp_endTagHack14 = (com.caucho.jsp.QBodyContent) out;
                    _jsp_tag0.setBodyContent(_jsp_endTagHack14);
                  }
                  pageContext.write(_jsp_string42, 0, _jsp_string42.length);
                  if (_jsp_tag20 == null) {
                    _jsp_tag20 = new org.apache.taglibs.standard.tag.el.core.ParamTag();
                    _jsp_tag20.setPageContext(pageContext);
                    _jsp_tag20.setParent(_jsp_tag0);
                  }

                  _jsp_tag20.setName("domain");
                  _jsp_tag20.setValue("customer");
                  _jsp_tag20.doStartTag();
                  _jsp_tag20.setBodyContent(null);
                  int _jsp_endTagVar20 = _jsp_tag20.doEndTag();
                  if (_jsp_endTagVar20 == javax.servlet.jsp.tagext.Tag.SKIP_PAGE)
                    return;
                  pageContext.write(_jsp_string42, 0, _jsp_string42.length);
                  _jsp_tag20.setName("listBean");
                  _jsp_tag20.setValue("customerListBean");
                  _jsp_tag20.doStartTag();
                  _jsp_tag20.setBodyContent(null);
                  int _jsp_endTagVar24 = _jsp_tag20.doEndTag();
                  if (_jsp_endTagVar24 == javax.servlet.jsp.tagext.Tag.SKIP_PAGE)
                    return;
                  pageContext.write(_jsp_string43, 0, _jsp_string43.length);
                  if (_jspEval16 == javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
                    out = pageContext.popBody();
                  } else {
                    _jsp_tag0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) null);
                  }
                }
                int _jsp_endTagVar25 = _jsp_tag0.doEndTag();
                if (_jsp_endTagHack14 != null) {
                  pageContext.releaseBody(_jsp_endTagHack14);
                  _jsp_endTagHack14 = null;
                }
                if (_jsp_endTagVar25 == javax.servlet.jsp.tagext.Tag.SKIP_PAGE)
                  return;
                } catch (Throwable t) {
                pageContext.setWriter(_jsp_writer15);
                out = _jsp_writer15;
                  _jsp_tag0.doCatch(t);
                } finally {
                  _jsp_tag0.doFinally();
                }
                pageContext.write(_jsp_string44, 0, _jsp_string44.length);
              } catch (java.lang.Throwable _jsp_e) {
                pageContext.handlePageException(_jsp_e);
              } finally {
                if (_jsp_tag0 != null)
                  _jsp_tag0.release();
                if (_jsp_tag1 != null)
                  _jsp_tag1.release();
                if (_jsp_tag2 != null)
                  _jsp_tag2.release();
                if (_jsp_tag3 != null)
                  _jsp_tag3.release();
                if (_jsp_tag4 != null)
                  _jsp_tag4.release();
                if (_jsp_tag5 != null)
                  _jsp_tag5.release();
                if (_jsp_tag6 != null)
                  _jsp_tag6.release();
                if (_jsp_tag7 != null)
                  _jsp_tag7.release();
                if (_jsp_tag8 != null)
                  _jsp_tag8.release();
                if (_jsp_tag9 != null)
                  _jsp_tag9.release();
                if (_jsp_tag10 != null)
                  _jsp_tag10.release();
                if (_jsp_tag11 != null)
                  _jsp_tag11.release();
                if (_jsp_tag12 != null)
                  _jsp_tag12.release();
                if (_jsp_tag13 != null)
                  _jsp_tag13.release();
                if (_jsp_tag14 != null)
                  _jsp_tag14.release();
                if (_jsp_tag15 != null)
                  _jsp_tag15.release();
                if (_jsp_tag16 != null)
                  _jsp_tag16.release();
                if (_jsp_tag17 != null)
                  _jsp_tag17.release();
                if (_jsp_tag18 != null)
                  _jsp_tag18.release();
                if (_jsp_tag19 != null)
                  _jsp_tag19.release();
                if (_jsp_tag20 != null)
                  _jsp_tag20.release();
                JspFactory.getDefaultFactory().releasePageContext(pageContext);
              }
            }

            private com.caucho.java.LineMap _caucho_line_map;
            private java.util.ArrayList _caucho_depends = new java.util.ArrayList();

            public boolean _caucho_isModified()
            {
              if (_caucho_isDead)
                return true;
              if (com.caucho.util.CauchoSystem.getVersionId() != -2089842223)
                return true;
              for (int i = _caucho_depends.size() - 1; i >= 0; i--) {
                com.caucho.vfs.Depend depend;
                depend = (com.caucho.vfs.Depend) _caucho_depends.get(i);
                if (depend.isModified())
                  return true;
              }
              return false;
            }

            public long _caucho_lastModified()
            {
              return 0;
            }

            public com.caucho.java.LineMap _caucho_getLineMap()
            {
              return _caucho_line_map;
            }

            public void destroy()
            {
                _caucho_isDead = true;
                super.destroy();
            }

            public void init(com.caucho.java.LineMap lineMap,
                             com.caucho.vfs.Path appDir)
              throws javax.servlet.ServletException
            {
              com.caucho.vfs.Path resinHome = com.caucho.util.CauchoSystem.getResinHome();
              com.caucho.vfs.MergePath mergePath = new com.caucho.vfs.MergePath();
              mergePath.addMergePath(appDir);
              mergePath.addMergePath(resinHome);
              mergePath.addClassPath(getClass().getClassLoader());
              _caucho_line_map = new com.caucho.java.LineMap("_customerList__jsp.java", "/management/customerList.jsp");
              _caucho_line_map.add(1, 1);
              _caucho_line_map.add(1, 48);
              _caucho_line_map.add(3, 49);
              _caucho_line_map.add(5, 50);
              _caucho_line_map.add(6, 59);
              _caucho_line_map.add(7, 67);
              _caucho_line_map.add(9, 76);
              _caucho_line_map.add(27, 82);
              _caucho_line_map.add(27, 93);
              _caucho_line_map.add(34, 104);
              _caucho_line_map.add(34, 109);
              _caucho_line_map.add(50, 120);
              _caucho_line_map.add(50, 121);
              _caucho_line_map.add(51, 124);
              _caucho_line_map.add(51, 138);
              _caucho_line_map.add(52, 141);
              _caucho_line_map.add(52, 142);
              _caucho_line_map.add(52, 145);
              _caucho_line_map.add(56, 157);
              _caucho_line_map.add(57, 159);
              _caucho_line_map.add(58, 161);
              _caucho_line_map.add(59, 163);
              _caucho_line_map.add(60, 165);
              _caucho_line_map.add(61, 167);
              _caucho_line_map.add(62, 169);
              _caucho_line_map.add(85, 171);
              _caucho_line_map.add(92, 176);
              _caucho_line_map.add(92, 190);
              _caucho_line_map.add(94, 193);
              _caucho_line_map.add(97, 197);
              _caucho_line_map.add(100, 201);
              _caucho_line_map.add(101, 203);
              _caucho_line_map.add(102, 205);
              _caucho_line_map.add(103, 209);
              _caucho_line_map.add(108, 216);
              _caucho_line_map.add(108, 218);
              _caucho_line_map.add(111, 222);
              _caucho_line_map.add(114, 227);
              _caucho_line_map.add(115, 229);
              _caucho_line_map.add(116, 231);
              _caucho_line_map.add(121, 239);
              _caucho_line_map.add(123, 243);
              _caucho_line_map.add(135, 259);
              _caucho_line_map.add(135, 270);
              _caucho_line_map.add(136, 281);
              _caucho_line_map.add(137, 285);
              _caucho_line_map.add(137, 289);
              com.caucho.vfs.Depend depend;
              depend = new com.caucho.vfs.Depend(appDir.lookup("management/customerList.jsp"), 1087659731000L, 6987L);
              _caucho_depends.add(depend);
            }

            protected void _caucho_clearDepends()
            {
              _caucho_depends.clear();
            }
            private static com.caucho.el.Expr _caucho_expr_0 =
              new com.caucho.el.EqExpr(6, new com.caucho.el.LongLiteral(0L), new com.caucho.el.ArrayExpr(new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customerListForm"), new com.caucho.el.StringLiteral("htmlData")), new com.caucho.el.StringLiteral("agencyId")));
            private static com.caucho.el.Expr _caucho_expr_1 =
              new com.caucho.el.StringLiteral("SELECTED");
            private static com.caucho.el.Expr _caucho_expr_2 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("agency"), new com.caucho.el.StringLiteral("id"));
            private static com.caucho.el.Expr _caucho_expr_3 =
              new com.caucho.el.EqExpr(6, new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("agency"), new com.caucho.el.StringLiteral("id")), new com.caucho.el.ArrayExpr(new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customerListForm"), new com.caucho.el.StringLiteral("htmlData")), new com.caucho.el.StringLiteral("agencyId")));
            private static com.caucho.el.Expr _caucho_expr_4 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("agency"), new com.caucho.el.StringLiteral("company"));
            private static com.caucho.el.Expr _caucho_expr_5 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customerListForm"), new com.caucho.el.StringLiteral("htmlData")), new com.caucho.el.StringLiteral("customerCode"));
            private static com.caucho.el.Expr _caucho_expr_6 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customerListForm"), new com.caucho.el.StringLiteral("htmlData")), new com.caucho.el.StringLiteral("name"));
            private static com.caucho.el.Expr _caucho_expr_7 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customerListForm"), new com.caucho.el.StringLiteral("htmlData")), new com.caucho.el.StringLiteral("address"));
            private static com.caucho.el.Expr _caucho_expr_8 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customerListForm"), new com.caucho.el.StringLiteral("htmlData")), new com.caucho.el.StringLiteral("city"));
            private static com.caucho.el.Expr _caucho_expr_9 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customerListForm"), new com.caucho.el.StringLiteral("htmlData")), new com.caucho.el.StringLiteral("postalCode"));
            private static com.caucho.el.Expr _caucho_expr_10 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customerListForm"), new com.caucho.el.StringLiteral("htmlData")), new com.caucho.el.StringLiteral("phone"));
            private static com.caucho.el.Expr _caucho_expr_11 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customerListForm"), new com.caucho.el.StringLiteral("htmlData")), new com.caucho.el.StringLiteral("cellPhone"));
            private static com.caucho.el.Expr _caucho_expr_12 =
              new com.caucho.el.UnaryExpr(16, new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customerListBean"), new com.caucho.el.StringLiteral("list")));
            private static com.caucho.el.Expr _caucho_expr_13 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customer"), new com.caucho.el.StringLiteral("id"));
            private static com.caucho.el.Expr _caucho_expr_14 =
              new com.caucho.el.EqExpr(6, new com.caucho.el.BinaryExpr(5, new com.caucho.el.BinaryExpr(1, new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("status"), new com.caucho.el.StringLiteral("index")), new com.caucho.el.LongLiteral(1L)), new com.caucho.el.LongLiteral(2L)), new com.caucho.el.LongLiteral(0L));
            private static com.caucho.el.Expr _caucho_expr_15 =
              new com.caucho.el.EqExpr(7, new com.caucho.el.BinaryExpr(5, new com.caucho.el.BinaryExpr(1, new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("status"), new com.caucho.el.StringLiteral("index")), new com.caucho.el.LongLiteral(1L)), new com.caucho.el.LongLiteral(2L)), new com.caucho.el.LongLiteral(0L));
            private static com.caucho.el.Expr _caucho_expr_16 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.ArrayExpr(new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("agenciesBean"), new com.caucho.el.StringLiteral("agenciesMap")), new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customer"), new com.caucho.el.StringLiteral("agencyId"))), new com.caucho.el.StringLiteral("company"));
            private static com.caucho.el.Expr _caucho_expr_17 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customer"), new com.caucho.el.StringLiteral("code"));
            private static com.caucho.el.Expr _caucho_expr_18 =
              new com.caucho.el.BooleanExpr(13, new com.caucho.el.BooleanExpr(13, new com.caucho.el.EqExpr(6, new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customer"), new com.caucho.el.StringLiteral("civilityId")), new com.caucho.el.IdExpr("STE_CIVILITY")), new com.caucho.el.EqExpr(6, new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customer"), new com.caucho.el.StringLiteral("civilityId")), new com.caucho.el.IdExpr("SCI_CIVILITY"))), new com.caucho.el.EqExpr(6, new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customer"), new com.caucho.el.StringLiteral("civilityId")), new com.caucho.el.IdExpr("SARL_CIVILITY")));
            private static com.caucho.el.Expr _caucho_expr_19 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customer"), new com.caucho.el.StringLiteral("company"));
            private static com.caucho.el.Expr _caucho_expr_20 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customer"), new com.caucho.el.StringLiteral("lastName"));
            private static com.caucho.el.Expr _caucho_expr_21 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customer"), new com.caucho.el.StringLiteral("firstName"));
            private static com.caucho.el.Expr _caucho_expr_22 =
              new com.caucho.el.BooleanExpr(12, new com.caucho.el.BooleanExpr(12, new com.caucho.el.EqExpr(6, new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customer"), new com.caucho.el.StringLiteral("address")), new com.caucho.el.NullLiteral()), new com.caucho.el.EqExpr(6, new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customer"), new com.caucho.el.StringLiteral("postalCode")), new com.caucho.el.NullLiteral())), new com.caucho.el.EqExpr(6, new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customer"), new com.caucho.el.StringLiteral("city")), new com.caucho.el.NullLiteral()));
            private static com.caucho.el.Expr _caucho_expr_23 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customer"), new com.caucho.el.StringLiteral("address"));
            private static com.caucho.el.Expr _caucho_expr_24 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customer"), new com.caucho.el.StringLiteral("postalCode"));
            private static com.caucho.el.Expr _caucho_expr_25 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customer"), new com.caucho.el.StringLiteral("city"));
            private static com.caucho.el.Expr _caucho_expr_26 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customer"), new com.caucho.el.StringLiteral("phone"));
            private static com.caucho.el.Expr _caucho_expr_27 =
              new com.caucho.el.ArrayExpr(new com.caucho.el.IdExpr("customer"), new com.caucho.el.StringLiteral("cellPhone"));

            private static byte []_jsp_string1;
            private static byte []_jsp_string28;
            private static byte []_jsp_string39;
            private static byte []_jsp_string41;
            private static byte []_jsp_string32;
            private static byte []_jsp_string34;
            private static byte []_jsp_string20;
            private static byte []_jsp_string9;
            private static byte []_jsp_string10;
            private static byte []_jsp_string44;
            private static byte []_jsp_string42;
            private static byte []_jsp_string17;
            private static byte []_jsp_string43;
            private static byte []_jsp_string38;
            private static byte []_jsp_string35;
            private static byte []_jsp_string24;
            private static byte []_jsp_string25;
            private static byte []_jsp_string12;
            private static byte []_jsp_string7;
            private static byte []_jsp_string8;
            private static byte []_jsp_string0;
            private static byte []_jsp_string33;
            private static byte []_jsp_string37;
            private static byte []_jsp_string40;
            private static byte []_jsp_string2;
            private static byte []_jsp_string22;
            private static byte []_jsp_string13;
            private static byte []_jsp_string19;
            private static byte []_jsp_string15;
            private static byte []_jsp_string27;
            private static byte []_jsp_string14;
            private static byte []_jsp_string4;
            private static byte []_jsp_string3;
            private static byte []_jsp_string31;
            private static byte []_jsp_string29;
            private static byte []_jsp_string30;
            private static byte []_jsp_string6;
            private static byte []_jsp_string36;
            private static byte []_jsp_string5;
            private static byte []_jsp_string11;
            private static byte []_jsp_string16;
            private static byte []_jsp_string26;
            private static byte []_jsp_string18;
            private static byte []_jsp_string21;
            private static byte []_jsp_string23;
            static {
              _jsp_string1 = "\n".getBytes();
              _jsp_string28 = "\n								".getBytes();
              _jsp_string39 = "'; document.listForm.action.value='delete'; document.listForm.subAction.value='one'; document.listForm.submit();\"/>\n					</td>\n				</tr>\n				".getBytes();
              _jsp_string41 = "\n			</table>\n		</div>\n		\n		<!-- multipage navigation -->\n		".getBytes();
              _jsp_string32 = "\n					<td>".getBytes();
              _jsp_string34 = "\n       							".getBytes();
              _jsp_string20 = "\n				<!-- customer id ".getBytes();
              _jsp_string9 = "</option>\n					".getBytes();
              _jsp_string10 = "\n				</select>\n			</p>\n			<p class=\"boxSearch\">Code client:&nbsp;<input type=\"text\" name=\"customerCode\" length=\"30\" value=\"".getBytes();
              _jsp_string44 = "\n		\n		</form>\n	<body>\n	\n</html>".getBytes();
              _jsp_string42 = "\n			".getBytes();
              _jsp_string17 = "\"></p>\n		</div>\n		\n		\n		<div class=\"boxTitle\">&nbsp;&nbsp;Actions:</div>\n		<div class=\"boxCol\">\n			<p class=\"boxAction\"><a class=\"classicButtonBlue\" onclick=\"document.listForm.action.value='list'; document.listForm.subAction.value='init'; document.listForm.submit();\">&nbsp;[ rechercher ]&nbsp;</a></p>\n			<p class=\"boxAction\"><a class=\"classicButtonBlue\" onclick=\"document.listForm.action.value='add'; document.listForm.subAction.value='init'; document.listForm.submit();\">&nbsp;[ ajouter ]&nbsp;</a></p>\n			<p class=\"boxAction\"><a class=\"classicButtonBlue\" onclick=\"\">&nbsp;[ supprimer ]&nbsp;</a></p>\n		</div>\n		\n		<div class=\"boxResults\">\n			<table border=\"0\" width=\"100%\" cellpadding=\"0px\" cellspacing=\"0px\"> \n				<tr>\n					<th width=\"2%\">&nbsp;</th>\n					<th width=\"13%\">Agence</th>\n					<th width=\"6%\">Code</th>\n					<th width=\"25%\">Nom</th>\n					<th width=\"30%\">Adresse</th>\n					<th width=\"8%\">T\u00e9l\u00e9phone</th>\n					<th width=\"8%\">Portable</th>\n					<th width=\"8%\">Actions</th>\n				</tr>\n".getBytes();
              _jsp_string43 = "\n		".getBytes();
              _jsp_string38 = "'; document.listForm.action.value='update'; document.listForm.subAction.value='init'; document.listForm.submit();\"/>&nbsp;&nbsp;\n						<img src=\"/include/pictures/glass.png\" alt=\"Voir d\u00e9tail\">&nbsp;&nbsp;\n						<img src=\"/include/pictures/trash.png\" alt=\"Supprimer\" onclick=\"document.listForm.id.value='".getBytes();
              _jsp_string35 = "\n           					".getBytes();
              _jsp_string24 = "\n				<tr class=\"result_odd\">\n					".getBytes();
              _jsp_string25 = "\n					<td><input type=\"checkbox\" value=\"".getBytes();
              _jsp_string12 = "\"></p>\n			<p class=\"boxSearch\">Adresse:&nbsp;<input type=\"text\" name=\"address\" length=\"30\" value=\"".getBytes();
              _jsp_string7 = "\" ".getBytes();
              _jsp_string8 = ">".getBytes();
              _jsp_string0 = "\n\n".getBytes();
              _jsp_string33 = "N/A".getBytes();
              _jsp_string37 = "</td>\n					<td>&nbsp;\n						<img src=\"/include/pictures/pencil.png\" alt=\"Modifier\" onclick=\"document.listForm.id.value='".getBytes();
              _jsp_string40 = "\n	".getBytes();
              _jsp_string2 = "\n\n<?xml version=\"1\" encoding=\"ISO-8859-1\"?>\n<!DOCTYPE html PUBLIC \"- //w3c//DTD XHTML 1.0 Strict//EN\" \n\"http://www.w3.org/TR/xhtml11/DTD/xhtml11-strict.dtd\">\n\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\n	<head>\n		<title>Liste des clients</title>\n		<link href=\"/include/style/styleList.css\" rel=\"stylesheet\" type=\"text/css\">\n		<link href=\"/include/style/global.css\" rel=\"stylesheet\" type=\"text/css\">\n		<link href=\"/include/style/header.css\" rel=\"stylesheet\" type=\"text/css\">\n		\n		<!-- create dynamic menu -->\n		".getBytes();
              _jsp_string22 = " \n				<tr class=\"result_par\">\n					".getBytes();
              _jsp_string13 = "\"></p>\n			<p class=\"boxSearch\">Ville:&nbsp;<input type=\"text\" name=\"city\" length=\"30\" value=\"".getBytes();
              _jsp_string19 = "\n				".getBytes();
              _jsp_string15 = "\"></p>\n			<p class=\"boxSearch\">T\u00e9l\u00e9phone:&nbsp;<input type=\"text\" name=\"phone\" length=\"30\" value=\"".getBytes();
              _jsp_string27 = "</td>\n					<td>".getBytes();
              _jsp_string14 = "\"></p>\n			<p class=\"boxSearch\">Code Postal:&nbsp;<input type=\"text\" name=\"postalCode\" length=\"30\" value=\"".getBytes();
              _jsp_string4 = "\n	\n		<form name=\"listForm\" action=\"DomainActionServlet\" method=\"POST\">\n			<input type=\"hidden\" name=\"domain\" value=\"customer\">\n			<input type=\"hidden\" name=\"action\">\n			<input type=\"hidden\" name=\"subAction\">\n			<input type=\"hidden\" name=\"id\">\n	\n		<br>\n				\n		<h1 class=\"title\">Clients&nbsp;&nbsp;:&nbsp;:&nbsp;&nbsp;Liste</h1>\n		\n		<div class=\"boxTitle\">&nbsp;&nbsp;Recherche par: </div>\n		<div class=\"boxCol\">\n			<p class=\"boxSearch\">Agence:&nbsp;\n				<select name=\"agencyId\" >\n					<option value=\"0\" ".getBytes();
              _jsp_string3 = "\n	\n	</head>\n	\n	<body>\n	\n		<!-- display header : company logo + menu + user info -->\n		".getBytes();
              _jsp_string31 = "</td>\n							".getBytes();
              _jsp_string29 = "\n							".getBytes();
              _jsp_string30 = "&nbsp;".getBytes();
              _jsp_string6 = "\n					<option value=\"".getBytes();
              _jsp_string36 = "\n					</td>\n					<td>".getBytes();
              _jsp_string5 = ">Toutes</option>\n					".getBytes();
              _jsp_string11 = "\"></p>\n			<p class=\"boxSearch\">Nom:&nbsp;<input type=\"text\" name=\"name\" length=\"30\" value=\"".getBytes();
              _jsp_string16 = "\"></p>\n			<p class=\"boxSearch\">Portable:&nbsp;<input type=\"text\" name=\"cellPhone\" length=\"30\" value=\"".getBytes();
              _jsp_string26 = "\"></td>\n					<td>".getBytes();
              _jsp_string18 = "\n				<tr class=\"noresult\">\n					<td colspan=\"7\">Aucun r\u00e9sultat</td>\n				</tr>\n	".getBytes();
              _jsp_string21 = " -->\n					".getBytes();
              _jsp_string23 = "\n					".getBytes();
            }
          }
