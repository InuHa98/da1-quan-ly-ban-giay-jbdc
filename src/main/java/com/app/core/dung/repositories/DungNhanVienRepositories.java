///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.app.core.dung.repositories;
//import com.app.common.configs.DBConnect;
//import com.app.core.dung.models.DungNhanVienModel;
//import java.sql.*;
//import java.util.ArrayList;
//
//
//public class DungNhanVienRepositories {
//    private Connection con ;
//    private PreparedStatement ps;
//    private ResultSet rs;
//    private String Sql;
//    public ArrayList<DungNhanVienModel>getAll(){
//        ArrayList <DungNhanVienModel>list= new ArrayList<>();
//        Sql=" select id,tai_khoan,mat_khau,email,ho_ten,sdt,gioi_tinh,dia_chi,trang_thai,ngay_tao from TaiKhoan ";
//        
//        try{
//            con =DBConnect.getInstance().getConnect();
//            ps=con.prepareStatement(Sql);
//            rs=ps.executeQuery();
//            while(rs.next()){
//               
//               DungNhanVienModel nv=new DungNhanVienModel();
//                nv.setId(rs.getInt(1));
//                nv.setTaiKhoan(rs.getString(2));
//                nv.setMatkhau(rs.getString(3));
//                nv.setEmail(rs.getString(4));
//                nv.setHoten(rs.getString(5));
//                nv.setSDT(rs.getString(6));
//                nv.setGioiTinh(rs.getBoolean(7));
//                    nv.setDiachi(rs.getString(8));
//                    nv.setTrangthai(rs.getBoolean(9));
//                    nv.setNgaytao(rs.getString(10));
//                       list.add(nv);
//               
//            }
//            return list;
//            
//        }catch(Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//    public int them(DungNhanVienModel m){
//        Sql="INSERT INTO TaiKhoan (tai_khoan, mat_khau, email, ho_ten, sdt, gioi_tinh, dia_chi, trang_thai, ngay_tao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        try{
//           con =DBConnect.getInstance().getConnect();
//            ps=con.prepareStatement(Sql);
//            ps.setObject(1, m.getTaiKhoan());
//              ps.setObject(2, m.getMatkhau());
//                ps.setObject(3, m.getEmail());
//                ps.setObject(4, m.getHoten());
//                  ps.setObject(5, m.getSDT()); 
//                  ps.setObject(6, m.isGioiTinh());
//                  ps.setObject(7, m.getDiachi());
//                   ps.setObject(8, m.isTrangthai());
//                    ps.setObject(9, m.getNgaytao());
//                    return ps.executeUpdate();
//        }catch(Exception e){
//            e.printStackTrace();
//            return 0;
//        }
//    }
//    public int xoa(int id){
//        Sql="delete from TaiKhoan where id=?";
//         try{
//           con =DBConnect.getInstance().getConnect();
//            ps=con.prepareStatement(Sql);
//            ps.setObject(1, id);
//           
//                    return ps.executeUpdate();
//        }catch(Exception e){
//            e.printStackTrace();
//            return 0;
//        }
//    }
//
//   public int sua(int id,DungNhanVienModel m){
//               Sql="UPDATE TaiKhoan\n" +
//"SET tai_khoan = ?, \n" +
//"    mat_khau = ?, \n" +
//"    email = ?, \n" +
//"    ho_ten = ?, \n" +
//"    sdt = ?, \n" +
//"    gioi_tinh = ?, \n" +
//"    dia_chi = ?, \n" +
//"    trang_thai = ?, \n" +
//"    ngay_tao = ?\n" +
//"WHERE id = ?; ";
//        try{
//           con =DBConnect.getInstance().getConnect();
//            ps=con.prepareStatement(Sql);
//            ps.setObject(1, m.getTaiKhoan());
//              ps.setObject(2, m.getMatkhau());
//                ps.setObject(3, m.getEmail());
//                ps.setObject(4, m.getHoten());
//                  ps.setObject(5, m.getSDT()); 
//                  ps.setObject(6, m.isGioiTinh());
//                  ps.setObject(7, m.getDiachi());
//                   ps.setObject(8, m.isTrangthai());
//                    ps.setObject(9, m.getNgaytao());
//                    ps.setObject(10, id);
//                    return ps.executeUpdate();
//        }catch(Exception e){
//            e.printStackTrace();
//            return 0;
//        }
//   }
//       public ArrayList<DungNhanVienModel> timKiem(String tenCanTim){
//          ArrayList <DungNhanVienModel>list= new ArrayList<>();
//        Sql=" SELECT id, tai_khoan, mat_khau, email, ho_ten, sdt, gioi_tinh, dia_chi, trang_thai, ngay_tao \" +\n" +
//"                 \"FROM TaiKhoan WHERE ho_ten LIKE ? OR tai_khoan LIKE ? OR mat_khau LIKE ? OR gioi_tinh LIKE ? OR email LIKE ?";
//        try{
//            con =DBConnect.getInstance().getConnect();
//            ps=con.prepareStatement(Sql);
//            ps.setObject(1, '%'+tenCanTim+'%');
//                        ps.setObject(2, '%'+ tenCanTim+'%');
//                        ps.setObject(3, '%'+tenCanTim+'%');
//                                    ps.setObject(4, '%'+tenCanTim+'%');
//                                                ps.setObject(5, '%'+tenCanTim+'%');
//            rs=ps.executeQuery();
//            while(rs.next()){
//               
//               DungNhanVienModel nv=new DungNhanVienModel();
//                nv.setId(rs.getInt(1));
//                nv.setTaiKhoan(rs.getString(2));
//                nv.setMatkhau(rs.getString(3));
//                nv.setEmail(rs.getString(4));
//                nv.setHoten(rs.getString(5));
//                nv.setSDT(rs.getString(6));
//                nv.setGioiTinh(rs.getBoolean(7));
//                nv.setDiachi(rs.getString(8));
//                nv.setTrangthai(rs.getBoolean(9));
//                nv.setNgaytao(rs.getString(10));
//                       list.add(nv);
//               
//            }
//            return list;
//            
//        }catch(Exception e){
//            e.printStackTrace();
//            return null;
//        }  
//}
//}
//
