package org.jboss.tools.examples.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.tools.examples.model.Product;
import org.jboss.tools.examples.rest.ProductService;
import org.jboss.tools.examples.rest.dto.ProductDTO;
import org.jboss.tools.examples.test.util.RandomString;

public abstract class BaseWSTestAbstract {

	protected enum InputStatus {
		VALID,
		INVALID,
		NULL,
		EMPTY
	}
	
	@Deployment
    public static WebArchive deployment() {
		// TODO find a way to load every class in the project
		WebArchive war = TestDeployment.deployment()
                .addPackage(Product.class.getPackage())
                .addPackage(ProductDTO.class.getPackage())
                .addPackage(ProductService.class.getPackage())
		 		.addPackage(BaseWSTestAbstract.class.getPackage())
				.addPackage(RandomString.class.getPackage());
    	//System.out.println(war.toString(true));
    	return war;
    }
	
	protected String createRandomString(int length) {
		return RandomString.getString(length);
	}
	
}

