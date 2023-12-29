package eredua;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;

public class FacadeBean {
	private static FacadeBean singleton = new FacadeBean();
	private static BLFacade facadeInterface;

	private FacadeBean() {
		try {
			facadeInterface = new BLFacadeImplementation(new DataAccess());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("FacadeBean: error al crear la lógica de negocio: " + e.getMessage());
		}
	}

	public static BLFacade getBusinessLogic() {
		return facadeInterface;
	}
}
