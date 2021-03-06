package com.frexesc.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.frexesc.SOAP.InsertBarangUserProxy;
import com.frexesc.model.Barang;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
public class AddCart extends HttpServlet {
	Gson gson = new Gson();
	String json = null;
	JsonParser jsonParser = new JsonParser();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddCart() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (session.getAttribute("username") == null) {
			response.getWriter().write("Redirect: ../register");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();
		if (session.getAttribute("username") == null) {
			response.getWriter().write("Redirect: ../register");
		} else {
						
			response.setContentType("text/html"); // set Content Type for AJAX
			try {
				json = WebServicesKit.readUrl("http://coba-soap.ap01.aws.af.cm/BS/barang/select?id="+request.getParameter("id_barang"));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JsonArray barangArray = jsonParser.parse(json).getAsJsonArray();
			ArrayList<Barang> barangList = new ArrayList<Barang>();
			for (JsonElement barang: barangArray) {
				Barang barangObj = gson.fromJson(barang, Barang.class);
				barangList.add(barangObj);
			}
			if (barangList.get(0).getTotal_item()< Integer
					.valueOf(request.getParameter("qty"))
					|| Integer.valueOf(request.getParameter("qty")) <= 0) {
				response.getWriter()
						.write("Failure: Transaksi tidak berhasil, qty yang dimasukkan tidak valid.");
			} else {
				String deskripsiTambahan = request
						.getParameter("deskripsi_tambahan");
				if (deskripsiTambahan == null)
					deskripsiTambahan = "";

				// Add to Cart here
				/**PORT*/
				InsertBarangUserProxy insertBarangPro = new InsertBarangUserProxy();
				insertBarangPro.insertBarangUser(request.getParameter("id_barang"),session.getAttribute("user_id").toString(),request.getParameter("qty"),deskripsiTambahan);
				System.out.println("IT WORKS SOAP updated=>"+request.getParameter("id_barang")+session.getAttribute("user_id").toString()+request.getParameter("qty")+deskripsiTambahan);
				/**PORT*/
				
				/**old*/
//				DbConnection dbConnection = new DbConnection();
//				Connection connection = dbConnection.mySqlConnection();
//				String query2 = "INSERT INTO barang_user (id_barang,id_user,status,jumlah_barang,deskripsi_tambahan) VALUES ("
//						+ request.getParameter("id_barang")
//						+ ", "
//						+ session.getAttribute("user_id")
//						+ ", 0, "
//						+ request.getParameter("qty")
//						+ ", \""
//						+ deskripsiTambahan + "\")";
//				try {
//					connection.createStatement().executeUpdate(query2);
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				/**old*/
				//POST
				//String[] param = {"id", "user", "qty", "desc"};
				//String[] val= {"" + request.getParameter("id_barang") , "" + session.getAttribute("user_id"), "" +  request.getParameter("qty"), "" + //deskripsiTambahan};
				//ServiceParser.postUrl(ServiceParser.BASE_URL + "BarangUserService/baranguserService/insertbaranguser",param, val);
						

				// Update to Barang here
				try {
					/**port*/
					
					
					/**port*/
					
					/**old*/
					// Update to Barang here
//					String query3 = "UPDATE barang SET jumlah_barang=" + (Integer.parseInt(rs.getString("jumlah_barang")) - Integer.parseInt(request.getParameter("qty"))) + " WHERE id=" + request.getParameter("id_barang"); 
//					
//					connection.createStatement().executeUpdate(query3);
//					
					response.getWriter().write("Success: Transaksi berhasil!");
					
					/**SAL*/
					json = WebServicesKit.readUrl("http://coba-soap.ap01.aws.af.cm/BS/barang/update?id="
							+ request.getParameter("id_barang")
							+ "&jumlah="
							+ (barangList.get(0).getTotal_item() - Integer
									.parseInt(request.getParameter("qty"))));
					/**old*/
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
}
