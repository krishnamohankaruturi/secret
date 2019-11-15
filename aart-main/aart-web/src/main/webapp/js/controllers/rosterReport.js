var rosterReport = can.Control({
}, {
	'init' : function(element, options) {
		this.options = options;
		this.element = element;
		this._renderReport();
		this._renderGraph();
	},
	_renderReport: function() {
		this.element.html(can.view('js/views/rosterReport.ejs', {reportData: this.options.report}));
	},
	_renderGraph: function() {
		var data = this.options.report;
		$('#graph', $(this.element)).css({height: 400});
		var bars = {
	      data: [],
	      bars: {
	        show: true,
	        barWidth: 2,
	        fillOpacity: 0.8,
	        shadowSize : 0,
	      }
	    }, 
	    markers = {
	      data: [],
	      markers: {
	          show: false,
	          position: 'ct'
	        }
	    },
	    i, xticks=[];
		
		if(data.studentScores != null) {
			for (i = 0; i < data.studentScores.length; i++) {
				bars.data.push([(i+1)*10, Math.round((data.studentScores[i].numCorrect/data.studentScores[i].numQuestions) * 100)]);
				markers.data.push([(i+1)*10, Math.round((data.studentScores[i].numCorrect/data.studentScores[i].numQuestions) * 100)]);
				xticks.push([(i+1)*10, data.studentScores[i].localStudentIdentifier]);
			}
		}
		
		Flotr.draw($('#graph', $('#reportViewport'))[0],
	      [bars], {
	        yaxis: {
	          ticks: [0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100],
	          min: 0,
	          max: 110,
	          title: '%',
	          titleAngle: 90
	        },
	        xaxis: {
	        	ticks : xticks,
	        	min: 0,
		        max: (data.studentScores.length * 10)+10,
		        title: 'Students'
	        },
	        grid: {
	          verticalLines: false
	        },
	        mouse : {
	            track : true,
	            relative : true,
	            trackFormatter: function(o) {
	            	i=o.index;
	            	var h = '<div class="report-tooltip">'+data.studentScores[i].student.legalLastName+', '+data.studentScores[i].student.legalFirstName+'</div>';
	            	h +='<div class="report-tooltip">'+ Math.round((data.studentScores[i].numCorrect/data.studentScores[i].numQuestions) * 100) +'%</div>';
	            	
	            	return h;
	            }
	        }
	      });
	}
});