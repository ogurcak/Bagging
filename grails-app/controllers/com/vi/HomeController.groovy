package com.vi

class HomeController {
	
	static layout = 'main'

    def index() { 
		def patterns = Pattern.findAllByTemp(false)
		
		render view: '/home/index', model:[patterns: patterns]
	}
	
	
	
	
	def addDefault_1(){
		def pattern = new Pattern(name: "1")
		pattern.addToPoints(new Point(x: 0.8, y: 0.8))
		pattern.addToPoints(new Point(x: 1.0, y: 1.5))
		pattern.addToPoints(new Point(x: 1.5, y: 1.0))
		pattern.addToPoints(new Point(x: 2.0, y: 0.8))
		pattern.addToPoints(new Point(x: 2.0, y: 1.5))
		pattern.save()
		
		pattern = new Pattern(name: "2")
		pattern.addToPoints(new Point(x: 1.1, y: 3.0))
		pattern.addToPoints(new Point(x: 1.5, y: 3.2))
		pattern.addToPoints(new Point(x: 1.4, y: 3.5))
		pattern.addToPoints(new Point(x: 2.0, y: 2.8))
		pattern.addToPoints(new Point(x: 1.9, y: 3.5))
		pattern.save()
		
		pattern = new Pattern(name: "3")
		pattern.addToPoints(new Point(x: 3.0, y: 2.5))
		pattern.addToPoints(new Point(x: 3.5, y: 3.1))
		pattern.addToPoints(new Point(x: 4.0, y: 3.1))
		pattern.addToPoints(new Point(x: 3.8, y: 2.6))
		pattern.addToPoints(new Point(x: 3.9, y: 3.3))
		pattern.save()
		
		pattern = new Pattern(name: "4")
		pattern.addToPoints(new Point(x: 3.4, y: 1.0))
		pattern.addToPoints(new Point(x: 3.5, y: 1.9))
		pattern.addToPoints(new Point(x: 3.9, y: 1.5))
		pattern.addToPoints(new Point(x: 4.1, y: 0.9))
		pattern.addToPoints(new Point(x: 4.1, y: 1.9))
		pattern.save()
		
		redirect(action: "index")
	}
	
	

	
	
	def addDefault_2(){
		def pattern = new Pattern(name: "X")
		pattern.addToPoints(new Point(x: 1.0, y: 1.0))
		pattern.addToPoints(new Point(x: 2.0, y: 1.0))
		pattern.addToPoints(new Point(x: 3.3, y: 1.0))
		pattern.addToPoints(new Point(x: 1.0, y: 2.0))
		pattern.addToPoints(new Point(x: 2.0, y: 2.0))
		pattern.save()
		
		pattern = new Pattern(name: "O")
		pattern.addToPoints(new Point(x: 5.0, y: 1.0))
		pattern.addToPoints(new Point(x: 6.0, y: 1.0))
		pattern.addToPoints(new Point(x: 5.0, y: 2.0))
		pattern.addToPoints(new Point(x: 6.0, y: 2.0))
		pattern.addToPoints(new Point(x: 5.0, y: 3.0))
		pattern.save()
		
		redirect(action: "index")
	}
	
	
	
	def add(){
		println params.add_x
		def pattern = Pattern.findByName(params.add_pattern) ?: new Pattern(name: params.add_pattern)
		pattern.addToPoints(new Point(x: params.add_x.toDouble(), y: params.add_y.toDouble()))
		pattern.save()
		
		redirect(action: "index")
	}
	
	
	
	def remove(){
		def point = Point.get(params.remove_point)
		def pattern = point.pattern
		pattern.removeFromPoints(point)
		pattern.save()
		point.delete()
		
		if(pattern.points.size() == 0)
			pattern.delete()
		
		redirect(action: "index")
	}
	
	
	
	def clean(){
		Pattern.list()*.delete()
		FinalPattern.list()*.delete()
		
		redirect(action: "index")
	}
	
	
	def search(){
		Pattern.findAllByTemp(true)*.delete()
		
		Pattern.findAllByTemp(false).each {
			def pattern = new Pattern(name: "${it.name}", temp: true)
			def point = it.points.first()
			pattern.addToPoints(new Point(x: point.x, y: point.y))
			point = it.points.last()
			pattern.addToPoints(new Point(x: point.x, y: point.y))
			pattern.save(flush: true)
		}
		
		def patterns = Pattern.findAllByTemp(true)
		def nearest = NNAlgorithm(params.search_x.toDouble(), params.search_y.toDouble())
		
		def finalPattern =  new FinalPattern(name: nearest.name)
		finalPattern.save()
		
		finalPattern = getFinal()
		
		render view: '/home/index', model:[patterns: patterns, nearest: nearest, finalPattern: finalPattern]
		
	}
	
	
	
	def searchForAnswer(){
		def nearest = null
		def finalPattern = null
		
		
		while(test()){
			Pattern.findAllByTemp(true)*.delete(flush: true)
		
			Pattern.findAllByTemp(false).each {
				def pattern = new Pattern(name: "${it.name}", temp: true)
				def tmp = []
				tmp.addAll it.points
				Collections.shuffle(tmp)
				def point = tmp.first()
				pattern.addToPoints(new Point(x: point.x, y: point.y))
				point = tmp.last()
				pattern.addToPoints(new Point(x: point.x, y: point.y))
				pattern.save(flush: true)
			}
		
//			println Pattern.findAllByTemp(true)
			
			nearest = NNAlgorithm(params.search_x.toDouble(), params.search_y.toDouble())
		
			finalPattern =  new FinalPattern(name: nearest.name)
			finalPattern.save(flush: true)
		
		}
		def patterns = Pattern.findAllByTemp(false)		
		finalPattern = getFinal()
		
		render view: '/home/index', model:[patterns: patterns, nearest: nearest, finalPattern: finalPattern]
	}
	
	private def test(){
		def array = FinalPattern.executeQuery("SELECT COUNT(a) FROM FinalPattern a GROUP BY a.name")
		array = array.sort()
		
		if(array.isEmpty())
			return true
		
		if(array.last() > 100 && array.size() == 1){
			println array
			return false
		}
			
		if(array.last() > 100 && array.last()/3*2 > array.get(array.size()-2)){
			println array
			return false
		}
		
		
		return true
	}
	
	
	private def NNAlgorithm(def x, def y){
		def min = null;
		def minPattern = null
		Pattern.findAllByTemp(true).each{pattern->
			pattern.points.each{point->
				def d = Math.sqrt((x-point.x)*(x-point.x) + (y-point.y)*(y-point.y))
				if(!min || min > d ) {
					min = d
					minPattern = pattern
				}
			}
		}
		return minPattern
	}
	
	
	private def getFinal(){
		def max = null
		def finalPattern = null
		Pattern.findAllByTemp(false).each{pattern->
			def size = FinalPattern.findAllByName(pattern.name).size()
			if(!max || max < size){
				max = size
				finalPattern = pattern.name
			}
		}
		return finalPattern
	}
}
