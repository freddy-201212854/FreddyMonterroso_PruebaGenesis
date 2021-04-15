package service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import com.google.gson.Gson;

import gt.gob.banguat.www.variables.ws.DataVariable;
import gt.gob.banguat.www.variables.ws.TipoCambioLocator;
import gt.gob.banguat.www.variables.ws.TipoCambioSoap;

/**
 * Servlet implementation class MidLayer
 */
@WebServlet("/MidLayer")
public class MidLayer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MidLayer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");		

        Gson gson = new Gson();
        
		String url = "https://www.banguat.gob.gt/variables/ws/TipoCambio.asmx";
		TipoCambioLocator locator = new TipoCambioLocator();
		
		TipoCambioSoap service = null;
		try {
			service = locator.getTipoCambioSoap(new URL(url));
			System.out.println("Parametros" + request.getParameter("FechaInicio"));
	        System.out.println("Parametros" + request.getParameter("FechaFin"));
	        
			DataVariable respuesta = service.tipoCambioRango(request.getParameter("FechaInicio"), request.getParameter("FechaFin"));
			            
	        TipoCambio arrayCaracteres[];
	        arrayCaracteres = new TipoCambio[respuesta.getVars().length];
	        
	        /*Conexion con = new Conexion();
	    	Connection insert = con.obtener();*/
	        
	        for	(int i = 0; i < respuesta.getVars().length; i++) {
	        	TipoCambio objeto = new TipoCambio(respuesta.getVars()[i].getFecha(), respuesta.getVars()[i].getVenta(), respuesta.getVars()[i].getCompra());
	        	
	        	arrayCaracteres[i] =  objeto;
	        	
	        	/*if (insert != null) {
	        		PreparedStatement consulta = insert.prepareStatement("INSERT INTO tipocambio (FECHA, venta, compra) VALUES (?, ?, ?)");
	                consulta.setString(1, respuesta.getVars()[i].getFecha());
	                consulta.setFloat(2, (float) respuesta.getVars()[i].getVenta());
	                consulta.setFloat(3, (float) respuesta.getVars()[i].getCompra());
	                
	                consulta.executeUpdate();
	                
	                consulta.executeUpdate();
	            }*/
	            System.out.println("Respuesta: " + respuesta.getVars()[i].getFecha() + " " + respuesta.getVars()[i].getVenta() + " " + respuesta.getVars()[i].getCompra());
	        }

	        String datos = gson.toJson(arrayCaracteres);

			response.getWriter().write(datos);

		} catch (MalformedURLException | ServiceException e) {
			// TODO Auto-generated catch block
			response.getWriter().write("Error al consultar fecha");
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");		


		doGet(request, response);
	}

}
