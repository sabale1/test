package in.ecgc.smile.erp.accounts.integrate.proxy;

import java.util.List;

import org.springframework.stereotype.Component;

import com.microsoft.schemas.compatibility.AlternateContentDocument.AlternateContent.Fallback;

import feign.hystrix.FallbackFactory;
import in.ecgc.smile.erp.accounts.integrate.model.BankBranchDetails;

@Component
public class BankBranchDetailsFallback implements FallbackFactory<BankBranchFeignClient>{

	@Override
	public BankBranchFeignClient create(Throwable cause) {
		return new BankBranchFeignClient() {

			@Override
			public List<BankBranchDetails> fetchBankBranchList() {
				// TODO Auto-generated method stub
				return null;
			}
		
		};
	}


}
