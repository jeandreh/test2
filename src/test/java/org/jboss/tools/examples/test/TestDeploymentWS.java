package org.jboss.tools.examples.test;

import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.tools.examples.model.Product;
import org.jboss.tools.examples.rest.ProductService;
import org.jboss.tools.examples.rest.dto.ProductDTO;

public class TestDeploymentWS {
	
    public static WebArchive deployment() {
    	WebArchive war = TestDeployment.deployment()
                .addPackage(Product.class.getPackage())
                .addPackage(ProductDTO.class.getPackage())
                .addPackage(ProductService.class.getPackage());
    	//System.out.println(war.toString(true));
    	return war;
    }
    
}
