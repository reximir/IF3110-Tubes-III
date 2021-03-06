package webService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import model.AccessManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import dto.Barang;

@Path("/barang")
// GantiNama
public class BarangService {
	String query;
	Connection con = new AccessManager().getCon();
	private Gson gson;

	@GET
	@Path("/insert")
	@Produces("application/json")
	public String insertBarang(@Context UriInfo info) {
		System.out.println("SR1");
		gson = new Gson();
		String json = null;
		JsonParser jsonParser = new JsonParser();
		String insertResult = null;
		try {
			String idKat = info.getQueryParameters().getFirst("idKat");
			String nama = info.getQueryParameters().getFirst("nama");
			String gambar = info.getQueryParameters().getFirst("gambar");
			String keterangan = info.getQueryParameters()
					.getFirst("keterangan");
			String harga = info.getQueryParameters().getFirst("harga");
			String jumlah = info.getQueryParameters().getFirst("jumlah");
			query = "INSERT INTO barang (id_kategori, nama_barang, gambar, harga_barang, keterangan, jumlah_barang, nKat) VALUES ('"
					+ idKat
					+ "','"
					+ nama
					+ "','"
					+ gambar
					+ "','"
					+ harga
					+ "','" + keterangan + "','" + jumlah + "','-1')";
			PreparedStatement stmt = con.prepareStatement(query);
			insertResult = gson.toJson(stmt.executeUpdate());
		} catch (Exception e) {
		}
		System.out.println("SR2");
		return insertResult;
	}

	@GET
	@Path("/select2")
	@Produces("application/json")
	public String selectSelection(
			@DefaultValue("") @QueryParam("p1") String p1,
			@DefaultValue("") @QueryParam("p2") String p2,
			@DefaultValue("") @QueryParam("p3") String p3,
			@DefaultValue("") @QueryParam("p4") String p4,
			@DefaultValue("") @QueryParam("p5") String p5,
			@QueryParam("page") int page) {
		String selectResult = null;
		query = "SELECT kategori.nama, barang.gambar, barang.id, barang.id_kategori, barang.nama_barang, barang.harga_barang, barang.jumlah_barang, barang.keterangan FROM barang JOIN kategori ON barang.id_kategori=kategori.id "
				+ p3 + p4 + p5 + p1 + p2 + "LIMIT " + page + ",10";
		System.out.println(query);
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs;
			rs = stmt.executeQuery();
			ArrayList<Barang> barangList = new ArrayList<Barang>();
			while (rs.next()) {
				Barang barang = new Barang();
				barang.setId(rs.getInt("id"));
				barang.setId_category(rs.getInt("id_kategori"));
				barang.setName(rs.getString("nama_barang"));
				barang.setPicture(rs.getString("gambar"));
				barang.setPrice(rs.getInt("harga_barang"));
				barang.setDescription(rs.getString("keterangan"));
				barang.setTotal_item(rs.getInt("jumlah_barang"));
				barang.setnKat(rs.getString("kategori.nama"));
				barangList.add(barang);
			}
			gson = new Gson();
			selectResult = gson.toJson(barangList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return selectResult;
	}

	@GET
	@Path("/select3")
	@Produces("application/json")
	public String select3(@QueryParam("p3") String p3,
			@QueryParam("p4") String p4, @QueryParam("p5") String p5,
			@QueryParam("page") int page) {
		String selectResult = null;
		query = "SELECT COUNT(id) AS JmlBarang FROM barang WHERE id=id " + p3
				+ p4 + p5;
		System.out.println(query);
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs;			
			rs = stmt.executeQuery();
			rs.next();
			int cID = (rs.getInt(1));
			gson = new Gson();
			selectResult = gson.toJson(cID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return selectResult;
	}

	@GET
	@Path("/select")
	@Produces("application/json")
	public String selectBarang(@DefaultValue("-1") @QueryParam("id") int id,
			@DefaultValue("-1") @QueryParam("idKat") int idKat,
			@DefaultValue("-1") @QueryParam("nama") String nama,
			@DefaultValue("0") @QueryParam("join") int join) {
		String selectResult = null;
		try {
			ArrayList<Barang> barangList = new ArrayList<Barang>();
			if (join == 1)
				query = "SELECT * FROM barang JOIN kategori ON barang.id_kategori=kategori.id AND barang.id="
						+ id;
			else if (join == 2)
				query = "SELECT * FROM barang JOIN barang_user ON barang.id=barang_user.id_barang AND barang.id_kategori="
						+ idKat
						+ " GROUP BY barang.id ORDER BY COUNT(barang.id) DESC LIMIT 0,4";
			else {
				query = "SELECT * FROM barang WHERE 1";
				if (id != -1)
					query += (" && id=" + id);
				if (idKat != -1)
					query += (" && id_kategori=" + idKat);
				if (!nama.equals("-1"))
					query += (" && nama_barang=\"" + nama + "\"");
			}
			System.out.println(query);
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Barang barang = new Barang();
				barang.setId(rs.getInt("id"));
				barang.setId_category(rs.getInt("id_kategori"));
				barang.setName(rs.getString("nama_barang"));
				barang.setPicture(rs.getString("gambar"));
				barang.setPrice(rs.getInt("harga_barang"));
				barang.setDescription(rs.getString("keterangan"));
				barang.setTotal_item(rs.getInt("jumlah_barang"));
				barangList.add(barang);
			}
			gson = new Gson();
			selectResult = gson.toJson(barangList);
		} catch (Exception e) {
		}
		return selectResult;
	}

	@GET
	@Path("/update")
	@Produces("application/json")
	public String updateBarang(@QueryParam("id") int id,
			@DefaultValue("-1") @QueryParam("idKat") int idKat,
			@DefaultValue("-1") @QueryParam("nama") String nama,
			@DefaultValue("-1") @QueryParam("gambar") String gambar,
			@DefaultValue("-1") @QueryParam("keterangan") String keterangan,
			@DefaultValue("-1") @QueryParam("harga") int harga,
			@DefaultValue("-1") @QueryParam("jumlah") int jumlah) {
		String updateResult = null;
		int temp;
		try {
			query = "UPDATE barang SET id=barang.id";
			if (idKat != -1)
				query += (", id_kategori=" + idKat);
			if (!nama.equals("-1"))
				query += (", nama_barang=\"" + nama + "\"");
			if (!gambar.equals("-1"))
				query += (", gambar=\"" + gambar + "\"");
			if (!keterangan.equals("-1"))
				query += (", keterangan=\"" + keterangan + "\"");
			if (harga != -1)
				query += (", harga_barang=" + harga);
			if (jumlah != -1)
				query += (", jumlah_barang=" + jumlah);
			if (id != -1)
				query += (" WHERE id=" + id);
			PreparedStatement stmt = con.prepareStatement(query);
			System.out.println(query);
			temp = stmt.executeUpdate();
			gson = new Gson();
			updateResult = gson.toJson(temp);
		} catch (Exception e) {
		}
		return updateResult;
	}

	@GET
	@Path("/delete")
	@Produces("application/json")
	public String deleteBarang(@Context UriInfo info) {
		String deleteResult = null;
		int temp;
		try {
			String id = info.getQueryParameters().getFirst("id");
			query = "DELETE FROM barang WHERE id=" + id;
			PreparedStatement stmt = con.prepareStatement(query);
			temp = stmt.executeUpdate();
			gson = new Gson();
			deleteResult = gson.toJson(temp);
		} catch (Exception e) {
		}
		return deleteResult;
	}
}
