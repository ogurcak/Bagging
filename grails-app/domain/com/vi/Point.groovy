package com.vi

class Point {
	
	Double x
	Double y
	
	static belongsTo = [pattern: Pattern]

    static constraints = {
		pattern nullable:true
    }
	
	
	public String toString(){
		return "[$x, $y, ${pattern.name}]"
   }
}
