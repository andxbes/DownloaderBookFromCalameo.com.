/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getswf;

import javax.swing.JOptionPane;

/**
 * http://p2.calameo.com/140829102709-db0b671491bce0b62f8642f7beab1ae4/p1.swf
 *
 * @author Andr
 */
public class DynamicURL {

	private String url, sufix = "", prefix = "", addressService = "";

	public DynamicURL(String url) {
		this.url = url;
		parse();
	}

	private void parse() {
		String splAddr[] = url.split("/");
		String fileName = splAddr[splAddr.length - 1];
		addressService = url.replace(fileName, "");

		String splitOfFileName[] = fileName.split("[0-9]");

		if (splitOfFileName.length == 1) {// 1.swf
			sufix = splitOfFileName[0];

		} else if (splitOfFileName.length >= 2) {
			prefix = splitOfFileName[0];
			sufix = splitOfFileName[splitOfFileName.length - 1];
		} else {
			JOptionPane.showMessageDialog(null, "Не знаю как отпарсить ");
			System.exit(0);
		}

	}

	@Override
	public String toString() {
		return getAddressService() + getPrefix() + "XXX" + getSufix();
	}

	/**
	 * @return the sufix
	 */
	public String getSufix() {
		return sufix;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @return the addressService
	 */
	public String getAddressService() {
		return addressService;
	}

}
