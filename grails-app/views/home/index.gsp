<%@ page import="com.vi.Point" %>
<%@ page import="com.vi.FinalPattern" %>
<div class="container">

	<h1>Bagging</h1>
	<div class="row">
		<div class="span24">
			<g:link controller="home" action="clean" class="btn pull-right">Clean</g:link>
			
			<g:link controller="home" action="addDefault_2" class="btn pull-right">Example Murti</g:link>
			<g:link controller="home" action="addDefault_1" class="btn pull-right">Murtin Figure 8.3</g:link>
		</div>
	</div>
	
	<div class="row">
		<div class="span7">
			<g:form controller="home" action="add">
				<input type="number" name="add_x" step="0.1" placeholder="x" class="span2">
				<input type="number" name="add_y" step="0.1" placeholder="y" class="span2">
				<input type="text" name="add_pattern" placeholder="class" class="span2">
				<g:submitButton class="btn pull-right" name="+"/>
			</g:form>
			
			<g:form controller="home" action="remove">
				<g:select class="span5" name="remove_point" from="${Point.list()}" value="${point?.id}" optionKey="id" optionValue="${it?.toString()}"/>
				<g:submitButton class="btn pull-right" name="-"/>
			</g:form>
			
			<hr>
			<br><br><br><br>
			<g:form controller="home" action="search">
				<input type="number" name="search_x" step="0.1" placeholder="x" class="span2" value="${params.search_x}">
				<input type="number" name="search_y" step="0.1" placeholder="y" class="span2" value="${params.search_y}">
				<g:submitButton class="btn pull-right" name="Search"/>
				<g:actionSubmit value="Search final" class="btn pull-right" action="searchForAnswer"/>
			</g:form>
			
			<h4>Actual classification: ${nearest?.name} </h4>
			<h4>Final classification: ${finalPattern} </h4>
			
			<g:each var="pattern" in="${patterns}" status="i">
				${pattern.name} - ${FinalPattern.findAllByName(pattern.name).size()}<small>x</small> <br>
			</g:each>
			
			
		</div>
		<div class="span17">
			<div id="placeholder" style="width:100%;height:500px;"></div>
		</div>
	</div>

</div>


<r:script>

var options = {
    series: {
        lines: { show: false },
        points: { show: true, fill: true, radius: 5, symbol: "circle" }
    },
    xaxes: [ { position: "bottom", min: 0 , max: 7} ],
    yaxes: [ { position: "left", min: 0  , max: 7} ],
    clickable: true,
    hoverable: true
};


$.plot($("#placeholder"), 
	[ 
	<g:each var="pattern" in="${patterns}" status="i">
		${pattern.toString()}
		<g:if test="$status != patterns.size()">,
		</g:if>
  	</g:each>
  	
  	<g:if test="${params.search_x && params.search_y}">
  		{ label: 'SEARCH', data: [[${params.search_x}, ${params.search_y}]] }
	</g:if>
	], options);

</r:script>


