package com.albo.test;

public class DataNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String nameHermo) {
        super("Data not found by nameHero:" + nameHermo);
    }

}
