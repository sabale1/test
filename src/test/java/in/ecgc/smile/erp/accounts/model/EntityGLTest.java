package in.ecgc.smile.erp.accounts.model;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class EntityGLTest {
	private Validator validator;

    @BeforeTest
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    public void testEntityGLModel() {
    	EntityGL entityGL = new EntityGL();
    	entityGL.setEntityGlCd("ECGC");
    	entityGL.setMainglCd("1700");
    	entityGL.setSubglCd("003");
    	//gl name is set wrong
    	entityGL.setGlName("");
    	//gl type is set wrong
    	entityGL.setGlType("Asset");
		entityGL.setActive('Y');
		entityGL.setBalInd("na");
		entityGL.setCashaccount(982348123);
		entityGL.setGlIsGroup('N');
		entityGL.setOldCd("1001");
		entityGL.setGlSubtype("002");
		
		entityGL.setIrdaBpaCd("test1");
		entityGL.setIrdaCd("test1");
		entityGL.setSchedule6("11123L");
		entityGL.setSubBifurcationLevel("ACCT");
		entityGL.setFinancialFormName("abc");
        System.out.println(entityGL);
        Set<ConstraintViolation<EntityGL>> violations = validator.validate(entityGL);
        System.out.println(violations);
        assertEquals(violations.size(), 2);
    }
}
