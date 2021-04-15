package package_main;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.xml.rpc.ServiceException;

import gt.gob.banguat.www.variables.ws.DataVariable;
import gt.gob.banguat.www.variables.ws.TipoCambioLocator;
import gt.gob.banguat.www.variables.ws.TipoCambioSoap;

public class Parte1_Examen_Tecnico {

	public static void main(String[] args) throws MalformedURLException, ServiceException, RemoteException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		String url = "https://www.banguat.gob.gt/variables/ws/TipoCambio.asmx";
		TipoCambioLocator locator = new TipoCambioLocator();
		
		TipoCambioSoap service = locator.getTipoCambioSoap(new URL(url));

		DataVariable respuesta = service.tipoCambioRango("02/02/2020", "02/02/2021");
		
        System.out.println("Respuesta: " + respuesta.getVars()[0].getFecha());
        
        Conexion con = new Conexion();
    	Connection insert = con.obtener();

        
        for	(int i = 0; i < respuesta.getVars().length; i++) {
        	
        	if (insert != null) {
            	PreparedStatement consulta = insert.prepareStatement("INSERT INTO cat_moneda (no_peticion, FECHA, venta, compra) VALUES (?, ?, ?, ?)");
                consulta.setInt(1, i+1);
                consulta.setString(2, respuesta.getVars()[i].getFecha());
                consulta.setFloat(3, (float) respuesta.getVars()[i].getVenta());
                consulta.setFloat(4, (float) respuesta.getVars()[i].getCompra());
                
                consulta.executeUpdate();
            }
            System.out.println("Respuesta: " + respuesta.getVars()[i].getFecha() + " " + respuesta.getVars()[i].getVenta() + " " + respuesta.getVars()[i].getCompra());
        }

       
       
	}

}
