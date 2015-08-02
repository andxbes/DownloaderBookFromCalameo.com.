/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getswf;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Andr
 */
public class DynamicURLIT {
    
    public DynamicURLIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSomeMethod() {
	// TODO review the generated test code and remove the default call to fail.
	String url = "http://p2.calameo.com/140829102709-db0b671491bce0b62f8642f7beab1ae4/5451sdfsdf.swf";
	String url2= "http://p2.calameo.com/140829102709-db0b671491bce0b62f8642f7beab1ae4/p5451.swf";
	String url3 = "http://p2.calameo.com/140829102709-db0b671491bce0b62f8642f7beab1ae4/1.swf";
	
	
	
	
	DynamicURL ur = new DynamicURL(url);
	Assert.assertTrue(url.equals(ur.getAddressService()+ur.getPrefix()+"5451"+ur.getSufix()));
	DynamicURL ur2 = new DynamicURL(url2);
	Assert.assertTrue(url2.equals(ur2.getAddressService()+ur2.getPrefix()+"5451"+ur2.getSufix()));
	DynamicURL ur3 = new DynamicURL(url3);
	Assert.assertTrue(url3.equals(ur3.getAddressService()+ur3.getPrefix()+"1"+ur3.getSufix()));

	System.out.println(url + "\n vs \n" + ur + "\n");
	System.out.println(url2 + "\n vs \n" + ur2 + "\n");
	System.out.println(url3 + "\n vs \n" + ur3 + "\n");
	
    }
    
}
