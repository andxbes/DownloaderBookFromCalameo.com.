package getswf;


import getswf._4book._4Book;

public class _4bookTest {

	//@Test
	public void test() {
		String url ="http://4book.org/gdz-reshebniki-ukraina/2-klass/1659-gdz-otvety-reshebnik-russkij-yazyk-2-klass-lapshina";
		_4Book book = new _4Book();
		book.parse(url);
		
		
	}

}
