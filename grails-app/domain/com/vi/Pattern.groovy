package com.vi

class Pattern {
	
	String name
	static hasMany = [points: Point]
	
	Boolean temp = false

    static constraints = {
    }
	
	static mapping = {
		points cascade: 'all-delete-orphan'
	}
	
	 public String toString(){
		 def data = []
		 this.points.each{point ->
			 data.add("[$point.x, $point.y]")
		 }
		 return "{ label: '$name', data: $data }"
	}
}
