//package services;
//
//<<<<<<< HEAD
//import java.net.HttpURLConnection;
//
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import models.Administrator;
//import dao.AdministratorDAO;
//
//@Stateless
//@Path("admin")
//public class AdministratorManager {
//
//	@EJB
//	private AdministratorDAO adminDao;
//	
//	@Inject
//	private AdministratorContext adminContext;
//
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	public void registerAdmin(Administrator admin) {
//		if (admin.getSecurityCode() == "12345") {
//			adminDao.addAdministrator(admin);
//			adminContext.setCurrentAdministrator(admin);
//		}
//	}
//
//	@Path("login")
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response loginAdmin(Administrator admin) {
//		boolean isUserValid = adminDao.validateAdministratorCredentials(
//				admin.getUsername(), admin.getPassword());
//		if (!isUserValid) {
//			return Response.status(HttpURLConnection.HTTP_UNAUTHORIZED).build();
//		}
//		adminContext.setCurrentAdministrator(admin);
//		return Response.ok().build();
// 	}
//	
//	
//}

