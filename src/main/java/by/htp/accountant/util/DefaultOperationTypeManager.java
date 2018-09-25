package by.htp.accountant.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import by.htp.accountant.bean.DefaultOperationTypes;
import by.htp.accountant.bean.OperationType;

public class DefaultOperationTypeManager {	
	
	public static final String FILE_NAME = "defaultoperationTypes/defaultTypes";
	public static final String LOCALE_RU ="ru";
	public static final String LOCALE_EN ="en";
	
	private DefaultOperationTypeManager() {
		
	}	
	
	
	public static List<OperationType> getDefaultOperationTypesFromPropertie (String local){
		
		List<OperationType> defaultOperationTypesList = new ArrayList<OperationType> ();
		ResourceBundle bundle = getResourseBundle(local);
		DefaultOperationTypes[] keysAndRolesList = DefaultOperationTypes.values();
		
		for(DefaultOperationTypes typeKeyAndRole : keysAndRolesList) {
			OperationType type = new OperationType();
			type.setOperationType(bundle.getString(typeKeyAndRole.getOperationTypeKey()));
			type.setId(typeKeyAndRole.getRole());
			defaultOperationTypesList.add(type);
		}		
		return defaultOperationTypesList;
	}
	
	
	private static ResourceBundle getResourseBundle(String local) {		
		
		ResourceBundle bundle;		
		if(!Validator.simpleOneParameterNullEmptyCheck(local)) {
			if(LOCALE_RU.equals(local) || LOCALE_EN.equals(local)) {
				bundle = ResourceBundle.getBundle(FILE_NAME + "_" + local);
			} else {
				bundle = ResourceBundle.getBundle(FILE_NAME);
			}
		} else {
			bundle = ResourceBundle.getBundle(FILE_NAME);
		}		
		return bundle;
		
	}
	
}
