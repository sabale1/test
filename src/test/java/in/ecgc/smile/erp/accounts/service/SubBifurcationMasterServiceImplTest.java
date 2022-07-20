package in.ecgc.smile.erp.accounts.service;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.model.SubBifurcations;
import in.ecgc.smile.erp.accounts.repository.SubBifurcationMasterDao;

public class SubBifurcationMasterServiceImplTest {

	@Mock
	SubBifurcationMasterDao subBifurcationDao;

	@InjectMocks
	SubBifurcationMasterServiceImpl subBifurcationMasterServiceImpl;

	private SubBifurcations subBifurcationMaster;

	@BeforeTest
	private void init() {
		MockitoAnnotations.initMocks(this);

	}

	@BeforeTest
	public void initSubBifurcationMasterValue() {
		subBifurcationMaster = new SubBifurcations();
		subBifurcationMaster.setSubBifurcationLevel("ELC");
		subBifurcationMaster.setDescription("Electricity Charges");
		subBifurcationMaster.setActive('V');
		subBifurcationMaster.setCreatedBy("ACCT");
		subBifurcationMaster.setCreatedDt(LocalDate.now());
		subBifurcationMaster.setLastUpdatedBy("ACCT");
		subBifurcationMaster.setLastUpdatedDt(LocalDate.now());
	}

	@Test
	public void getSubBifurcationsTest() {
		List<SubBifurcations> listSubBifurcations = new ArrayList<>();
		listSubBifurcations.add(subBifurcationMaster);
		when(subBifurcationDao.getSubBifurcations()).thenReturn(listSubBifurcations);
		Assert.assertEquals(subBifurcationMasterServiceImpl.getSubBifurcations(), listSubBifurcations);
	}

	@Test
	public void getSubBifurcationsLevelTest() {
		List<String> listLevels = new ArrayList<>();
		listLevels.add("FMG");
		listLevels.add("ECG");
		when(subBifurcationDao.getSubBifurcationLevels()).thenReturn(listLevels);
		Assert.assertEquals(subBifurcationMasterServiceImpl.getSubBifurcationsLevel(), listLevels);
		;
	}

	@Test
	public void getSubBifurcationsByLevelTest() {
		when(subBifurcationDao.getSubBifurcationsByLevel("ELC")).thenReturn(subBifurcationMaster);
		Assert.assertEquals(subBifurcationMasterServiceImpl.getSubBifurcationsByLevel("ELC"), subBifurcationMaster);
	}

	@Test
	public void addSubBifurcationTest() {
		when(subBifurcationDao.addSubBifurcation(subBifurcationMaster)).thenReturn(Integer.valueOf(1));
		Assert.assertEquals(subBifurcationMasterServiceImpl.addSubBifurcation(subBifurcationMaster),
				Integer.valueOf(1));
	}

	@Test
	public void updatedSubBifurcationsTest() {
		when(subBifurcationDao.updateSubBifurcationLevel(subBifurcationMaster)).thenReturn(subBifurcationMaster);
		Assert.assertEquals(
				subBifurcationMasterServiceImpl.updatedSubBifurcations(subBifurcationMaster, subBifurcationMaster),
				subBifurcationMaster);
	}
}
