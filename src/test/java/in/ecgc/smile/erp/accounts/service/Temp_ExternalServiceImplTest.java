package in.ecgc.smile.erp.accounts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.model.Temp_Exporter;
import in.ecgc.smile.erp.accounts.model.Temp_TDS_DTL;

public class Temp_ExternalServiceImplTest {

	@Mock
	private Temp_Exporter temp_Exporter;

	@Mock
	private Temp_TDS_DTL temp_TDS_DTL;

	@InjectMocks
	Temp_ExternalServiceImpl externalService;

	@BeforeTest
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getExporterListTest() throws Exception {

		List<Temp_Exporter> expCodeList = new ArrayList<Temp_Exporter>();
		expCodeList.add(new Temp_Exporter("103783", "SHIVAM EXPORT", "MIRZAPUR", "Uttar Pradesh", 0, "001", "TBA",
				"HOLOG02", "EAST", "VARANASI", 16));
		expCodeList.add(new Temp_Exporter("103321", "BHARAT BELTING (P) LTD.", "MIRZAPUR", "Uttar Pradesh", 0, "001",
				"TBA", "HOLOG02", "EAST", "VARANASI", 16));
		expCodeList.add(new Temp_Exporter("104400", "FOOT INDIA CORPORATION", "MIRZAPUR", "Uttar Pradesh", 0, "001",
				"TBA", "HOLOG02", "EAST", "VARANASI", 16));
		expCodeList.add(new Temp_Exporter("103318", "ACME MIRROR WORKS", "MIRZAPUR", "Uttar Pradesh", 0, "001", "TBA",
				"HOLOG02", "EAST", "VARANASI", 16));
		expCodeList.add(new Temp_Exporter("103575", "MADHOO INTERNATIONAL", "MIRZAPUR", "Uttar Pradesh", 0, "001",
				"TBA", "HOLOG02", "EAST", "VARANASI", 16));

		Assert.assertEquals(externalService.getExporterList(), expCodeList);
	}

	@Test
	public void getTDSDtlTest() throws Exception {
		List<String> sectionsOfTDS = new ArrayList<String>();
		List<String> states = new ArrayList<String>();
		Map<String, String> vendorStates = new HashedMap<>();
		Map<String, String> supplyStates = new HashedMap<>();
		Map<String, String> stateMap = new HashedMap<>();
		Map<String, String> natureOfService = new HashedMap<String, String>();
		Map<String, String> gstType = new HashedMap<String, String>();

		states.add("Andhra Pradesh");
		states.add("Arunachal Pradesh");
		states.add("Assam");
		states.add("Bihar ");
		states.add("Chhattisgarh");
		states.add("Goa");
		states.add("Gujarat");
		states.add("Haryana");
		states.add("Himachal Pradesh");
		states.add("Jharkhand");
		states.add("Karnataka");
		states.add("Kerala");
		states.add("Madhya Pradesh");
		states.add("Maharashtra");
		states.add("Manipur");
		states.add("Meghalaya");
		states.add("Mizoram");
		states.add("Nagaland");
		states.add("Odisha");
		states.add("Punjab");
		states.add("Rajasthan");
		states.add("Sikkim");
		states.add("Tamil Nadu");
		states.add("Telangana ");
		states.add("Tripura");
		states.add("Uttarakhand");
		states.add("Uttar Pradesh ");
		states.add("West Bengal");
		states.add("Andaman and Nicobar Islands");
		states.add("Dadra and Nagar Haveli and Daman and Diu");
		states.add("Chandigarh");
		states.add("Lakshadweep");
		states.add("Puducherry");
		states.add("Delhi");
		states.add("Ladakh");
		states.add("Jammu and Kashmir");

		stateMap.put("st1", "Andhra Pradesh");
		stateMap.put("st2", "Arunachal Pradesh");
		stateMap.put("st3", "Assam");
		stateMap.put("st4", "Bihar");
		stateMap.put("st5", "Chhattisgarh");
		stateMap.put("st6", "Goa");
		stateMap.put("st7", "Gujarat");
		stateMap.put("st8", "Haryana");
		stateMap.put("st9", "Himachal Pradesh");
		stateMap.put("st10", "Jharkhand");
		stateMap.put("st11", "Karnataka");
		stateMap.put("st12", "Kerala");
		stateMap.put("st13", "Madhya Pradesh");
		stateMap.put("st14", "Maharashtra");
		stateMap.put("st15", "Manipur");
		stateMap.put("st16", "Meghalaya");
		stateMap.put("st17", "Mizoram");
		stateMap.put("st18", "Nagaland");
		stateMap.put("st19", "Odisha");
		stateMap.put("st20", "Punjab");
		stateMap.put("st21", "Rajasthan");
		stateMap.put("st22", "Sikkim");
		stateMap.put("st23", "Tamil Nadu");
		stateMap.put("st24", "Telangana ");
		stateMap.put("st25", "Tripura");
		stateMap.put("st26", "Uttarakhand");
		stateMap.put("st27", "Uttar Pradesh ");
		stateMap.put("st28", "West Bengal");
		stateMap.put("st29", "Delhi");
		stateMap.put("ut1", "Andaman and Nicobar Islands");
		stateMap.put("ut2", "Dadra and Nagar Haveli and Daman and Diu");
		stateMap.put("ut3", "Chandigarh");
		stateMap.put("ut4", "Lakshadweep");
		stateMap.put("ut5", "Puducherry");
		stateMap.put("ut7", "Ladakh");
		stateMap.put("ut8", "Jammu and Kashmir");
		vendorStates = stateMap;
		supplyStates = stateMap;

		sectionsOfTDS.add("194C-Payment to Contractors and Advertising");
		sectionsOfTDS.add("194D-Insurance Commission");
		sectionsOfTDS.add("194H-Commission and Brokerage");
		sectionsOfTDS.add("194I-Rent");
		sectionsOfTDS.add("194IA-Immovable Property");
		sectionsOfTDS.add("194J-Professional Fees");
		sectionsOfTDS.add("195-Payment to Non Resident");
		sectionsOfTDS.add("OTH-Others");

		gstType.put("i", "IGST");
		gstType.put("cs", "CGST-SGST");
		gstType.put("cu", "CGST-UTGST");

		natureOfService.put("nos1", "Service Provided by Director or Body Corporate of a Company");
		natureOfService.put("nos2", "Agency Commission");
		natureOfService.put("nos3", "Supply of Manpower and Security");
		natureOfService.put("nos4", "Service Provided by a person who is located in a Non Taxable Territory");
		natureOfService.put("nos5", "Individual Advocates");
		natureOfService.put("nos6", "Director Fees");
		natureOfService.put("nos7", "Import of Services");
		natureOfService.put("nos8", "Services Provided by an Arbitral Tribunal");
		natureOfService.put("nos9", "Hiring of motor vehicle");
		natureOfService.put("nos10", "Legal Service");
		natureOfService.put("nos11", "Sponsorship Service");
		natureOfService.put("nos12", "Work Contract");
		natureOfService.put("nos13", "Unregister Vendor");
		natureOfService.put("nos14", "Sponsorship");
		natureOfService.put("nos15", "Goods transport agency");
		natureOfService.put("nos16", "Security Services");

		Temp_TDS_DTL tdsDtl = new Temp_TDS_DTL(sectionsOfTDS, vendorStates, supplyStates, natureOfService, gstType);
		Assert.assertEquals(externalService.getTDSDtl(), tdsDtl);
	}
}
